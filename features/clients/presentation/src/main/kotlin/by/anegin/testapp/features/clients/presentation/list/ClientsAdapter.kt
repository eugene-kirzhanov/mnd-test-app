package by.anegin.testapp.features.clients.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import by.anegin.testapp.features.clients.domain.models.Client
import com.bumptech.glide.RequestManager

internal class ClientsAdapter(
    private val glide: RequestManager,
    private val onEditClick: (clientId: Long) -> Unit
) : ListAdapter<Client, ClientViewHolder>(ClientUiModelsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder =
        ClientViewHolder(parent, glide, onEditClick)

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ClientUiModelsDiffCallback : DiffUtil.ItemCallback<Client>() {
        override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean =
            oldItem == newItem
    }
}