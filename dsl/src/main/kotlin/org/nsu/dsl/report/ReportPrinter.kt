package org.nsu.dsl.report

import org.nsu.dsl.entity.Student
import org.nsu.dsl.entity.Task
import org.nsu.dsl.entity.TaskTestResult

interface ReportPrinter {

    fun printReport(
        outputFolder: String,
        testsResult: Map<Student, Map<Task, TaskTestResult>>,
        tasks: List<Task>
    )

}