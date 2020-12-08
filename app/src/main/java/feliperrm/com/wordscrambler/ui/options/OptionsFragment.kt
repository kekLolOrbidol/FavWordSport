package feliperrm.com.wordscrambler.ui.options


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import feliperrm.com.wordscrambler.R
import feliperrm.com.wordscrambler.ui.MainViewModel
import feliperrm.com.wordscrambler.ui.SHARED_PREF_NAME
import feliperrm.com.wordscrambler.utils.getViewModel
import kotlinx.android.synthetic.main.fragment_options.*
import timber.log.Timber

class OptionsFragment : Fragment() {

    private val activityVm by lazy {
        activity?.getViewModel {
            MainViewModel(
                context?.getSharedPreferences(
                    SHARED_PREF_NAME, Context.MODE_PRIVATE
                )
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_options, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rangeBar.setOnRangeBarChangeListener { rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue ->
            Timber.d(
                "leftPinIndex: %s, leftPinValue: %s, rightPinIndex: %s, rightPinValue: %s",
                leftPinIndex,
                leftPinValue,
                rightPinIndex,
                rightPinValue
            )
            activityVm?.updateWordOptions(leftPinValue.toInt(), rightPinValue.toInt())
        }
        rangeBar.setRangePinsByValue(
            activityVm?.minWordsSize?.value?.toFloat() ?: 2f,
            activityVm?.maxWordSize?.value?.toFloat() ?: 11f
        )
    }
}
