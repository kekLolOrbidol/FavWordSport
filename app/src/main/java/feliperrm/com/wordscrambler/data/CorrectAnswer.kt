package feliperrm.com.wordscrambler.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by FelipeRRM on 4/2/2019.
 */
@Entity
data class CorrectAnswer(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var wordId: Int?,
    var secondsPlayed: Int?,
    var wrongAnswers: Int?
)