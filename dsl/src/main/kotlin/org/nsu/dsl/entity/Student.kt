package org.nsu.dsl.entity

data class Student(
    val name: String,
    val url: String,
    val branch: String,
    var studentDir: String="",
)
