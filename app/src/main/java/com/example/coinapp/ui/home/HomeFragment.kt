package com.example.coinapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.coinapp.R
import com.example.coinapp.base.BaseFragment
import com.example.coinapp.databinding.FragmentHomeBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.ui.home.adapter.TickerListAdapter
import com.example.coinapp.ui.home.adapter.TickerListPagerAdapter
import com.example.domain.model.ticker.Currency
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val _homeViewModel: HomeViewModel by viewModels()

    private var _tickerListAdapter: TickerListAdapter? = null
    private var _favoriteListAdapter: TickerListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = _homeViewModel

        setupListAdapters()
        setupViewPager()
        setupObserver()
    }

    private fun setupViewPager() {
        binding.apply {
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            viewPager.adapter = TickerListPagerAdapter(_tickerListAdapter!!, _favoriteListAdapter!!)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = (viewPager.adapter as TickerListPagerAdapter).getListTitle(position)
            }.attach()
        }
    }

    private fun setupListAdapters() {
        val favoriteClickListener = object : TickerListAdapter.FavoriteClickListener {
            override fun onAddFavorite(symbol: String) {
                _homeViewModel.insertFavoriteTicker(symbol)
            }

            override fun onDeleteFavorite(symbol: String) {
                _homeViewModel.deleteFavoriteTicker(symbol)
            }
        }
        _tickerListAdapter = TickerListAdapter(favoriteClickListener)
        _favoriteListAdapter = TickerListAdapter(favoriteClickListener)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _homeViewModel.tickerList.collectWithLifecycle(lifecycle) { tickerList ->
                _tickerListAdapter?.submitList(tickerList?.filter { it.currencyType == Currency.KRW })
                _favoriteListAdapter?.submitList(tickerList?.filter { it.isFavorite })
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}