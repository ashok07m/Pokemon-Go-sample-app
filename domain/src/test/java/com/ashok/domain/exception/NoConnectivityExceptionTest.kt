package com.ashok.domain.exception

import com.ashok.domain.helpers.BaseTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NoConnectivityExceptionTest : BaseTest() {

    private lateinit var noConnectivityException: NoConnectivityException

    override fun setup() {
        super.setup()
        noConnectivityException = NoConnectivityException()
    }

    @Test
    fun `test getMessageInteraction`() {
        assertNotNull(noConnectivityException)
        val actual = noConnectivityException.localizedMessage
        assertTrue(actual.isNullOrEmpty())
    }
}