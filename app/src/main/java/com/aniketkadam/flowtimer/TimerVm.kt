package com.aniketkadam.flowtimer


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow

/**
 * A timer should be startable, stoppable.
 * When started, it counts down from 60 seconds to zero. Showing a '-' at the end.
 *
 */
class TimerVm : ViewModel() {

    private val timerIntent = TimerUseCase(viewModelScope)
    val timerStateFlow: StateFlow<TimerState> = timerIntent.timerStateFlow

    fun toggleStart(totalSeconds: Int) = timerIntent.toggleTime(totalSeconds)

    fun toggleStart(totalSecondsString : String) {
        val validSeconds = totalSecondsString.toIntOrNull()

        if(validSeconds != null) {
            toggleStart(validSeconds)
        }
    }
}

data class TimerState(
    val secondsRemaining: Int? = null,
    val totalSeconds: Int = 60,
    val textWhenStopped: String = "-"
) {
    val displaySeconds: String = (secondsRemaining ?: textWhenStopped).toString()

    // Show 100% if seconds remaining is null
    val progressPercentage: Float = (secondsRemaining ?: totalSeconds) / totalSeconds.toFloat()

    override fun toString(): String = "Seconds Remaining $secondsRemaining, totalSeconds: $totalSeconds, progress: $progressPercentage"

}