import entities.Student
import entities.Task
import org.nsu.dsl.ConfigurationRunner
import org.nsu.dsl.block.SingletonSemaphore

class MarkCalculator {
    fun exec() {
        val runner = ConfigurationRunner(students, downloadPath, outputPath, tasks)
        SingletonSemaphore.setLimit(semaphoreQuota)
        runner.executeCheck()
    }

    fun student(name: String, url: String, branch: String) {
        students.add(Student(name, url, branch))
    }

    fun registerTask(name: String, date: String){
        tasks.add(Task(name, date))
    }

    private val students: MutableList<Student> = ArrayList()
    var downloadPath = "./work_area/"
    var outputPath = "./output/"
    private val tasks: MutableList<Task> = ArrayList()
    private val semaphoreQuota = 10

    companion object {
        fun mark(initializer: MarkCalculator.() -> Unit): MarkCalculator =
            MarkCalculator().apply(initializer)
    }
}