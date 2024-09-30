package com.mingg.coincheck.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ButtonSortBinding
import com.mingg.coincheck.extension.getEnum
import com.mingg.domain.model.ticker.SortCategory
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.SortType

class SortButton(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private lateinit var _binding: ButtonSortBinding

    private lateinit var _sortCategory: SortCategory

    private lateinit var _sortModel: SortModel

    private var _onSortChangedListener: OnSortChangedListener? = null

    init {
        init(context)
        getAttrs(attrs)
        setListener()
    }

    private fun init(context: Context?) {
        _binding = ButtonSortBinding.inflate(LayoutInflater.from(context), this, true)
        _binding.ivSortArrow.setImageResource(R.drawable.ic_arrow_normal)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SortButton)

        _sortCategory = typedArray.getEnum(R.styleable.SortButton_sortCategory)

        _binding.tvSortName.apply {
            text = when (_sortCategory) {
                SortCategory.NAME -> context.getString(R.string.sort_coin_name)
                SortCategory.PRICE -> context.getString(R.string.sort_coin_price)
                SortCategory.RATE -> context.getString(R.string.sort_coin_rate)
                SortCategory.VOLUME -> context.getString(R.string.sort_coin_volume)
            }
        }

        typedArray.recycle()
    }

    private fun setListener() {
        setOnClickListener {
            _binding.apply {
                if (_sortModel.category == _sortCategory) {
                    when (_sortModel.type) {
                        SortType.ASC -> _sortModel.type = SortType.DESC
                        SortType.DESC -> _sortModel.type = SortType.ASC
                    }
                } else {
                    _sortModel.type = SortType.DESC
                }
                _sortModel.category = _sortCategory
            }
            _onSortChangedListener?.onChanged(_sortModel)
        }
    }

    fun setSortState(sortModel: SortModel) {
        _sortModel = sortModel.copy()
        _binding.apply {
            if (_sortModel.category == _sortCategory) {
                when (sortModel.type) {
                    SortType.DESC -> ivSortArrow.setImageResource(R.drawable.ic_arrow_down)
                    SortType.ASC -> ivSortArrow.setImageResource(R.drawable.ic_arrow_up)
                }
            } else {
                ivSortArrow.setImageResource(R.drawable.ic_arrow_normal)
            }
        }
    }

    fun getSortState(): SortModel = _sortModel.copy()

    fun setOnSortChangedListener(listener: OnSortChangedListener) {
        _onSortChangedListener = listener
    }

    interface OnSortChangedListener {
        fun onChanged(sortModel: SortModel)
    }
}