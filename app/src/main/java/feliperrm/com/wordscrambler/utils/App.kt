package feliperrm.com.wordscrambler.utils

import android.app.Application
import androidx.room.Room
import com.onesignal.OneSignal
import feliperrm.com.wordscrambler.data.RoomDatabase
import timber.log.Timber

/**
 * Created by FelipeRRM on 4/2/2019.
 */
class App : Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java, "db_v1"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
        application = this
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var application: App
    }

}