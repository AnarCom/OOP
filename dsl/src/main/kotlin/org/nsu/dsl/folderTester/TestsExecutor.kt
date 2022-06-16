package org.nsu.dsl.folderTester

import org.nsu.dsl.entity.DirectoryAlias
import org.nsu.dsl.entity.Student
import org.nsu.dsl.entity.Task
import org.nsu.dsl.entity.TaskTestResult

interface TestsExecutor {
    fun executeTests(
        tasks: List<Task>,
        students:List<Student>,
        aliasResolver: (String, List<String>) -> DirectoryAlias?,
    ): Map<Student, Map<Task, TaskTestResult>>
}