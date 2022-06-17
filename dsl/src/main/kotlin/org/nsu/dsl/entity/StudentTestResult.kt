package org.nsu.dsl.entity

import java.util.concurrent.ConcurrentHashMap

data class StudentTestResult(
    var cloneRes: TestResultStatus = TestResultStatus.FAILED,
    var testRes: MutableMap<Task, TaskTestResult> = ConcurrentHashMap()
)
