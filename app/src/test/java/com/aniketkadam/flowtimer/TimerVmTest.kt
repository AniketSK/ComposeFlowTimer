package com.aniketkadam.flowtimer

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class TimerVmTest {

    lateinit var vm : TimerVm

    @Before
    fun setup() {
        vm = TimerVm()
    }

    @Test
    fun `empty string of seconds won't crash`() {
        vm.toggleStart("")
    }
}