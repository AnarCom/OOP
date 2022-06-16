package org.nsu.dsl.util

import org.nsu.dsl.entity.Student
import java.util.*

fun prepareFolderName(downloadPath: String, student: Student): String =
    "$downloadPath/${student.name.lowercase(Locale.getDefault()).replace(" ", "")}"

