package com.aniketkadam.flowtimer



import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun SideEffectVibrateOn(vibrationEffect: VibrationEffect, vibrateIf: () -> Boolean) {
    val c = LocalContext.current
    SideEffect {
        if (vibrateIf()) {
            ContextCompat.getSystemService(c, Vibrator::class.java)?.vibrate(vibrationEffect)
        }
    }
}
