package com.example.exchangerateconverter.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InputValidationUtilTest {

    @Test
    fun `empty value returns 0`() {
        val result = InputValidationUtil.handleInput("")
        assertThat(result).isEqualTo(0.toDouble())
    }
    
    @Test
    fun `value contains any characters in input returns 0`() {
        val result = InputValidationUtil.handleInput("10 Million")
        assertThat(result).isEqualTo(0.toDouble())
    }

    @Test
    fun `value contains a negative number returns 0`() {
        val result = InputValidationUtil.handleInput("-100")
        assertThat(result).isEqualTo(0.toDouble())
    }

    @Test
    fun `single decimal input returns 0`() {
        val result = InputValidationUtil.handleInput(".")
        assertThat(result).isEqualTo(0.toDouble())
    }

    @Test
    fun `leading decimal input returns result as double`() {
        val result = InputValidationUtil.handleInput(".5")
        assertThat(result).isEqualTo(0.5)
    }

    @Test
    fun `invalid number with decimal returns 0`() {
        val result = InputValidationUtil.handleInput("5.0.0")
        assertThat(result).isEqualTo(0.toDouble())
    }

    @Test
    fun `valid input returns result as double`() {
        val result = InputValidationUtil.handleInput("100")
        assertThat(result).isEqualTo(100.toDouble())
    }

    @Test
    fun `valid input with decimal returns result as double`() {
        val result = InputValidationUtil.handleInput("123.45")
        assertThat(result).isEqualTo(123.45)
    }

}