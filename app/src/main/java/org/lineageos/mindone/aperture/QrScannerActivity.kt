/*
 * SPDX-FileCopyrightText: 2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.mindone.aperture

import org.lineageos.mindone.aperture.models.CameraMode

class QrScannerActivity : CameraActivity() {
    override fun overrideInitialCameraMode() = CameraMode.QR
}
