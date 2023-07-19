package com.finaltask.meatq

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.finaltask.meatq.databinding.FragmentHomeBinding
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val bundle = Bundle()
    private val pickImage = 1
    private val takePhoto = 2
    lateinit var imageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                1001
            )
        } else {
            // Camera permission is already granted
            // Proceed with camera operations
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivPhoto.setOnClickListener {
            openImagePicker()
        }
        binding.btnResult.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_resultFragment,bundle)
        }
        homeViewModel.image.observe(viewLifecycleOwner){
            binding.btnResult.isEnabled = it != "empty"
        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnResult.isEnabled = false
    }

    private fun openImagePicker() {
        val options = arrayOf<CharSequence>("Ambil Gambar", "Pilih dari Galeri", "Batal")
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pilih salah satu")
        builder.setItems(options) { dialog, item ->
            when (options[item]) {
                "Ambil Gambar" -> capturePhoto()
                "Pilih dari Galeri" -> chooseFromGallery()
                "Batal" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK && data != null) {

            val imageUri: Uri? = data.data
            imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            binding.ivPhoto.setImageBitmap(imageBitmap)
            homeViewModel.changeImage(imageBitmap.toString())
            bundle.putString("bitmap",encodeImage(imageBitmap))

        } else if (requestCode == takePhoto && resultCode == Activity.RESULT_OK && data != null) {

            val imageBitmap: Bitmap = data.extras?.get("data") as Bitmap
            binding.ivPhoto.setImageBitmap(imageBitmap)
            homeViewModel.changeImage(imageBitmap.toString())
            bundle.putString("bitmap",encodeImage(imageBitmap))
            Log.e("Msg",encodeImage(imageBitmap)!!)

        }
    }

    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, takePhoto)
    }

    private fun chooseFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


}