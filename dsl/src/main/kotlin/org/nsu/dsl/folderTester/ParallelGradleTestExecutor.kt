package org.nsu.dsl.folderTester

import org.nsu.dsl.entity.DirectoryAlias
import org.nsu.dsl.entity.Student
import org.nsu.dsl.entity.Task
import org.nsu.dsl.entity.TaskTestResult
import org.nsu.dsl.util.ls

class ParallelGradleTestExecutor : TestsExecutor {

    override fun executeTests(
        tasks: List<Task>,
        students: List<Student>,
        aliasResolver: (String, List<String>) -> DirectoryAlias?
    ): Map<Student, Map<Task, TaskTestResult>> {

        val taskNames = tasks.map {
            it.name
        }

        val studentFolders: MutableMap<Student, List<DirectoryAlias>> = HashMap()

        students.forEach { student ->
            val lsData = ls(student.studentDir)
            val aliases = lsData.mapNotNull {
                aliasResolver(it, taskNames)
            }
            studentFolders[student] = aliases
        }

        val taskList: MutableList<GradleTestThread> = ArrayList()

        students.forEach { student ->
            studentFolders[student]!!.forEach { alias ->
                taskList.add(
                    GradleTestThread(
                        student,
                        alias,
                        tasks.find {
                            it.name == alias.originalName
                        }!!
                    )
                )
            }
        }

        taskList.forEach(Thread::start)
        taskList.forEach(Thread::join)


        val ans: MutableMap<Student, MutableMap<Task, TaskTestResult>> = HashMap()

        taskList.forEach { testThread ->
            if(!ans.containsKey(testThread.student)){
                ans[testThread.student] = HashMap()
            }
            ans[testThread.student]!![testThread.task] = testThread.taskTestResult
        }

        println(ans)

        return ans
    }
}