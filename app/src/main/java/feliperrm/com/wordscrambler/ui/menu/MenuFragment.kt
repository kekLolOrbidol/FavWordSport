package feliperrm.com.wordscrambler.ui.menu


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import feliperrm.com.wordscrambler.R
import feliperrm.com.wordscrambler.databinding.FragmentMenuBinding
import feliperrm.com.wordscrambler.utils.getViewModel
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private val vm by lazy { getViewModel { MenuViewModel(resources.openRawResource(R.raw.words)) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentMenuBinding.inflate(inflater, container, false).apply {
            viewModel = vm
            lifecycleOwner = this@MenuFragment.viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playGameButton.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_gameFragment) }
        statisticsButton.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_statisticsFragment) }
        optionsButton.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_optionsFragment) }
    }
}
