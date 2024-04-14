package id.ac.unri.submission_one.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import id.ac.unri.submission_one.databinding.FragmentImageBinding
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {

    private lateinit var edtImageName: EditText
    private lateinit var btnChooseFile: Button
    private lateinit var imageToUpload: ImageView
    private lateinit var btnUpload: Button

    private lateinit var storageRef: StorageReference
    private var selectedImageUri: Uri? = null

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)

        edtImageName = binding.edtImgName
        btnChooseFile = binding.btnChooseFile
        imageToUpload = binding.imageToUpload
        btnUpload = binding.btnUpload

        storageRef = FirebaseStorage.getInstance().reference

        btnChooseFile.setOnClickListener {
            selectImageFromGallery()
        }

        btnUpload.setOnClickListener {
            uploadImage()
        }
        return binding.root
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    private fun uploadImage() {
        if (selectedImageUri != null) {
            val imageName = edtImageName.text.toString()
            if (imageName.isNotEmpty()) {
                val imagesRef = storageRef.child("images")
                val imageFileName = "$imageName.jpg"
                val imageRef = imagesRef.child(imageFileName)

                imageRef.putFile(selectedImageUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), "Failed to get image URL", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Image name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
                imageToUpload.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 101
    }
}