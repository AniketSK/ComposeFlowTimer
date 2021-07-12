package com.aniketkadam.flowtimer


import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TimerUseCaseTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `test flow with a helper function`() = runBlockingTest {
        pauseDispatcher {
            val t = TimerUseCase(this)
            t.toggleTime(60)
            val testObserver = t.timerStateFlow.test(this)
            advanceTimeBy(10)
            testObserver.assertValues(
                TimerState(null, 60, "-"),
                TimerState(60, 60, "-")
            )

            advanceTimeBy(500)
            testObserver.assertValues(
                TimerState(null, 60, "-"),
                TimerState(60, 60, "-")
            )

            advanceTimeBy(550)
            testObserver.assertValues(
                TimerState(null, 60, "-"),
                TimerState(60, 60, "-"),
                TimerState(59, 60, "-")
            )
            testObserver.finish()
        }
    }

}

fun <T> Flow<T>.test(scope: CoroutineScope): TestObserver<T> {
    return TestObserver(scope, this)
}

class TestObserver<T>(
    scope: CoroutineScope,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()
    private val job: Job = scope.launch {
        flow.collect { values.add(it) }
    }

    fun assertNoValues(): TestObserver<T> {
        assertEquals(emptyList<T>(), this.values)
        return this
    }

    fun assertValues(vararg values: T): TestObserver<T> {
        assertEquals(values.toList(), this.values)
        return this
    }

    fun finish() {
        job.cancel()
    }
}