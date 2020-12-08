package feliperrm.com.wordscrambler.utils

import feliperrm.com.wordscrambler.data.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

/**
 * Created by FelipeRRM on 4/1/2019.
 */
class WordSave(private val iS: InputStream) {

    suspend fun saveWordsToDb() = withContext(Dispatchers.IO) {
        iS.bufferedReader().useLines { lines ->
            // Inserts all the words into the DB in a single transaction as it is much faster than inserting word by word
            val wordsList = lines.mapTo(ArrayList()) { word -> Word(word = word, timesPlayed = 0) }
            App.application.db.wordDao().insertWords(wordsList)
        }
    }

}