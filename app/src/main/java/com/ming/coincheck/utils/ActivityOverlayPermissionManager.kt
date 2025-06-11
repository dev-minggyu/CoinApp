package com.ming.coincheck.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.ming.coincheck.R
import java.lang.ref.WeakReference

class ActivityOverlayPermissionManager private constructor(private val fragment: WeakReference<Fragment>) {
    private var callback: (Boolean) -> Unit = {}

    private val permissionCheck =
        fragment.get()?.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            fragment.get()?.let {
                when (Settings.canDrawOverlays(it.requireContext())) {
                    true -> sendResultAndCleanUp(true)
                    false -> sendResultAndCleanUp(false)
                }
            }
        }

    fun checkPermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        fragment.get()?.let {
            when (Settings.canDrawOverlays(it.requireContext())) {
                true -> sendResultAndCleanUp(true)
                false -> displayRationale(it.requireContext())
            }
        }
    }

    private fun displayRationale(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.permission_overlay_dialog_title))
            .setMessage(context.getString(R.string.permission_overlay_dialog_message))
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.permission_overlay_dialog_button_positive)) { _, _ ->
                requestPermissions(context)
            }
            .show()
    }

    private fun sendResultAndCleanUp(grantResult: Boolean) {
        callback(grantResult)
        callback = {}
    }

    private fun requestPermissions(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${context.packageName}".toUri()
        )
        permissionCheck?.launch(intent)
    }

    companion object {
        fun from(fragment: Fragment) =
            ActivityOverlayPermissionManager(WeakReference(fragment))
    }
}