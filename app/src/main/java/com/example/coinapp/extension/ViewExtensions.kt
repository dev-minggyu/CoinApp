package com.example.coinapp.extension

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat

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

fun EditText.addNumberFormatter() {
    this.addTextChangedListener(object : TextWatcher {
        private var current = ""

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s.toString()
            if (text.isNotEmpty() && text != current) {
                var strNumber: String
                var strDecimal = ""
                if (text.contains(".")) {
                    strNumber = text.substring(0, text.indexOf("."))
                    strDecimal = text.substring(text.indexOf("."), text.length)
                } else {
                    strNumber = text
                }
                strNumber = strNumber.replace(",", "")
                val doubleText = strNumber.toDouble()
                current = NumberFormat.getNumberInstance().format(doubleText) + strDecimal
                setText(current)
                setSelection(current.length)
            }
        }
    })
}

fun EditText.getTextWithoutComma(): String = text.toString().replace(",", "")