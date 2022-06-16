package org.nsu.dsl.cloner

import org.nsu.dsl.entity.Student

interface StudentRepCloner {

    fun cloneAll(student: List<Student>): Map<Student, Boolean>

}