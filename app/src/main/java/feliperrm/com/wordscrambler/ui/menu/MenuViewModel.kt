package feliperrm.com.wordscrambler.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feliperrm.com.wordscrambler.utils.App
import feliperrm.com.wordscrambler.utils.WordSave
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.InputStream

class MenuViewModel(private val wordsStream: InputStream) : ViewModel() {

    val wordsAvailable = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            wordsAvailable.value = App.application.db.wordDao().getTotalWords()
            if (wordsAvailable.value ?: 0 <= 0) {
                Timber.d("Loading words from file into DB!")
                loadWordFromFile()
                wordsAvailable.value = App.application.db.wordDao().getTotalWords()
            }
            Timber.d("Words in DB: %s", wordsAvailable.value)
        }
    }

    private suspend fun loadWordFromFile() {
        WordSave(wordsStream).saveWordsToDb()
    }

}
