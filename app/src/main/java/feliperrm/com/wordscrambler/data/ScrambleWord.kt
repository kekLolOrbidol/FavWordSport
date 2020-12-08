package feliperrm.com.wordscrambler.data

/**
 * Created by FelipeRRM on 03/04/2019.
 */
class ScrambleWord(val word: Word) {

    private val scrambledText = scramble()

    private fun scramble(): String {
        return String(word.word.asIterable().shuffled().toCharArray())
    }

    override fun toString(): String {
        return scrambledText
    }
}