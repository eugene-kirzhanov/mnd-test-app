package by.anegin.testapp.features.clients.presentation.edit.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.anegin.testapp.core.util.observe
import by.anegin.testapp.features.clients.domain.models.WeightUnits
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.databinding.FragmentEditClientWeightBinding
import by.anegin.testapp.features.clients.presentation.edit.EditClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditWeightFragment : Fragment(R.layout.fragment_edit_client_weight) {

    private val viewModel: EditClientViewModel by viewModels({ requireParentFragment() })

    private var binding: FragmentEditClientWeightBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentEditClientWeightBinding.inflate(inflater, container, false)
            .also { this.binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel.client.observe(viewLifecycleOwner) { client ->
                if (client.weight > 0) {
                    editWeight.setText(client.weight.toString())
                    editWeight.setSelection(editWeight.length())
                }
                when (client.weightUnits) {
                    WeightUnits.LB -> if (!radioWeightLb.isChecked) radioWeightLb.isChecked = true
                    WeightUnits.KG -> if (!radioWeightKg.isChecked) radioWeightKg.isChecked = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.editWeight?.apply {
            post {
                setSelection(length())
                requestFocus()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding?.let {
            val weightText = it.editWeight.toString().trim()
            val weight = if (weightText.isEmpty()) 0f else weightText.toFloatOrNull() ?: return@let
            val weightUnits = if (it.radioWeightLb.isChecked) WeightUnits.LB else WeightUnits.KG
            viewModel.setWeight(weight, weightUnits)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}