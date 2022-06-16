package org.nsu.dsl.report

import org.nsu.dsl.entity.Student
import org.nsu.dsl.entity.Task
import org.nsu.dsl.entity.TaskTestResult
import org.nsu.dsl.entity.TestResultStatus
import org.nsu.dsl.util.createFolder
import java.io.File
import java.io.FileOutputStream

class HtmlReportPrinter : ReportPrinter {

    private val builder: StringBuilder = java.lang.StringBuilder()

    private fun tableBeginWriter() {
        builder.append("<table border='1'>")
    }

    private fun tableEndWriter() {
        builder.append("</table>")
    }

    private fun tdWriter(colspan: Int, content: String) {
        builder.append("<td colspan='$colspan'>")
            .append(content)
            .append("</td>")
    }

    private fun tdWriter(content: String) {
        builder.append("<td>")
            .append(content)
            .append("</td>")
    }

    private fun trWriter() {
        builder.append("<tr>")
    }

    private fun trEndWriter() {
        builder.append("</tr>")
    }

    private fun prepareHtmlContent(
        testsResult: Map<Student, Map<Task, TaskTestResult>>,
        tasks: List<Task>,
    ): String {

        builder.clear()
        builder.append("<body>")
        tableBeginWriter()

        trWriter()
        tdWriter("Name")
        tasks.forEach {
            tdWriter(3, it.name)
        }
        tdWriter("")
        trEndWriter()

        trWriter()
        tdWriter("")
        repeat(tasks.size) {
            tdWriter("Tests")
            tdWriter("Javadoc")
            tdWriter("Sum")
        }
        tdWriter("Total")
        trEndWriter()



        testsResult.keys.forEach { student ->
            trWriter()
            tdWriter(student.name)
            var sum = 0
            tasks.forEach { task ->
                var localSum = 0
                if(!testsResult[student]!!.containsKey(task)){
                    repeat(2){
                        tdWriter(TestResultStatus.FAILED.reportText)
                    }
                } else {
                    val testRes = testsResult[student]!![task]!!
                    tdWriter(testRes.test.reportText)
                    tdWriter(testRes.javadoc.reportText)

                    if(testRes.test != TestResultStatus.FAILED){
                        localSum += testRes.test.scoreChange
                        if(testRes.javadoc != TestResultStatus.FAILED){
                            localSum += testRes.javadoc.scoreChange
                        }
                    }
                }
                sum += localSum
                tdWriter(localSum.toString())
            }
            tdWriter(sum.toString())
            trEndWriter()
        }


        tableEndWriter()
        builder.append("</body>")
        return builder.toString()
    }

    override fun printReport(
        outputFolder: String,
        testsResult: Map<Student, Map<Task, TaskTestResult>>,
        tasks: List<Task>
    ) {
        createFolder(outputFolder)

        val outputWriter = FileOutputStream(File("$outputFolder/output.html"), false)
        outputWriter.write(prepareHtmlContent(testsResult, tasks).toByteArray())
        outputWriter.close()
    }
}