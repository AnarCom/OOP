package org.nsu.dsl

import org.nsu.dsl.cloner.DefaultStudentRepCloner
import org.nsu.dsl.cloner.StudentRepCloner
import org.nsu.dsl.cloner.vcsCloner.GitClone
import org.nsu.dsl.entity.DirectoryAlias
import org.nsu.dsl.entity.Student
import org.nsu.dsl.entity.Task
import org.nsu.dsl.folderTester.ParallelGradleTestExecutor
import org.nsu.dsl.folderTester.TestsExecutor
import org.nsu.dsl.report.HtmlReportPrinter
import org.nsu.dsl.report.ReportPrinter
import org.nsu.dsl.runner.ConfigurationRunner
import org.nsu.dsl.semaphore.SingletonSemaphore


class MarkCalculator {

    fun exec(
        studentCloner: StudentRepCloner = DefaultStudentRepCloner(GitClone()),
        testExecutor: TestsExecutor = ParallelGradleTestExecutor(),
        reportPrinter: ReportPrinter = HtmlReportPrinter(),
    ) {
        SingletonSemaphore.setLimit(semaphoreQuota)
        val testData = ConfigurationRunner(
            students,
            tasks,
            downloadPath,
            outputPath,
            aliasProducer
        ).execCheck()

        reportPrinter.printReport(outputPath, testData, tasks)
    }

    fun registerStudent(name: String, url: String, branch: String = "master") {
        students.add(Student(name, url, branch))
    }

    fun registerTask(name: String, date: String = "") {
        tasks.add(Task(name, date))
    }


    val students: MutableList<Student> = ArrayList()
    var downloadPath = "./work_area/"
    var outputPath = "./output/"
    val tasks: MutableList<Task> = ArrayList()
    var semaphoreQuota = 5

    var aliasProducer: (String, List<String>) -> DirectoryAlias? = { folder, tasks ->
        if (tasks.contains(folder)) {
            DirectoryAlias(folder, folder)
        } else {
            null
        }
    }

    companion object {
        fun mark(initializer: MarkCalculator.() -> Unit): MarkCalculator =
            MarkCalculator().apply(initializer)
    }

}