package com.ivanov.newsapi.presentation.fragments.images

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.BuildConfig
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.FragmentSelectImageBinding
import com.ivanov.newsapi.presentation.activities.MainActivity
import com.ivanov.newsapi.presentation.fragments.images.util.ImageUtil

class SelectImageFragment : Fragment(R.layout.fragment_select_image) {

    private val binding by viewBinding(FragmentSelectImageBinding::bind)
    private var tempUri: Uri? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            if (imageUri != null)
                toNextFragment(imageUri)
        }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success)
                toNextFragment(tempUri)
        }

    private val requestPermissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted)
                takeImage()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.pickImage.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.takeImage.setOnClickListener {
            requestPermissionCamera.launch(Manifest.permission.CAMERA)
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenCreated {
            ImageUtil.createFile(requireContext())?.also { file ->
                tempUri = FileProvider.getUriForFile(
                    requireContext(),
                    "${BuildConfig.APPLICATION_ID}.provider",
                    file
                )
                takePicture.launch(tempUri)
            }
        }
    }

    private fun toNextFragment(imageUri: Uri?) {
        val bundle = Bundle()
        bundle.putString("imageUri", imageUri.toString())
        (activity as MainActivity).navController.navigate(
            R.id.action_navigation_select_image_fragment_to_navigation_show_image_fragment,
            bundle
        )
    }
}