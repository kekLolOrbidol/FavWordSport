package feliperrm.com.wordscrambler.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import feliperrm.com.wordscrambler.R
import feliperrm.com.wordscrambler.utils.getViewModel

class MainActivity : AppCompatActivity() {

    private val activityVm by lazy {
        getViewModel { MainViewModel(getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)) }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityVm.loadValues()
    }
}
