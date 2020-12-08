package feliperrm.com.wordscrambler.ui.game


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import feliperrm.com.wordscrambler.databinding.FragmentGameBinding
import feliperrm.com.wordscrambler.ui.MainViewModel
import feliperrm.com.wordscrambler.ui.SHARED_PREF_NAME
import feliperrm.com.wordscrambler.utils.App
import feliperrm.com.wordscrambler.utils.getViewModel

class GameFragment : Fragment() {

    private val activityVm by lazy {
        activity?.getViewModel {
            MainViewModel(
                context?.getSharedPreferences(
                    SHARED_PREF_NAME, Context.MODE_PRIVATE
                )
            )
        }
    }
    private val vm by lazy {
        getViewModel {
            GameViewModel(
                App.application.db,
                activityVm?.minWordsSize?.value ?: MainViewModel.DEFAULT_MIN_SIZE,
                activityVm?.maxWordSize?.value ?: MainViewModel.DEFAULT_MAX_SIZE
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentGameBinding.inflate(inflater, container, false).apply {
            viewModel = vm
            lifecycleOwner = this@GameFragment.viewLifecycleOwner
        }.root


}
