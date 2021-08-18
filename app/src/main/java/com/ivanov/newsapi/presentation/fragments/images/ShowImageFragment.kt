package com.ivanov.newsapi.presentation.fragments.images

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.FragmentShowImageBinding
import com.ivanov.newsapi.presentation.activities.MainActivity
import com.ivanov.newsapi.presentation.fragments.images.util.ImageUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.InputStream

class ShowImageFragment : Fragment(R.layout.fragment_show_image) {

    private val binding by viewBinding(FragmentShowImageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showImage()
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