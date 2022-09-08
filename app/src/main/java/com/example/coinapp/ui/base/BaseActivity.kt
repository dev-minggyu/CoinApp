package com.example.coinapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView<T>(this, layoutId).apply {
            lifecycleOwner = this@BaseActivity
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}