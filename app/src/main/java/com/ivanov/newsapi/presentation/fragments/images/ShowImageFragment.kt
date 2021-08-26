package com.ivanov.newsapi.presentation.fragments.images

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.FragmentShowImageBinding
import com.ivanov.newsapi.presentation.activities.MainActivity
import com.ivanov.newsapi.presentation.fragments.images.util.ImageUtil
import com.ivanov.newsapi.presentation.fragments.images.util.ZOOM_MAX
import com.ivanov.newsapi.presentation.fragments.images.util.ZOOM_MIN

class ShowImageFragment : Fragment(R.layout.fragment_show_image) {

    private val binding by viewBinding(FragmentShowImageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setScale()
        setOnClickListeners()
        showImage()
    }

    private fun setScale() {
        binding.imageOverview.minimumScale = ZOOM_MIN
        binding.imageOverview.maximumScale = ZOOM_MAX
    }

    private fun setOnClickListeners() {
        binding.zoomIn.setOnClickListener {
            zoomIn()
        }

        binding.zoomOut.setOnClickListener {
            zoomOut()
        }
    }

    private fun showImage() {
        val uri = Uri.parse(arguments?.getString("imageUri"))
        try {
            val bitmap = ImageUtil.decodeSampledBitmapFromResource(requireContext(), uri)
            binding.imageOverview.setImageBitmap(bitmap)
        } catch (e: Exception) {
            (activity as MainActivity).navController.popBackStack()
        }
    }

    private fun zoomIn() {
        if (binding.imageOverview.scale < ZOOM_MAX) {
            binding.imageOverview.scale += 1
        }
    }

    private fun zoomOut() {
        if (binding.imageOverview.scale > ZOOM_MIN) {
            val scale = binding.imageOverview.scale

            if (scale - 1 <= ZOOM_MIN) {
                binding.imageOverview.scale = ZOOM_MIN
            } else {
                binding.imageOverview.scale -= 1
            }
        }
    }
}