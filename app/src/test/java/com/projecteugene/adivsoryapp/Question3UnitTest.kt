package com.projecteugene.adivsoryapp

import com.projecteugene.advisoryapps.utils.Question3
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class Question3UnitTest {
    @Test
    fun question3() {
        assertEquals(Question3.function(0), "23571")
        assertEquals(Question3.function(1), "35711")
        assertEquals(Question3.function(2), "57111")
        assertEquals(Question3.function(3), "71113")
        assertEquals(Question3.function(100), "19319")
        assertEquals(Question3.function(139), "26927")
    }
}
