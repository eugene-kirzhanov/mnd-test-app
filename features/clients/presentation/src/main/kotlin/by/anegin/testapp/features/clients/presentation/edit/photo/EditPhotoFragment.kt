package by.anegin.testapp.features.clients.presentation.edit.photo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.anegin.testapp.core.util.observe
import by.anegin.testapp.features.clients.presentation.R
import by.anegin.testapp.features.clients.presentation.databinding.FragmentEditClientPhotoBinding
import by.anegin.testapp.features.clients.presentation.edit.EditClientViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EditPhotoFragment : Fragment(R.layout.fragment_edit_client_photo), ImageChooser.Callback {

    private val viewModel: EditClientViewModel by viewModels({ requireParentFragment() })

    private var binding: FragmentEditClientPhotoBinding? = null

    private var imageChooser: ImageChooser? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        imageChooser = ImageChooser(this, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentEditClientPhotoBinding.inflate(inflater, container, false)
            .also { this.binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {

            buttonPickPhoto.setOnClickListener {
                imageChooser?.pickImageFromGallery()
            }

            buttonRemovePhoto.setOnClickListener {
                viewModel.clearPhoto()
            }

            viewModel.client.observe(viewLifecycleOwner) { client ->
                if (client.photoUri.isNullOrBlank()) {
                    Glide.with(this@EditPhotoFragment).clear(imagePhoto)

                    imagePhoto.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    imagePhoto.setImageResource(R.drawable.ic_baseline_person_24)

                    buttonRemovePhoto.isEnabled = false
                } else {
                    Glide.with(this@EditPhotoFragment)
                        .load(client.photoUri)
                        .centerCrop()
                        .into(imagePhoto)

                    buttonRemovePhoto.isEnabled = true
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        imageChooser = null
        super.onDetach()
    }

    override fun onImageChooseSuccess(imageUri: Uri) {
        viewModel.setPhoto(imageUri.toString())
    }

    override fun onImageChooseError(error: ImageChooser.Error) {
        val errorMessage = when (error) {
            ImageChooser.Error.STORAGE_PERMISSION_DENIED -> R.string.error_storage_permission_denied
            ImageChooser.Error.INVALID_IMAGE -> R.string.error_invalid_file_format
            ImageChooser.Error.FILE_NOT_FOUND -> R.string.error_file_not_found
            ImageChooser.Error.NO_ASSOCIATED_GALLERY_APPS -> R.string.error_gallery_app_not_found
        }
        Timber.e(getString(errorMessage))
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}