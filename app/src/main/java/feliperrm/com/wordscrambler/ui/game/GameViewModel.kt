package feliperrm.com.wordscrambler.ui.game

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feliperrm.com.wordscrambler.data.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.concurrent.fixedRateTimer

private const val WORDS_PER_REQUEST = 50
private const val SIZE_TO_REQUEST_MORE = 10

class GameViewModel(private val db: RoomDatabase, private val minWordSize: Int, private val maxWordSize: Int) :
    ViewModel() {

    // List containing the next words to be played
    private val wordsList = ObservableArrayList<Word>()

    // Scrambled word that is currently being played
    private val scrambledWord = MutableLiveData<ScrambleWord>()
    // LiveData (Observable) String representation of the scrambled word. Updates automatically whenever we have a new scrambled word.
    val scrambledWordText =
        MediatorLiveData<String>().apply { addSource(scrambledWord) { word -> value = word.toString() } }
    val timeElapsedWord = MutableLiveData<Int>().apply { value = 0 }
    private val timeElapsed = MutableLiveData<Int>().apply { value = 0 }
    val rightAnswersSession = MutableLiveData<Int>().apply { value = 0 }
    val wrongAnswersSession = MutableLiveData<Int>().apply { value = 0 }
    private val wrongAnswersWord = MutableLiveData<Int>()

    // Current text written by the player
    val currentText = MutableLiveData<String>()

    // Timer handling the incremental watch
    var timer: Timer? = null

    init {
        viewModelScope.launch {
            wordsList.addAll(db.wordDao().getRandomWords(WORDS_PER_REQUEST, minWordSize, maxWordSize))
            wordsList.addOnListChangedCallback(listListener)
            setNewWord()
        }
    }

    private fun setNewWord() {
        wrongAnswersWord.value = 0
        scrambledWord.value = ScrambleWord(wordsList.removeAt(0))
        Timber.d("UNSCRAMBLED WORD: %s", scrambledWord.value?.word?.word)
        resetTimer()
    }

    private fun resetTimer() {
        timeElapsedWord.value = 0
        timer?.cancel()
        timer = fixedRateTimer(period = 1000) {
            viewModelScope.launch {
                timeElapsed.apply { value = value?.plus(1) ?: 1 }
                timeElapsedWord.apply { value = value?.plus(1) ?: 1 }
            }
        }
    }

    fun playWord() {
        if (currentText.value?.equals(scrambledWord.value?.word?.word, ignoreCase = true) == true) {
            correctAnswer()
        } else {
            wrongAnswer()
        }
        currentText.value = ""
    }

    private fun correctAnswer() {
        storeRightAnswer(scrambledWord.value?.word?.id, timeElapsedWord.value, wrongAnswersWord.value)
        rightAnswersSession.apply { value = value?.plus(1) ?: 1 }
        setNewWord()
    }

    private fun storeRightAnswer(wordId: Int?, secondsPlayed: Int?, wrongAnswers: Int?) {
        GlobalScope.launch {
            db.rightAnswerDao().insertRightAnswer(
                CorrectAnswer(
                    wordId = wordId,
                    secondsPlayed = secondsPlayed,
                    wrongAnswers = wrongAnswers
                )
            )
        }
    }

    private fun wrongAnswer() {
        wrongAnswersSession.apply { value = value?.plus(1) ?: 1 }
        wrongAnswersWord.apply { value = value?.plus(1) ?: 1 }
    }

    override fun onCleared() {
        timer?.cancel()
        savePlayedSession()
        super.onCleared()
    }

    private fun savePlayedSession() {
        GlobalScope.launch {
            db.sessionDao().insertSession(
                Session(
                    secondsPlayed = timeElapsed.value,
                    rightAnswers = rightAnswersSession.value,
                    wrongAnswers = wrongAnswersSession.value
                )
            )
        }
    }

    private val listListener = object : ObservableList.OnListChangedCallback<ObservableArrayList<Word>>() {

        override fun onChanged(sender: ObservableArrayList<Word>?) {}

        /* Loads more words from the DB if we reach a certain threshold.
         The idea is to always have some words available in memory and not do DB operations all the time,
         as these are much more expensive.
         */
        override fun onItemRangeRemoved(sender: ObservableArrayList<Word>?, positionStart: Int, itemCount: Int) {
            if (wordsList.size < SIZE_TO_REQUEST_MORE) {
                viewModelScope.launch {
                    wordsList.addAll(db.wordDao().getRandomWords(WORDS_PER_REQUEST, minWordSize, maxWordSize))
                }
            }
        }

        override fun onItemRangeMoved(
            sender: ObservableArrayList<Word>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
        }

        override fun onItemRangeInserted(sender: ObservableArrayList<Word>?, positionStart: Int, itemCount: Int) {}

        override fun onItemRangeChanged(sender: ObservableArrayList<Word>?, positionStart: Int, itemCount: Int) {}

    }


}
