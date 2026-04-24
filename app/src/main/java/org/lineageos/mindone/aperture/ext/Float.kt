/*
 * SPDX-FileCopyrightText: 2023 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.mindone.aperture.ext

import kotlin.math.pow

fun Float.previousPowerOfTwo(): Float {
    if (this <= 0) return 0f

    // Use 1.2x steps (geometric/logarithmic) for more granular and consistent zoom feel
    val step = 1.2f
    val currentStep = kotlin.math.floor(kotlin.math.log(this, step)).toInt()
    val result = step.pow(currentStep)

    return if (result >= this - 0.01f) {
        step.pow(currentStep - 1)
    } else {
        result
    }
}

fun Float.nextPowerOfTwo(): Float {
    if (this <= 0) return 0f

    // Use 1.2x steps (geometric/logarithmic) for more granular and consistent zoom feel
    val step = 1.2f
    val currentStep = kotlin.math.ceil(kotlin.math.log(this, step)).toInt()
    val result = step.pow(currentStep)

    return if (result <= this + 0.01f) {
        step.pow(currentStep + 1)
    } else {
        result
    }
}
