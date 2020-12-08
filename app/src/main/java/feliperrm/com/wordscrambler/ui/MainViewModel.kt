package feliperrm.com.wordscrambler.ui

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by FelipeRRM on 4/7/2019.
 * This View Model is scopes to the Activity, therefore is shared between all fragments
 * */

const val SHARED_PREF_NAME = "global_settings_pref"

class MainViewModel(private val sharedPreferences: SharedPreferences?) : ViewModel() {

    val minWordsSize = MutableLiveData<Int>()
    val maxWordSize = MutableLiveData<Int>()

    fun loadValues() {
        minWordsSize.value = sharedPreferences?.getInt(MIN_WORD_SIZE, DEFAULT_MIN_SIZE)
        maxWordSize.value = sharedPreferences?.getInt(MAX_WORD_SIZE, DEFAULT_MAX_SIZE)
    }

    fun updateWordOptions(minSize: Int, maxSize: Int) {
        if (minWordsSize.value != minSize) {
            minWordsSize.value = minSize
            sharedPreferences?.edit()?.putInt(MIN_WORD_SIZE, minSize)?.apply()
        }
        if (maxWordSize.value != maxSize) {
            maxWordSize.value = maxSize
            sharedPreferences?.edit()?.putInt(MAX_WORD_SIZE, maxSize)?.apply()
        }
    }

    companion object {
        private const val MIN_WORD_SIZE = "minWordSize"
        public const val DEFAULT_MIN_SIZE = 2
        private const val MAX_WORD_SIZE = "maxWordSize"
        public const val DEFAULT_MAX_SIZE = 11
    }

}