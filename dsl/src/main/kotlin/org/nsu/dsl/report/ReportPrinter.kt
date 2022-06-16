package org.nsu.dsl.report

import org.nsu.dsl.entity.Student
import org.nsu.dsl.entity.Task
import org.nsu.dsl.entity.TaskTestResult

interface ReportPrinter {

    /**
     * Prints report from system.
     * @param outputFolder folder that should contain report.
     * @param testsResult results of tests from tester.
     * @param tasks list of tasks that should be presented in report.
     */
    fun printReport(
        outputFolder: String,
        testsResult: Map<Student, Map<Task, TaskTestResult>>,
        tasks: List<Task>
    )

}