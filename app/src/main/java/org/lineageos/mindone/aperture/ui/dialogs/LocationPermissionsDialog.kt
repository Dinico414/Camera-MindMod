/*
 * SPDX-FileCopyrightText: 2023-2025 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.mindone.aperture.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.core.view.WindowCompat
import org.lineageos.mindone.aperture.R

class LocationPermissionsDialog(activity: Activity) : Dialog(activity) {
    private val turnOnButton by lazy {
        findViewById<Button>(R.id.locationPermissionsDialogTurnOnButton)
    }
    private val laterButton by lazy {
        findViewById<Button>(R.id.locationPermissionsDialogLaterButton)
    }

    var onResultCallback: (Boolean) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.location_permissions_dialog)
        window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            WindowCompat.setDecorFitsSystemWindows(it, false)
        }

        setOnCancelListener {
            onResultCallback(false)
        }

        turnOnButton.setOnClickListener {
            dismiss()
            onResultCallback(true)
        }
        laterButton.setOnClickListener {
            dismiss()
            onResultCallback(false)
        }
    }
}
