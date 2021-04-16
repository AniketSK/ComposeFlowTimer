package com.aniketkadam.flowtimer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimerDisplay(timerState: TimerState, toggleStartStop: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            timerState.progressPercentage,
            Modifier.clickable { toggleStartStop() })
        Text(timerState.displaySeconds)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimerDisplay() {
    TimerDisplay(timerState = TimerState(60)) {}
}