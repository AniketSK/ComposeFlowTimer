package com.aniketkadam.flowtimer

import android.os.VibrationEffect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerDisplay(
    timerState: TimerState,
    toggleStartStop: () -> Unit,
    vibrateOnStop: Boolean = true,
) {
    val pad = 8.dp
    val circleSize = LocalConfiguration.current.screenWidthDp.dp - pad * 2
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { toggleStartStop() }
            .fillMaxSize()
            .padding(pad)
    ) {
        CircularProgressIndicator(
            timerState.progressPercentage,
            strokeWidth = 14.dp,
            modifier = Modifier
                .size(circleSize)
        )
        Text(timerState.displaySeconds, fontSize = 150.sp)
    }

    if (vibrateOnStop) {
        SideEffectVibrateOn(
            VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE),
            vibrateIf = {
                timerState.secondsRemaining == 0
            })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimerDisplay() {
    TimerDisplay(timerState = TimerState(60), {})
}