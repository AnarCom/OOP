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

    private fun executeGradleTask(
        args: List<String>,
        workName: String,
        dir: File,
        timeoutMin:Long = 1
    ): TestResultStatus {
        SingletonSemaphore.getSemaphore().acquire()
        println("Starting $workName for student: ${student.name}, task:${task.name}")

        var resVal = TestResultStatus.FAILED
        try {
            val builder = ProcessBuilder()
            // TODO implement for diff OS
            builder.command(args)
            builder.directory(dir)
            val process = builder.start()
            if (!process.waitFor(timeoutMin, TimeUnit.MINUTES)) {
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
        println("Finished $workName for student: ${student.name}, task:${task.name}")
        SingletonSemaphore.getSemaphore().release()
        return resVal
    }

    private fun gradleExecuteTests(dir: File): TestResultStatus {
        return executeGradleTask(
            listOf("cmd.exe", "/c", "gradle", "test"),
            "test",
            dir
        )
    }

    private fun gradleExecuteJavadoc(dir: File): TestResultStatus {
        return executeGradleTask(
            listOf("cmd.exe", "/c", "gradle", "javadoc"),
            "javadoc",
            dir
        )
    }

    private fun gradleExecuteCodestyle(dir: File): TestResultStatus {
        val res = executeGradleTask(
            listOf("cmd.exe", "/c", "gradle", "checkstyleMain"),
            "checkstyle",
            dir
        )
        return if(res == TestResultStatus.TIMEOUT){
            TestResultStatus.FAILED
        } else {
            res
        }
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

        taskTestResult.codestyle = gradleExecuteCodestyle(
            file
        )
    }
}