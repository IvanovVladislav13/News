package com.ivanov.newsapi.presentation.fragments.images

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.BuildConfig
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.FragmentSelectImageBinding
import com.ivanov.newsapi.presentation.activities.MainActivity
import com.ivanov.newsapi.presentation.fragments.images.util.ImageUtil
import kotlinx.coroutines.launch

class SelectImageFragment : Fragment(R.layout.fragment_select_image) {

    private val binding by viewBinding(FragmentSelectImageBinding::bind)
    private var tempUri: Uri? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            toNextFragment(imageUri)
        }

    private val takePictureResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if(success)
                toNextFragment(tempUri)
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
            takeImage()
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenCreated {
            ImageUtil.createFile(requireContext())?.also {
                tempUri = FileProvider.getUriForFile(
                    requireContext(),
                    "${BuildConfig.APPLICATION_ID}.provider",
                    it
                )
                takePictureResult.launch(tempUri)
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