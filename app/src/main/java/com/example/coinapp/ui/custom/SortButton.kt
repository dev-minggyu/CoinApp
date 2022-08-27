package com.example.coinapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.coinapp.R
import com.example.coinapp.databinding.ButtonSortBinding
import com.example.coinapp.enums.SortCategory
import com.example.coinapp.enums.SortType
import com.example.coinapp.extension.getEnum

class SortButton(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private lateinit var _binding: ButtonSortBinding

    private lateinit var _sortCategory: SortCategory

    private var _onSortChangedListener: OnSortChangedListener? = null

    private var _sortType = SortType.NO

    init {
        init(context)
        getAttrs(attrs)
        setListener()
    }

    private fun init(context: Context?) {
        _binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.button_sort,
            this,
            false
        )
        addView(_binding.root)
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
            _binding.ivSortArrow.apply {
                _sortType = when (_sortType) {
                    SortType.NO -> SortType.DESC
                    SortType.DESC -> SortType.ASC
                    SortType.ASC -> SortType.NO
                }
                _onSortChangedListener?.onChanged(_sortCategory, _sortType)
            }
        }
    }

    fun setArrowDrawable(res: Int) {
        _binding.ivSortArrow.setImageResource(res)
    }

    fun setOnSortChangedListener(listener: OnSortChangedListener) {
        _onSortChangedListener = listener
    }

    interface OnSortChangedListener {
        fun onChanged(sortCategory: SortCategory, sortType: SortType)
    }
}