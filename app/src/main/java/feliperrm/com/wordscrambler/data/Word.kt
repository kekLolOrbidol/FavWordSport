package feliperrm.com.wordscrambler.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by FelipeRRM on 4/2/2019.
 */
@Entity(indices = [Index(value = ["word"], unique = true)])
data class Word(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var word: String,
    var timesPlayed: Int? = 0
)