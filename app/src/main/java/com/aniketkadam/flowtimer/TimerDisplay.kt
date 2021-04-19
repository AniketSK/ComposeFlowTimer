package com.aniketkadam.flowtimer

import android.os.VibrationEffect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

@Composable
fun TimerDisplay(
    timerState: TimerState,
    toggleStartStop: (String) -> Unit,
    vibrateOnStop: Boolean = true,
) {
    val pad = 8.dp
    val circleSize = min(
        LocalConfiguration.current.screenWidthDp,
        LocalConfiguration.current.screenHeightDp
    ).dp - pad * 2

    var newTime by rememberSaveable {
        mutableStateOf("60")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { toggleStartStop(newTime) }
            .fillMaxSize()
            .padding(pad)
    ) {
        CircularProgressIndicator(
            timerState.progressPercentage,
            strokeWidth = 14.dp,
            modifier = Modifier
                .size(circleSize)
        )

        if (timerState.secondsRemaining == null) {
            TextField(value = newTime, onValueChange = { newTime = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { toggleStartStop(newTime) }),
                trailingIcon = {
                    Button({ toggleStartStop(newTime) }) {
                        Text("Start")
                    }
                }
            )
        } else {
            Text(timerState.displaySeconds, fontSize = 150.sp)
        }
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
fun PreviewActiveTimerDisplay() {
    TimerDisplay(timerState = TimerState(60, 60), {})
}

@Preview(showBackground = true)
@Composable
fun PreviewIdleTimerDisplay() {
    TimerDisplay(timerState = TimerState(null, 60), {})
}