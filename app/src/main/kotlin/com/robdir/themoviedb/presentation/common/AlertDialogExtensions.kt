package com.robdir.themoviedb.presentation.common

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.robdir.themoviedb.R

fun AppCompatActivity.showConfirmCancelAlert(
    title: String? = null,
    message: String,
    confirmButtonLabel: String = getString(R.string.alert_action_ok),
    onConfirmButtonClicked: (DialogInterface, Int) -> Unit,
    cancelButtonLabel: String = getString(R.string.alert_action_cancel),
    onCancelButtonClicked: (DialogInterface, Int) -> Unit = { dialog, _ -> dialog.dismiss() },
    onCancel: ((DialogInterface) -> Unit)? = null,
    isCancelable: Boolean = true
) {
    try {
        AlertDialog.Builder(this)
            .apply { title?.let { setTitle(it) } }
            .setMessage(message)
            .setPositiveButton(confirmButtonLabel, onConfirmButtonClicked)
            .setNegativeButton(cancelButtonLabel, onCancelButtonClicked)
            .setCancelable(isCancelable)
            .apply { if (onCancel != null) setOnCancelListener(onCancel) }
            .create()
            .show()
    } catch (exception: Exception) {
        // Nothing to do here
    }
}
