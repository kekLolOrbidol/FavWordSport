package feliperrm.com.wordscrambler.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by FelipeRRM on 4/2/2019.
 */
@Dao
interface CorrectAnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRightAnswer(correctAnswer: CorrectAnswer)

    /**
     * Returns the wordId that took the player more time to guess.
     * In the future we can also take into account the number of attempts at guessing the answer.
     */
    @Query("SELECT * FROM CorrectAnswer ORDER BY secondsPlayed DESC LIMIT 1")
    suspend fun getHardestAnswerWord(): CorrectAnswer?

    /**
     * Returns the wordId that took the player less time to guess
     * In the future we can also take into account the number of attempts at guessing the answer.
     */
    @Query("SELECT * FROM CorrectAnswer ORDER BY secondsPlayed ASC LIMIT 1")
    suspend fun getEasiestAnswerWord(): CorrectAnswer?

}