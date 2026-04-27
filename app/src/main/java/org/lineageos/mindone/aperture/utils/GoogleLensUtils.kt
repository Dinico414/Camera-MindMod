/*
 * SPDX-FileCopyrightText: 2023-2025 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.mindone.aperture.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object GoogleLensUtils {
    private const val GSA_PACKAGE_NAME = "com.google.android.googlequicksearchbox"
    const val LAUNCHER_PACKAGE_NAME = "com.google.android.apps.googlecamera.fishfood"
    private const val LAUNCHER_APK_ASSET_NAME = "ApertureLensLauncher.apk"

    private fun isGsaAvailable(context: Context) = runCatching {
        context.packageManager.getApplicationInfo(GSA_PACKAGE_NAME, 0).enabled
    }.getOrDefault(false)

    fun isLensLauncherAvailable(context: Context) = runCatching {
        context.packageManager.getApplicationInfo(LAUNCHER_PACKAGE_NAME, 0).enabled
    }.getOrDefault(false)

    fun isGoogleLensAvailable(context: Context) =
        isGsaAvailable(context) && isLensLauncherAvailable(context)

    fun launchGoogleLens(context: Context) {
        context.startActivity(
            Intent().setClassName(LAUNCHER_PACKAGE_NAME, "$LAUNCHER_PACKAGE_NAME.MainActivity")
        )
    }

    fun installLensLauncher(context: Context) {
        val apkFile = File(context.cacheDir, LAUNCHER_APK_ASSET_NAME)
        context.assets.open(LAUNCHER_APK_ASSET_NAME).use { input ->
            FileOutputStream(apkFile).use { output ->
                input.copyTo(output)
            }
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            apkFile
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
