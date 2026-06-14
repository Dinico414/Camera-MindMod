/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.mindone.aperture.ext

import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.params.RggbChannelVector
import android.os.Build
import androidx.camera.camera2.interop.CaptureRequestOptions
import androidx.core.util.toRange
import org.lineageos.mindone.aperture.models.ColorCorrectionAberrationMode
import org.lineageos.mindone.aperture.models.DistortionCorrectionMode
import org.lineageos.mindone.aperture.models.EdgeMode
import org.lineageos.mindone.aperture.models.FrameRate
import org.lineageos.mindone.aperture.models.HotPixelMode
import org.lineageos.mindone.aperture.models.NoiseReductionMode
import org.lineageos.mindone.aperture.models.ShadingMode
import org.lineageos.mindone.aperture.models.VideoStabilizationMode
import kotlin.math.ln
import kotlin.math.pow

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun <ValueT> CaptureRequestOptions.Builder.setOrClearCaptureRequestOption(
    key: CaptureRequest.Key<ValueT>,
    value: ValueT?
): CaptureRequestOptions.Builder = value?.let {
    setCaptureRequestOption(key, it)
} ?: clearCaptureRequestOption(key)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setFrameRate(
    frameRate: FrameRate?
) = setOrClearCaptureRequestOption(
    CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE,
    frameRate?.range?.toRange(),
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setVideoStabilizationMode(
    videoStabilizationMode: VideoStabilizationMode?
) = setOrClearCaptureRequestOption(
    CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE,
    when (videoStabilizationMode) {
        VideoStabilizationMode.OFF -> CameraMetadata.CONTROL_VIDEO_STABILIZATION_MODE_OFF
        VideoStabilizationMode.ON -> CameraMetadata.CONTROL_VIDEO_STABILIZATION_MODE_ON
        VideoStabilizationMode.ON_PREVIEW ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                CameraMetadata.CONTROL_VIDEO_STABILIZATION_MODE_PREVIEW_STABILIZATION
            } else {
                CameraMetadata.CONTROL_VIDEO_STABILIZATION_MODE_ON
            }

        null -> null
    }
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setEdgeMode(
    edgeMode: EdgeMode?
) = setOrClearCaptureRequestOption(
    CaptureRequest.EDGE_MODE,
    when (edgeMode) {
        EdgeMode.OFF -> CameraMetadata.EDGE_MODE_OFF
        EdgeMode.FAST -> CameraMetadata.EDGE_MODE_FAST
        EdgeMode.HIGH_QUALITY -> CameraMetadata.EDGE_MODE_HIGH_QUALITY
        EdgeMode.ZERO_SHUTTER_LAG -> CameraMetadata.EDGE_MODE_ZERO_SHUTTER_LAG
        null -> null
    }
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setNoiseReductionMode(
    noiseReductionMode: NoiseReductionMode?
) = setOrClearCaptureRequestOption(
    CaptureRequest.NOISE_REDUCTION_MODE,
    when (noiseReductionMode) {
        NoiseReductionMode.OFF -> CameraMetadata.NOISE_REDUCTION_MODE_OFF
        NoiseReductionMode.FAST -> CameraMetadata.NOISE_REDUCTION_MODE_FAST
        NoiseReductionMode.HIGH_QUALITY -> CameraMetadata.NOISE_REDUCTION_MODE_HIGH_QUALITY
        NoiseReductionMode.MINIMAL -> CameraMetadata.NOISE_REDUCTION_MODE_MINIMAL
        NoiseReductionMode.ZERO_SHUTTER_LAG -> CameraMetadata.NOISE_REDUCTION_MODE_ZERO_SHUTTER_LAG
        null -> null
    }
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setShadingMode(
    shadingMode: ShadingMode?
) = setOrClearCaptureRequestOption(
    CaptureRequest.SHADING_MODE,
    when (shadingMode) {
        ShadingMode.OFF -> CameraMetadata.SHADING_MODE_OFF
        ShadingMode.FAST -> CameraMetadata.SHADING_MODE_FAST
        ShadingMode.HIGH_QUALITY -> CameraMetadata.SHADING_MODE_HIGH_QUALITY
        null -> null
    }
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setColorCorrectionAberrationMode(
    colorCorrectionAberrationMode: ColorCorrectionAberrationMode?
) = setOrClearCaptureRequestOption(
    CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE,
    when (colorCorrectionAberrationMode) {
        ColorCorrectionAberrationMode.OFF -> CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_OFF
        ColorCorrectionAberrationMode.FAST -> CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_FAST
        ColorCorrectionAberrationMode.HIGH_QUALITY ->
            CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY

        null -> null
    }
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setDistortionCorrectionMode(
    distortionCorrectionMode: DistortionCorrectionMode?
) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    setOrClearCaptureRequestOption(
        CaptureRequest.DISTORTION_CORRECTION_MODE,
        when (distortionCorrectionMode) {
            DistortionCorrectionMode.OFF -> CameraMetadata.DISTORTION_CORRECTION_MODE_OFF
            DistortionCorrectionMode.FAST -> CameraMetadata.DISTORTION_CORRECTION_MODE_FAST
            DistortionCorrectionMode.HIGH_QUALITY ->
                CameraMetadata.DISTORTION_CORRECTION_MODE_HIGH_QUALITY

            null -> null
        }
    )
} else {
    this
}

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setHotPixelMode(
    hotPixelMode: HotPixelMode?
) = setOrClearCaptureRequestOption(
    CaptureRequest.HOT_PIXEL_MODE,
    when (hotPixelMode) {
        HotPixelMode.OFF -> CameraMetadata.HOT_PIXEL_MODE_OFF
        HotPixelMode.FAST -> CameraMetadata.HOT_PIXEL_MODE_FAST
        HotPixelMode.HIGH_QUALITY -> CameraMetadata.HOT_PIXEL_MODE_HIGH_QUALITY
        null -> null
    }
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setFocusMode(
    autoFocus: Boolean
) = setCaptureRequestOption(
    CaptureRequest.CONTROL_AF_MODE,
    if (autoFocus) {
        CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE
    } else {
        CameraMetadata.CONTROL_AF_MODE_OFF
    }
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setFocusDistance(
    focusDistance: Float?
) = setOrClearCaptureRequestOption(
    CaptureRequest.LENS_FOCUS_DISTANCE,
    focusDistance
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setWhiteBalanceMode(
    autoWhiteBalance: Boolean
) = setCaptureRequestOption(
    CaptureRequest.CONTROL_AWB_MODE,
    if (autoWhiteBalance) {
        CameraMetadata.CONTROL_AWB_MODE_AUTO
    } else {
        CameraMetadata.CONTROL_AWB_MODE_OFF
    }
)

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setWhiteBalanceTemperature(
    temperature: Int?
) = temperature?.let {
    // This is a very rough approximation of Kelvin to ColorCorrectionGains
    // For a real implementation, a more precise lookup table or formula should be used
    val temp = it / 100.0
    val red = if (temp <= 66) 255.0 else 329.698727446 * (temp - 60).pow(-0.1332047592)
    val green = if (temp <= 66) {
        99.4708025861 * ln(temp) - 161.1195681661
    } else {
        288.1221695283 * (temp - 60).pow(-0.0755148492)
    }
    val blue = if (temp >= 66) 255.0 else {
        if (temp <= 19) 0.0 else 138.5177312231 * ln(temp - 10) - 305.0447927307
    }

    val gains = RggbChannelVector(
        (red / 255.0 * 2.0).toFloat(),
        (green / 255.0 * 1.0).toFloat(),
        (green / 255.0 * 1.0).toFloat(),
        (blue / 255.0 * 2.0).toFloat()
    )
    setCaptureRequestOption(CaptureRequest.COLOR_CORRECTION_MODE, CameraMetadata.COLOR_CORRECTION_MODE_TRANSFORM_MATRIX)
    setCaptureRequestOption(CaptureRequest.COLOR_CORRECTION_GAINS, gains)
} ?: run {
    setCaptureRequestOption(CaptureRequest.COLOR_CORRECTION_MODE, CameraMetadata.COLOR_CORRECTION_MODE_FAST)
    clearCaptureRequestOption(CaptureRequest.COLOR_CORRECTION_GAINS)
}

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setExposureMode(
    autoExposure: Boolean
) = this.apply {
    setCaptureRequestOption(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON)
    setCaptureRequestOption(CaptureRequest.CONTROL_AE_LOCK, !autoExposure)
}

@androidx.camera.camera2.interop.ExperimentalCamera2Interop
fun CaptureRequestOptions.Builder.setExposureCompensation(
    exposureCompensation: Int?
) = setOrClearCaptureRequestOption(
    CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION,
    exposureCompensation
)
