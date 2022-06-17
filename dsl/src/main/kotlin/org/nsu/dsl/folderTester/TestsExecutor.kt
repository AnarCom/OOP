package org.nsu.dsl.folderTester

import org.nsu.dsl.entity.DirectoryAlias
import org.nsu.dsl.entity.Student
import org.nsu.dsl.entity.Task
import org.nsu.dsl.entity.TaskTestResult

interface TestsExecutor {
    /**
     * Executes test for Students.
     * @param tasks list of tasks that can be executed.
     * @param students list of students for check.
     * @param aliasResolver resolver for understanding that some dirs has different name
     * @return returns result of tests.
     */
    fun executeTests(
        tasks: List<Task>,
        students:List<Student>,
        aliasResolver: (String, List<String>) -> DirectoryAlias?,
    ): Map<Student, Map<Task, TaskTestResult>>
}