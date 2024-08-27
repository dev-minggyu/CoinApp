package com.mingg.coinapp.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.mingg.coinapp.R
import java.lang.ref.WeakReference

@RequiresApi(Build.VERSION_CODES.M)
class ActivityOverlayPermissionManager private constructor(private val activity: WeakReference<AppCompatActivity>) {
    private var callback: (Boolean) -> Unit = {}

    private val permissionCheck =
        activity.get()
            ?.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                activity.get()?.let {
                    when (Settings.canDrawOverlays(it)) {
                        true -> sendResultAndCleanUp(true)
                        false -> sendResultAndCleanUp(false)
                    }
                }
            }

    fun checkPermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        activity.get()?.let {
            when (Settings.canDrawOverlays(it)) {
                true -> sendResultAndCleanUp(true)
                false -> displayRationale(it)
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
        fun from(activity: AppCompatActivity) =
            ActivityOverlayPermissionManager(WeakReference(activity))
    }
}