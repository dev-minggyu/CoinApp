package com.example.data.repository.setting

import com.example.data.mapper.setting.FloatingTickerMapper
import com.example.data.repository.setting.local.SettingLocalDataSourceImpl
import com.example.domain.model.setting.FloatingTicker
import com.example.domain.model.ticker.AtomicTickerList
import com.example.domain.repository.setting.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingLocalDataSourceImpl: SettingLocalDataSourceImpl,
    private val atomicTickerList: AtomicTickerList
) : SettingRepository {
    override fun getSettingTickerChangeColor(): Boolean = settingLocalDataSourceImpl.getSettingTickerChangeColor()

    override fun setSettingTickerChangeColor(value: Boolean) {
        settingLocalDataSourceImpl.setSettingTickerChangeColor(value)
    }

    override fun getSettingFloatingWindow(): Boolean = settingLocalDataSourceImpl.getSettingFloatingWindow()

    override fun setSettingFloatingTickerList(list: List<String>) = settingLocalDataSourceImpl.setSettingFloatingTickerList(list)

    override fun getSettingFloatingTickerList(): List<String> = settingLocalDataSourceImpl.getSettingFloatingTickerList()

    override suspend fun getFloatingTickerList(): List<FloatingTicker> =
        withContext(Dispatchers.Default) {
            val symbolList = atomicTickerList.getSymbolList()
            val settingFloatingTickerList = settingLocalDataSourceImpl.getSettingFloatingTickerList()
            if (settingFloatingTickerList.isEmpty()) {
                return@withContext symbolList.map {
                    FloatingTickerMapper.fromTickerSymbol(it)
                }
            } else {
                val floatingTickerList = symbolList.map {
                    FloatingTickerMapper.fromTickerSymbol(it)
                }
                settingFloatingTickerList.forEach { checkedTicker ->
                    floatingTickerList.find { floatingTicker ->
                        floatingTicker.symbol == checkedTicker
                    }?.apply {
                        isChecked = true
                    }
                }
                return@withContext floatingTickerList
            }
        }
}