package feliperrm.com.wordscrambler.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by FelipeRRM on 4/2/2019.
 */
@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)

    @Query("SELECT sum(rightAnswers) FROM Session")
    suspend fun getTotalRightAnswers(): Int

    @Query("SELECT sum(wrongAnswers) FROM Session")
    suspend fun getTotalWrongAnswers(): Int

    @Query("SELECT count(*) FROM Session")
    suspend fun getTotalSessions(): Int

    @Query("SELECT sum(secondsPlayed) FROM Session")
    suspend fun getTotalTimePlayed(): Int?

}