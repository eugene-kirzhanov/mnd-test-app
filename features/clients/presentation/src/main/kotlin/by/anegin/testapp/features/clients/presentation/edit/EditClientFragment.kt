package by.anegin.testapp.features.clients.presentation.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.anegin.testapp.core.util.observe
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.databinding.FragmentEditClientBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class EditClientFragment : Fragment(R.layout.fragment_edit_client) {

    private val viewModel: EditClientViewModel by viewModels()

    private var binding: FragmentEditClientBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentEditClientBinding.inflate(inflater, container, false)
            .also { this.binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.let(::setupViewPager)

        binding?.buttonPrev?.setOnClickListener {
            viewModel.onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            viewModel.onNextPressed()
        }

        viewModel.toolbarTitle.observe(viewLifecycleOwner) { title ->
            binding?.toolbar?.setTitle(title)
        }
        viewModel.nextButtonTitle.observe(viewLifecycleOwner) { buttonTitle ->
            binding?.buttonNext?.setText(buttonTitle)
        }
        viewModel.currentPage.observe(viewLifecycleOwner) { currentPage ->
            binding?.viewPager?.let {
                if (it.currentItem != currentPage) {
                    it.setCurrentItem(currentPage, true)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.onBackPressed()
        }
    }

    private fun setupViewPager(binding: FragmentEditClientBinding) {
        binding.viewPager.adapter = EditClientPagerAdapter(childFragmentManager, lifecycle, EditClientViewModel.PAGES)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.onPageSelected(position)
            }
        })

        // use TabLayout with custom tabBackground as indicator for ViewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val ARG_CLIENT_ID = "client_id"

        fun makeArguments(clientId: Long) = bundleOf(ARG_CLIENT_ID to clientId)
    }
}