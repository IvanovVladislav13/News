package com.ivanov.newsapi.presentation.fragments.images

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.FragmentShowImageBinding
import com.ivanov.newsapi.presentation.activities.MainActivity
import com.ivanov.newsapi.presentation.fragments.images.util.ImageUtil
import com.ivanov.newsapi.presentation.fragments.images.util.ZoomMultiTouch

class ShowImageFragment : Fragment(R.layout.fragment_show_image) {

    private val binding by viewBinding(FragmentShowImageBinding::bind)
    private val zoom = ZoomMultiTouch()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnListener()
        showImage()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnListener() {
        binding.imageOverview.setOnTouchListener { v, event ->
            val view: ImageView = v as ImageView
            view.bringToFront()
            zoom.viewTransformation(view, event)
            return@setOnTouchListener true
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
}