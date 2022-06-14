package org.nsu.dsl

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.nsu.dsl.entities.Student

class MarkCalculatorTest {
    @Test
    fun studentAddTest() {
        val markCalculator = MarkCalculator.mark {
            student("name", "url", "master")
        }
        Assertions.assertEquals(
            markCalculator.students,
            listOf(
                Student("name", "url", "master")
            )
        )
    }

    @Test
    fun addTaskTest() {
        val markCalculator = MarkCalculator.mark {
            registerTask("1", "", listOf("12", "13"))
        }

        val task = markCalculator.tasks[0]
        val ans = listOf("1", "12", "13")
        Assertions.assertEquals(task.date, "");
        Assertions.assertEquals(task.name, "1");

        task.alternativeNames.forEach {
            Assertions.assertTrue(ans.contains(it))
        }
    }

    @Test
    fun addDirTest(){
        val markCalculator = MarkCalculator.mark {
            excludeDirectory(".git")
        }

        Assertions.assertTrue(markCalculator.excludedDirectories.contains(".git"))
        Assertions.assertTrue(markCalculator.excludedDirectories.size == 1)

    }
}