package feliperrm.com.wordscrambler.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by FelipeRRM on 4/2/2019.
 */
@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<Word>)

    /**
     * Returns a random number o words with the specified min length
     * @param minLength minimum length of each returned word
     */
    @Query("SELECT * FROM Word WHERE length(word) >= :minLength AND length(word) <= :maxLength ORDER BY RANDOM() LIMIT :number")
    suspend fun getRandomWords(number: Int, minLength: Int = 2, maxLength: Int = 11): List<Word>

    @Query("SELECT count(*) FROM Word")
    suspend fun getTotalWords(): Int

    @Query("SELECT * FROM Word WHERE id == :id")
    suspend fun getWordById(id: Int): Word

}