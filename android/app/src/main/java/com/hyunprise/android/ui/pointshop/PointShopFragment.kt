package com.hyunprise.android.ui.pointshop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyunprise.android.HomeActivity
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
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}