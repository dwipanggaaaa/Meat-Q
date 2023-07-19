package com.finaltask.meatq

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.finaltask.meatq.databinding.FragmentWelcomeBinding
import android.Manifest


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val requestCode = 1001

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode
            )
        } else {
            // Camera permission is already granted
            // Proceed with camera operations
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                requestCode
            )
        } else {
            // Camera permission is already granted
            // Proceed with camera operations
        }
        binding.clWelcome.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment2_to_homeFragment)
        }
        binding.btnAbout.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment2_to_aboutFragment)
        }
        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment2_to_helpFragment)
        }
    }
}