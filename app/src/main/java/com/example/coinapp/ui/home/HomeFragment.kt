package com.example.coinapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.coinapp.R
import com.example.coinapp.base.BaseFragment
import com.example.coinapp.databinding.FragmentHomeBinding
import com.example.coinapp.ui.home.adapter.FavoriteClickListener
import com.example.coinapp.ui.home.adapter.TickerListAdapter
import com.example.coinapp.ui.home.adapter.TickerListPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val _homeViewModel: HomeViewModel by viewModels()

    private var _tickerListAdapter: TickerListAdapter? = null
    private var _favoriteAdapter: TickerListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = _homeViewModel

        setupViewPager()
    }

    private fun setupViewPager() {
        initListAdapters()

        binding.apply {
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            viewPager.adapter = TickerListPagerAdapter(_tickerListAdapter!!, _favoriteAdapter!!)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = (viewPager.adapter as TickerListPagerAdapter).getListTitle(position)
            }.attach()
        }
    }

    private fun initListAdapters() {
        val favoriteClickListener = object : FavoriteClickListener {
            override fun onAddFavorite(symbol: String) {
//                _homeViewModel.addFavoriteSymbol(symbol)
            }

            override fun onDeleteFavorite(symbol: String) {
//                _homeViewModel.deleteFavoriteSymbol(symbol)
            }
        }
        _tickerListAdapter = TickerListAdapter(favoriteClickListener)
        _favoriteAdapter = TickerListAdapter(favoriteClickListener)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}