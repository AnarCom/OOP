package org.nsu.dsl.runner

import org.nsu.dsl.cloner.DefaultStudentRepCloner
import org.nsu.dsl.cloner.StudentRepCloner
import org.nsu.dsl.cloner.vcsCloner.GitClone
import org.nsu.dsl.cloner.vcsCloner.VcsClone
import org.nsu.dsl.entity.*
import org.nsu.dsl.folderTester.ParallelGradleTestExecutor
import org.nsu.dsl.folderTester.TestsExecutor
import org.nsu.dsl.util.deleteAndCreateFolder
import org.nsu.dsl.util.prepareFolderName
import kotlin.concurrent.thread

class ConfigurationRunner(
    private val students: List<Student>,
    private val tasks: List<Task>,
    private val downloadPath: String,
    private val outputPath: String,
    private val aliasResolver: (String, List<String>) -> DirectoryAlias?,
) {

    fun execCheck(
        studentCloner: StudentRepCloner = DefaultStudentRepCloner(GitClone()),
        testExecutor: TestsExecutor = ParallelGradleTestExecutor(),
    ): Map<Student, Map<Task, TaskTestResult>> {
        // step -> clear and create tmp folder
        deleteAndCreateFolder(downloadPath)

        // step -> add folder name to student
        students.forEach {
            it.studentDir = prepareFolderName(downloadPath, it)
        }

        // step -> clone action
        val testResults = studentCloner.cloneAll(
            students
        )

        // step -> tests action
        val testRes = testExecutor.executeTests(
            tasks,
            testResults.keys.filter {
                testResults[it]!!
            }, aliasResolver
        ).toMutableMap()

        // step -> merge data action

        testResults.keys.filter {
            !testResults[it]!!
        }.forEach {
            testRes[it] = HashMap()
        }

        // step -> return
        return testRes
    }
}