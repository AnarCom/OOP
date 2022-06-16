package org.nsu.dsl.cloner

import org.nsu.dsl.entity.Student

interface StudentRepCloner {

    /**
     * Clones student's rep from VCS.
     * @param student List of students that should be cloned to local device.
     * @return map with result of cloning (in value should be true if rep is success cloned).
     */
    fun cloneAll(student: List<Student>): Map<Student, Boolean>

}