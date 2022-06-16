package org.nsu.dsl.folderTester

import org.nsu.dsl.entity.*
import org.nsu.dsl.semaphore.SingletonSemaphore
import java.io.File
import java.util.concurrent.TimeUnit


class GradleTestThread(
    val student: Student,
    private val directoryAlias: DirectoryAlias,
    val task: Task,
) : Thread() {

    val taskTestResult = TaskTestResult()

    private fun gradleExecuteTests(dir:File): TestResultStatus {
        SingletonSemaphore.getSemaphore().acquire()
        println("Starting test for student: ${student.name}, task:${task.name}")

        var resVal = TestResultStatus.FAILED
        try {
            val builder = ProcessBuilder()
            // TODO implement for diff OS
            builder.command(listOf("cmd.exe", "/c", "gradle", "test"))
            builder.directory(dir)
            val process = builder.start()
            if (!process.waitFor(1, TimeUnit.MINUTES)) {
                process.destroy()
                resVal = TestResultStatus.TIMEOUT
            } else {
                val exitCode = process.exitValue()
                process.destroy()
                if (exitCode == 0) {
                    resVal = TestResultStatus.OK
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Finished test for student: ${student.name}, task:${task.name}")
        SingletonSemaphore.getSemaphore().release()
        return TestResultStatus.OK
    }

    private fun gradleExecuteJavadoc(dir: File): TestResultStatus {
        SingletonSemaphore.getSemaphore().acquire()
        println("Starting javadoc for student: ${student.name}, task:${task.name}")
        var resVal = TestResultStatus.FAILED
        try {
            val builder = ProcessBuilder()
            // TODO implement for diff OS
            builder.command(listOf("cmd.exe", "/c", "gradle", "javadoc"))
            builder.directory(dir)
            val process = builder.start()
            if (!process.waitFor(1, TimeUnit.MINUTES)) {
                process.destroy()
                resVal = TestResultStatus.TIMEOUT
            } else {
                val exitCode = process.exitValue()
                process.destroy()
                if (exitCode == 0) {
                    resVal = TestResultStatus.OK
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        println("Finished javadoc for student: ${student.name}, task:${task.name}")
        SingletonSemaphore.getSemaphore().release()
        return resVal
    }


    override fun run() {
        val file = File(
            "${student.studentDir}/${directoryAlias.realName}"
        )

        taskTestResult.test = gradleExecuteTests(
            file
        )

        taskTestResult.javadoc = gradleExecuteJavadoc(
            file
        )
    }
}