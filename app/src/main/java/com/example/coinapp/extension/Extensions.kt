package com.example.coinapp.extension

import android.app.Activity
import android.content.res.TypedArray
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun Activity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}

fun Activity.showSnackBar(
    text: String,
    buttonName: String? = null,
    clickListener: View.OnClickListener? = null
): Snackbar {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE)
    clickListener?.let {
        snackBar.setAction(buttonName, clickListener)
    }
    snackBar.show()
    return snackBar
}

fun Fragment.showSnackBar(
    text: String, buttonName: String? = null,
    clickListener: View.OnClickListener? = null
): Snackbar? {
    var snackBar: Snackbar? = null
    view?.let {
        snackBar = Snackbar.make(it, text, Snackbar.LENGTH_INDEFINITE).apply {
            clickListener?.let {
                setAction(buttonName, clickListener)
            }
            show()
        }
    }
    return snackBar
}

inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int) =
    getInt(
        index,
        -1
    ).let { if (it >= 0) enumValues<T>()[it] else throw IllegalAccessException("No enum found matching index : $index") }