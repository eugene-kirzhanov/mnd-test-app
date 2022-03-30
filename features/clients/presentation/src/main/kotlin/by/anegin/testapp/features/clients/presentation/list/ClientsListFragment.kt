package by.anegin.testapp.features.clients.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import by.anegin.testapp.core.di.ViewModelInjection
import by.anegin.testapp.core.util.observe
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.databinding.FragmentClientsListBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

internal class ClientsListFragment : DaggerFragment(R.layout.fragment_clients_list) {

    private var binding: FragmentClientsListBinding? = null

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ClientsListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentClientsListBinding.inflate(inflater, container, false)
            .also { this.binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ClientsAdapter(viewModel::onClientEditClick)
        binding?.recyclerviewClients?.adapter = adapter

        viewModel.clients.observe(viewLifecycleOwner) { clients ->
            adapter.submitList(clients)
        }

        viewModel.isEmptyLabelVisible.observe(viewLifecycleOwner) { isVisible ->
            binding?.textNoClients?.isVisible = isVisible
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}