package by.anegin.testapp.features.clients.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.anegin.testapp.core.util.observe
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.databinding.FragmentClientsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ClientsListFragment : Fragment(R.layout.fragment_clients_list) {

    private var binding: FragmentClientsListBinding? = null

    private val viewModel: ClientsListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentClientsListBinding.inflate(inflater, container, false)
            .also { this.binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ClientsAdapter(viewModel::showEditClientScreen)
        binding?.recyclerviewClients?.adapter = adapter

        binding?.buttonAddClient?.setOnClickListener {
            viewModel.showAddClientScreen()
        }

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