package org.nsu.dsl.entity

data class TaskTestResult(
    var javadoc:TestResultStatus = TestResultStatus.TIMEOUT,
    var test:TestResultStatus = TestResultStatus.TIMEOUT,
)
