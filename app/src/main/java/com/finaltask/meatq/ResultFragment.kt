package com.finaltask.meatq

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.finaltask.meatq.databinding.FragmentResultBinding
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private lateinit var file: File
    private val resultViewModel : ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageBitmap = arguments?.getString("bitmap")
        if(imageBitmap != null){

            val image = decodeImage(imageBitmap)
            binding.ivPhoto.setImageBitmap(image)
            saveBitmapToFile(image,"image.jpg")

        }


        lifecycleScope.launch {
            val result = resultViewModel.uploadImage(file).predictedLabel?.get(0)
            binding.tvCalculation.text = when (result) {
                "notfresh" -> "Not Fresh"
                "fresh" -> "Fresh"
                else -> "Failed"
            }
        }



        binding.btnBack.setOnClickListener {

            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)

        }

    }

    private fun decodeImage(img: String): Bitmap {
        val imageBytes = Base64.decode(img, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun saveBitmapToFile(bitmap: Bitmap, fileName: String) {
        val storageDir = Environment.getExternalStorageDirectory().toString() // Get external storage directory
        file = File(storageDir, fileName)

        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Compress bitmap and save to the file
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



}