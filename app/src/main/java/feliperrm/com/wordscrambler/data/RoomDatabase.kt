package feliperrm.com.wordscrambler.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by FelipeRRM on 4/2/2019.
 */
@Database(entities = [Word::class, Session::class, CorrectAnswer::class], version = 2)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun sessionDao(): SessionDao
    abstract fun rightAnswerDao(): CorrectAnswerDao
}