package feliperrm.com.wordscrambler.ui.menu


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import feliperrm.com.wordscrambler.R
import feliperrm.com.wordscrambler.databinding.FragmentStatisticsBinding
import feliperrm.com.wordscrambler.ui.statistics.StatisticsViewModel
import feliperrm.com.wordscrambler.utils.App
import feliperrm.com.wordscrambler.utils.getViewModel
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment : Fragment() {

    private val vm by lazy { getViewModel { StatisticsViewModel(App.application.db) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentStatisticsBinding.inflate(inflater, container, false).apply {
            viewModel = vm
            lifecycleOwner = this@StatisticsFragment.viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        answersPieChart.description.isEnabled = false
        answersPieChart.centerText = getString(R.string.answers)
        answersPieChart.setCenterTextSize(28f)
        answersPieChart.legend.isEnabled = false
    }
}
