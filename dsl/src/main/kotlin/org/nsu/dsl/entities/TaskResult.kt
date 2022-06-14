package org.nsu.dsl.entities

data class TaskResult(
    val isTestPassed: Boolean,
    val isJavadocPassed: Boolean,
    val isInTime: Boolean,
    val taskName:String
)
