package org.nsu.dsl.cloner

import org.nsu.dsl.cloner.vcsCloner.GitClone
import org.nsu.dsl.cloner.vcsCloner.VcsClone
import org.nsu.dsl.entity.Student
import org.nsu.dsl.util.deleteAndCreateFolder
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

class DefaultStudentRepCloner(
    private val cloner: VcsClone = GitClone(),
) : StudentRepCloner {

    override fun cloneAll(student: List<Student>): Map<Student, Boolean> {

        val cloneResults: MutableMap<Student, Boolean> = ConcurrentHashMap();

        val cloners = student.map {
            thread {
                println("Starting of cloning rep: ${it.url}")
                val cloneRes = cloner.clone(
                    it.url,
                    it.branch,
                    it.studentDir
                )
                println("Finished cloning rep: ${it.url} (exit code == 0 ? $cloneRes)")
                cloneResults[it] = cloneRes
            }
        }

        cloners.forEach {
            it.join()
        }
        return cloneResults
    }


}