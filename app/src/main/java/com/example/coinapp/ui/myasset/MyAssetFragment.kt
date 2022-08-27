package com.example.coinapp.ui.myasset

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.coinapp.R
import com.example.coinapp.base.BaseFragment
import com.example.coinapp.databinding.FragmentHomeBinding

class MyAssetFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_my_asset) {
    private val _myAssetViewModel: MyAssetViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = MyAssetFragment()
    }
}