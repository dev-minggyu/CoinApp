package com.example.coinapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.coinapp.R
import com.example.coinapp.base.BaseFragment
import com.example.coinapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val _homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = _homeViewModel
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}