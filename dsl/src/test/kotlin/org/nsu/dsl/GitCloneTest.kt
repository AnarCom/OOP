package org.nsu.dsl

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nsu.dsl.cloner.vcsCloner.GitClone
import org.nsu.dsl.util.createFolder
import org.nsu.dsl.util.deleteFolder
import java.io.File

class GitCloneTest {

    @BeforeEach
    fun createFolder(){
        deleteFolder(File("./for_test/"))
        createFolder(File("./for_test/"))
    }

    @AfterEach
    fun deleteFolder(){
        deleteFolder(File("./for_test/"))
    }

    @Test
    fun errorInRepUrlTest(){
        Assertions.assertFalse(GitClone().clone("s", "main", "./for_test/"))
    }

    @Test
    fun normalCloneTest(){
        Assertions.assertTrue(GitClone().clone("https://github.com/AnarCom/OOP", "main", "./for_test/"))
    }
}