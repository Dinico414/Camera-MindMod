/*
 * SPDX-FileCopyrightText: 2022-2025 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.mindone.aperture.models

enum class CameraMode(
    val supportedFlashModes: Set<FlashMode>,
) {
    PHOTO(
        setOf(
            FlashMode.OFF,
            FlashMode.AUTO,
            FlashMode.ON,
            FlashMode.SCREEN,
        ),
    ),
    VIDEO(
        setOf(
            FlashMode.OFF,
            FlashMode.TORCH,
        ),
    ),
    QR(
        setOf(
            FlashMode.OFF,
            FlashMode.TORCH,
        ),
    ),
}
