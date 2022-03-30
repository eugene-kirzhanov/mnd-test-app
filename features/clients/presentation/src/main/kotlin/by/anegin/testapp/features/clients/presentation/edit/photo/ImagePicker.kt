package by.anegin.testapp.features.clients.presentation.edit.photo

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileNotFoundException
import timber.log.Timber

class ImagePicker(
    private val fragment: Fragment,
    private val listener: Callback
) {

    enum class Error {
        STORAGE_PERMISSION_DENIED,
        INVALID_IMAGE,
        FILE_NOT_FOUND,
        NO_ASSOCIATED_GALLERY_APPS
    }

    interface Callback {
        fun onImageChooseSuccess(imageUri: Uri)
        fun onImageChooseError(error: Error)
    }

    private var pendingImageUri: Uri? = null

    private val requestPermissionForImageChooser = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            pendingImageUri?.let {
                try {
                    if (it.isImage(fragment.requireContext())) {
                        listener.onImageChooseSuccess(it)
                    } else {
                        listener.onImageChooseError(Error.INVALID_IMAGE)
                    }
                } catch (e: FileNotFoundException) {
                    listener.onImageChooseError(Error.FILE_NOT_FOUND)
                }
            }
        } else {
            listener.onImageChooseError(Error.STORAGE_PERMISSION_DENIED)
        }
        pendingImageUri = null
    }

    private val pickImageFromGalleryLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), PickImageLauncherCallback()
    )

    private inner class PickImageLauncherCallback : ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result: ActivityResult?) {
            if (result?.resultCode == Activity.RESULT_OK) {
                var imageUri: Uri? = null

                // try to get image through clip data
                val clipData = result.data?.clipData
                if (clipData != null && clipData.itemCount > 0) {
                    imageUri = clipData.getItemAt(0).uri
                }

                if (imageUri == null) {
                    var isCamera = true
                    if (result.data?.data != null) {
                        isCamera = result.data?.action == MediaStore.ACTION_IMAGE_CAPTURE
                    }
                    imageUri = if (isCamera || result.data?.data == null) {
                        getCaptureImageOutputUri()
                    } else {
                        result.data?.data
                    }
                }

                if (imageUri != null) {
                    if (isReadExternalStoragePermissionsRequired(imageUri)) {
                        pendingImageUri = imageUri
                        requestPermissionForImageChooser.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    } else {
                        try {
                            if (imageUri.isImage(fragment.requireContext())) {
                                listener.onImageChooseSuccess(imageUri)
                            } else {
                                listener.onImageChooseError(Error.INVALID_IMAGE)
                            }
                        } catch (e: FileNotFoundException) {
                            listener.onImageChooseError(Error.FILE_NOT_FOUND)
                        }
                    }
                }
            }
        }
    }

    fun pickImageFromGallery() {
        val intents = getGalleryIntents(fragment.requireContext().packageManager)
        if (intents.isEmpty()) {
            listener.onImageChooseError(Error.NO_ASSOCIATED_GALLERY_APPS)
            return
        }

        val launchIntent = if (intents.size == 1) {
            intents.first()
        } else {
            val target = intents[intents.size - 1]
            val intentsForChooser = intents.toMutableList()
            intentsForChooser.removeAt(intents.size - 1)

            Intent.createChooser(target, "Select application").apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, intentsForChooser.toTypedArray())
            }
        }
        pickImageFromGalleryLauncher.launch(launchIntent)
    }

    private fun isReadExternalStoragePermissionsRequired(uri: Uri): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            try {
                fragment.requireContext().contentResolver.openInputStream(uri)?.close()
                false
            } catch (e: Exception) {
                true
            }
        } else {
            false
        }
    }

    private fun getGalleryIntents(packageManager: PackageManager): List<Intent> {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        return packageManager.queryIntentActivities(pickIntent, 0).map { info ->
            Intent(pickIntent).apply {
                component = ComponentName(info.activityInfo.packageName, info.activityInfo.name)
                setPackage(info.activityInfo.packageName)
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*"))
            }
        }
    }

    private fun getCaptureImageOutputUri(): Uri? {
        var outputFileUri: Uri? = null
        val getImage = fragment.context?.externalCacheDir
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, "pickedImage.jpg"))
        }
        return outputFileUri
    }

    private fun Uri.isImage(context: Context): Boolean {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        return try {
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(this), null, options)
            options.outWidth != -1 && options.outHeight != -1
        } catch (e: Exception) {
            Timber.e(e)
            false
        }
    }
}