package i.design.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SnowFlakeTest {
    private val snowFlake = SnowFlake()
    @Test
    fun nextId() {
        val idA = snowFlake.nextId()
        val idB = snowFlake.nextId()
        assertNotEquals(idA,idB)
    }
}