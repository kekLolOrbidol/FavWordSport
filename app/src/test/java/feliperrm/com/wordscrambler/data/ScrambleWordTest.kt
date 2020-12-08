package feliperrm.com.wordscrambler.data

import org.junit.Test

/**
 * Created by FelipeRRM on 09/04/2019.
 */
class ScrambleWordTest {

    @Test
    fun testScrambledTest() {
        val word1 = Word(word ="car")
        val scrambled1 = ScrambleWord(word1)
        val scrambledString = scrambled1.toString()

    }
}