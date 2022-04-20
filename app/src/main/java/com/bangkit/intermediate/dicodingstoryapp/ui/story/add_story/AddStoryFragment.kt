package com.bangkit.intermediate.dicodingstoryapp.ui.story.add_story

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bangkit.intermediate.dicodingstoryapp.data.remote.request.AddStoryRequest
import com.bangkit.intermediate.dicodingstoryapp.data.repository.Result
import com.bangkit.intermediate.dicodingstoryapp.databinding.FragmentAddStoryBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseFragment
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.ViewModelFactory
import com.bangkit.intermediate.dicodingstoryapp.ui.story.AddStoryViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.story.camera.CameraActivity
import com.bangkit.intermediate.dicodingstoryapp.ui.story.story_list.StoryListFragment
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class AddStoryFragment : BaseFragment() {
    private var getFile: File? = null
    private var isBackCamera = true
    private var isFromCamera = true
    private val filenameFormat = "dd-MM-yyyy"

    private val timestamp =
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis())

    private val launcherIntentCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != CAMERA_X_RESULT) return@registerForActivityResult
            val myFile = it.data?.getSerializableExtra("picture") as File
            getFile = myFile
            isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val bitmap = BitmapFactory.decodeFile(myFile.path)

            val orientation =
                requireActivity().applicationContext.resources.configuration.orientation

            val result = if (orientation == Configuration.ORIENTATION_PORTRAIT) rotateBitmap(bitmap,
                isBackCamera) else bitmap

            (binding as FragmentAddStoryBinding).imageViewStoryDetail.setImageBitmap(result)
            isFromCamera = true
        }

    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != RESULT_OK) return@registerForActivityResult
            val selectedImage = it.data?.data as Uri
            val myFile = uriToFile(selectedImage, requireContext())
            getFile = myFile
            (binding as FragmentAddStoryBinding).imageViewStoryDetail.setImageURI(selectedImage)
            isFromCamera = false
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            val binding = binding as FragmentAddStoryBinding
            val file = savedInstanceState.getSerializable(STATE_IMAGE) as File?

            if (file != null) {
                getFile = file
                val bitmap = BitmapFactory.decodeFile(file.path)
                val result = if (!isFromCamera) rotateBitmap(bitmap, isBackCamera) else bitmap
                binding.imageViewStoryDetail.setImageBitmap(result)
            }

            val description = savedInstanceState.getString(STATE_DESCRIPTION)

            if (description != null)
                binding.editTextDescription.setText(description)
        }

        setupView(binding)
        setupViewModel()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun setupView(viewBinding: Any) {
        if (allPermissionGranted()) return

        ActivityCompat.requestPermissions(
            requireActivity(),
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS,
        )
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getStoryInstance()
        val viewModel: AddStoryViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        val binding = binding as FragmentAddStoryBinding
        binding.buttonCamera.setOnClickListener { startCamera() }
        binding.buttonGallery.setOnClickListener { startGallery() }
        binding.buttonUpload.setOnClickListener { uploadStory() }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_CODE_PERMISSIONS && allPermissionGranted()) return
        Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val binding = binding as FragmentAddStoryBinding
        val text = binding.editTextDescription.text
        if (!(text.isNullOrBlank() || text.isNullOrEmpty()))
            outState.putString(STATE_DESCRIPTION, "${binding.editTextDescription.text}")
        if (getFile != null)
            outState.putSerializable(STATE_IMAGE, getFile as File)
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCamera.launch(intent)
    }

    private fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
        val matrix = Matrix()

        return if (isBackCamera) {
            matrix.postRotate(90f)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else {
            matrix.postRotate(-90f)
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }

    private fun uriToFile(selectedImage: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val myFile = createTempFile(context)
        val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream.read(buffer).also { len = it } > 0) outputStream.write(buffer, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private fun createTempFile(context: Context): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timestamp, ".jpg", storageDir)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadStory() {
        val binding = binding as FragmentAddStoryBinding
        val text = binding.editTextDescription.text
        if (getFile == null) {
            Toast.makeText(requireContext(), "Please input picture", Toast.LENGTH_SHORT).show()
        } else if (text.isNullOrEmpty() || text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Please input description", Toast.LENGTH_SHORT).show()
        } else {
            val file = reduceImageFile(getFile as File)
            val description = "${text.trim()}".toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

            val imageMultipart =
                MultipartBody.Part.createFormData("photo", file.name, requestImageFile)

            val request = AddStoryRequest(imageMultipart, description)
            val viewModel = viewModel as AddStoryViewModel
            viewModel.getToken(requireContext())

            viewModel.addNewStory(request)?.observe(requireActivity()) { result ->
                if (result == null) return@observe
                val progressBar = binding.progressBarAddStory

                when (result) {
                    Result.Loading -> progressBar.visibility = View.VISIBLE
                    is Result.Error -> showError(progressBar, result.error)
                    is Result.Success -> {
                        Toast.makeText(requireContext(), result.data, Toast.LENGTH_SHORT).show()
                        val intent = Intent()
                        intent.putExtra("isSubmitted", true)
                        activity?.setResult(StoryListFragment.ADD_STORY_RESULT, intent)
                        activity?.finish()
                    }
                }
            }
        }
    }

    private fun reduceImageFile(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int

        do {
            val bitmapStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bitmapStream)
            val bitmapByteArray = bitmapStream.toByteArray()
            streamLength = bitmapByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)

        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val STATE_IMAGE = "state_image"
        private const val STATE_DESCRIPTION = "state_description"
    }
}