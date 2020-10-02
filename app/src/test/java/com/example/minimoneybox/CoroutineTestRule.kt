package com.example.minimoneybox

import org.junit.rules.TestWatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule(private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        dispatcher.runBlockingTest { block() }
    }

}