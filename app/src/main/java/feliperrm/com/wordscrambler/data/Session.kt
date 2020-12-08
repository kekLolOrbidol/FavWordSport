package feliperrm.com.wordscrambler.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by FelipeRRM on 4/2/2019.
 */
@Entity
data class Session(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var secondsPlayed: Int?,
    var rightAnswers: Int?,
    var wrongAnswers: Int?
)