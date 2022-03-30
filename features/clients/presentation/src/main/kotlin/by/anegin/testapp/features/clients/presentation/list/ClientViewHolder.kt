package by.anegin.testapp.features.clients.presentation.list

import android.view.ViewGroup
import by.anegin.testapp.core.util.ViewBindingViewHolder
import by.anegin.testapp.features.clients.domain.models.Client
import by.anegin.testapp.features.clients.domain.models.WeightUnits
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.databinding.ItemClientBinding
import java.text.DateFormat
import java.util.Date
import java.util.Locale

internal class ClientViewHolder(
    parent: ViewGroup,
    private val onEditClick: (clientId: Long) -> Unit
) : ViewBindingViewHolder<ItemClientBinding>(parent, ItemClientBinding::inflate) {

    private var clientId: Long? = null

    init {
        binding.buttonEdit.setOnClickListener {
            clientId?.let(onEditClick)
        }
    }

    fun bind(client: Client) {
        this.clientId = client.id

        binding.textBirthdateValue.text = formatDate(client.dateOfBirth)
        binding.textWeightValue.text = formatWeight(client.weight, client.weightUnits)
    }

    private fun formatDate(date: Date): String =
        DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault()).format(date)

    private fun formatWeight(weight: Float, units: WeightUnits): String =
        "$weight${units.getTitle()}"

    private fun WeightUnits.getTitle() = when (this) {
        WeightUnits.LB -> binding.root.context.getString(R.string.weight_units_lb)
        WeightUnits.KG -> binding.root.context.getString(R.string.weight_units_kg)
    }
}