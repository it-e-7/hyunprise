package com.hyunprise.android.ui.pointshop

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.hyunprise.android.R
import com.hyunprise.android.databinding.FragmentPointShopBinding


class PointShopFragment : Fragment() {

    private var _binding: FragmentPointShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPointShopBinding.inflate(inflater, container, false)

        binding.pointShopBackBtn.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val controller = MediaController(requireContext())
        binding.myPointVideo.setMediaController(controller)
        val videoUri = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.my_point_video)
        binding.myPointVideo.setVideoURI(videoUri)
        binding.myPointVideo.start()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.myPointVideo.stopPlayback()
        binding.myPointVideo.setMediaController(null)
        _binding = null
    }
}