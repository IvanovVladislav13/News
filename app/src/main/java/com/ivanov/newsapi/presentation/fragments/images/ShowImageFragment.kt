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
import com.ivanov.newsapi.presentation.fragments.images.util.ZOOM_MAX
import com.ivanov.newsapi.presentation.fragments.images.util.ZOOM_MIN
import com.ivanov.newsapi.presentation.fragments.images.util.ZoomMultiTouch

class ShowImageFragment : Fragment(R.layout.fragment_show_image) {

    private val binding by viewBinding(FragmentShowImageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        showImage()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnClickListeners() {
        binding.imageOverview.setOnTouchListener { v, event ->
            val view: ImageView = v as ImageView
            view.bringToFront()
            ZoomMultiTouch.viewTransformation(view, event)
            return@setOnTouchListener true
        }

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
        if (binding.imageOverview.scaleX < ZOOM_MAX
            && binding.imageOverview.scaleY < ZOOM_MAX
        ) {
            val x = binding.imageOverview.scaleX
            val y = binding.imageOverview.scaleY

            binding.imageOverview.scaleX = x + 1
            binding.imageOverview.scaleY = y + 1
        }
    }

    private fun zoomOut() {
        if (binding.imageOverview.scaleX > ZOOM_MIN
            && binding.imageOverview.scaleY > ZOOM_MIN
        ) {
            val x = binding.imageOverview.scaleX
            val y = binding.imageOverview.scaleY

            if ((x == ZOOM_MIN && y == ZOOM_MIN) || x - 1 <= ZOOM_MIN) {
                binding.imageOverview.scaleX = ZOOM_MIN
                binding.imageOverview.scaleY = ZOOM_MIN
                binding.imageOverview.animate()
                    .x(ZOOM_MIN).y(ZOOM_MIN).setDuration(500).start()
            } else {
                binding.imageOverview.scaleX = x - 1
                binding.imageOverview.scaleY = y - 1
            }
        }
    }
}