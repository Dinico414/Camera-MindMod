/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.mindone.aperture.utils

import android.os.Build

object DeviceCapabilities {
    private val BROKEN_RAW_HARDWARE = setOf(
        "mt8781", // MediaTek Helio G99
        "mt6789", // MediaTek Helio G99 (alternate platform name)
    )

    val isRawCaptureSupported: Boolean
        get() = Build.HARDWARE.lowercase() !in BROKEN_RAW_HARDWARE
}
