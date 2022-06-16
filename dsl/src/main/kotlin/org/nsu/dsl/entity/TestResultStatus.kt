package org.nsu.dsl.entity

enum class TestResultStatus(
    val scoreChange:Int = 0,
    val reportText: String = ""
) {
    TIMEOUT(0, "T"),
    OK(1, "+"),
    FAILED(0, "-"),
}