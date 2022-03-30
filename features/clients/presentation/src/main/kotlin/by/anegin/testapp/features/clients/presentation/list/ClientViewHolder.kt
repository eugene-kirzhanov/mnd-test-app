package by.anegin.testapp.features.clients.presentation.list

import android.view.ViewGroup
import android.widget.ImageView
import by.anegin.testapp.core.util.ViewBindingViewHolder
import by.anegin.testapp.features.clients.domain.models.Client
import by.anegin.testapp.features.clients.domain.models.WeightUnits
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.databinding.ItemClientBinding
import com.bumptech.glide.RequestManager
import java.text.DateFormat
import java.util.Date
import java.util.Locale

internal class ClientViewHolder(
    parent: ViewGroup,
    private val glide: RequestManager,
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

        if (client.photoUri.isNullOrBlank()) {
            glide.clear(binding.imagePhoto)
            binding.imagePhoto.scaleType = ImageView.ScaleType.CENTER_INSIDE
            binding.imagePhoto.setImageResource(R.drawable.ic_baseline_person_24)
        } else {
            glide
                .load(client.photoUri)
                .centerCrop()
                .into(binding.imagePhoto)
        }
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