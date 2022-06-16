package org.nsu.dsl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.nsu.dsl.cloner.DefaultStudentRepCloner
import org.nsu.dsl.cloner.vcsCloner.VcsClone
import org.nsu.dsl.entity.Student

class DefaultStudentRepClonerTest {

    @Test
//    @Ignore
    fun mainTest(){
        val gitCloner = mockk<VcsClone>()

        every { gitCloner.clone("a", "a", "./output/") } returns false
        every { gitCloner.clone("b", "b", "./output/") } returns true

        val cloner = DefaultStudentRepCloner(gitCloner)
        val students = listOf(
            Student("tut", "a", "a", "./output/"),
            Student("by", "b", "b", "./output/")
        )

        val ans = cloner.cloneAll(students)
        println(ans)
        Assertions.assertEquals(
            ans,
            mapOf(
                Pair(students[0], false),
                Pair(students[1], true)
            )
        )
    }
}