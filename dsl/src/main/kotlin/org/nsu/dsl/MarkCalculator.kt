package org.nsu.dsl

import org.nsu.dsl.block.SingletonSemaphore
import org.nsu.dsl.entities.Student
import org.nsu.dsl.entities.Task

class MarkCalculator {
    fun exec() {
        val runner = ConfigurationRunner(
            students,
            downloadPath,
            outputPath,
            tasks,
            excludedDirectories
        )
        SingletonSemaphore.setLimit(semaphoreQuota)
        runner.executeCheck()
    }

    fun student(name: String, url: String, branch: String) {
        students.add(Student(name, url, branch))
    }

    fun registerTask(name: String, date: String, alternativeNames: List<String> = listOf()) {
        val buf = alternativeNames.toMutableList()
        buf.add(name)
        tasks.add(Task(name, date, buf))
    }

    fun excludeDirectory(name:String){
        excludedDirectories.add(name)
    }

    val excludedDirectories = mutableListOf<String>();
    val students: MutableList<Student> = ArrayList()
    var downloadPath = "./work_area/"
    var outputPath = "./output/"
    val tasks: MutableList<Task> = ArrayList()
    private val semaphoreQuota = 5

    companion object {
        fun mark(initializer: MarkCalculator.() -> Unit): MarkCalculator =
            MarkCalculator().apply(initializer)
    }
}