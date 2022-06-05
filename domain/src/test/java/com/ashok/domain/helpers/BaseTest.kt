package com.ashok.domain.helpers

import org.junit.After
import org.junit.Before

abstract class BaseTest {

    @Before
    open fun setup() {
    }

    @After
    open fun clear() {
    }
}
