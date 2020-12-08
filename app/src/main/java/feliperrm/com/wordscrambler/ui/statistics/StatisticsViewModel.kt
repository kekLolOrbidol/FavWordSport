package feliperrm.com.wordscrambler.ui.statistics

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import feliperrm.com.wordscrambler.data.RoomDatabase
import kotlinx.coroutines.launch

class StatisticsViewModel(private val db: RoomDatabase) : ViewModel() {

    val rightAnswers = MutableLiveData<Int>()
    val wrongAnswers = MutableLiveData<Int>()
    val answersPieEntries = MediatorLiveData<List<PieEntry>>().apply {
        addSource(rightAnswers) {
            value = getAnswersPieEntries(rightAnswers.value ?: 0, wrongAnswers.value ?: 0)
        }
        addSource(wrongAnswers) {
            value = getAnswersPieEntries(rightAnswers.value ?: 0, wrongAnswers.value ?: 0)
        }
    }

    val sessionsPlayed = MutableLiveData<String>()
    val timePlayed = MutableLiveData<String>().apply { value = "0" }
    val easiestWord = MutableLiveData<String>()
    val easiestWordSeconds = MutableLiveData<Int>().apply { value = 0 }
    val hardestWord = MutableLiveData<String>()
    val hardestWordSeconds = MutableLiveData<Int>().apply { value = 0 }

    private fun getAnswersPieEntries(right: Int, wrong: Int) =
        ArrayList<PieEntry>().apply {
            add(PieEntry(right.toFloat(), "Right"))
            add(PieEntry(wrong.toFloat(), "Wrong"))
        }

    init {
        viewModelScope.launch {
            sessionsPlayed.value = db.sessionDao().getTotalSessions().toString()
            rightAnswers.value = db.sessionDao().getTotalRightAnswers()
            wrongAnswers.value = db.sessionDao().getTotalWrongAnswers()
            timePlayed.value = db.sessionDao().getTotalTimePlayed()?.toString() ?: "0"
            val easiestAnswerWord = db.rightAnswerDao().getEasiestAnswerWord()
            val hardestAnswerWord = db.rightAnswerDao().getHardestAnswerWord()
            easiestAnswerWord?.wordId?.let {
                easiestWord.value = db.wordDao().getWordById(it).word
            }
            hardestAnswerWord?.wordId?.let {
                hardestWord.value = db.wordDao().getWordById(it).word
            }
            easiestWordSeconds.value = easiestAnswerWord?.secondsPlayed ?: 0
            hardestWordSeconds.value = hardestAnswerWord?.secondsPlayed ?: 0
        }
    }

}
