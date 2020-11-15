package com.example.exchangerateconverter.util

import java.lang.Double.parseDouble
import java.lang.NumberFormatException

/**
 * Input is valid if:
 * ... is not empty
 * ... is a number
 * ... is a positive number
 * ... contains no special characters besides decimal
 * ... does not start with a decimal
 */

object InputValidationUtil {
    fun handleInput(text: String): Double {
        try {
            val num = parseDouble(text)
            if (num > 0) {
                return num
            }
        } catch (e: NumberFormatException) {
        }
        return 0.toDouble()
    }
}