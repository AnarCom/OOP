package org.nsu.dsl.util

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream


private fun execForFsAndCatch(file: File, func: (File) -> Unit) {
    try {
        func(file)
    } catch (e: IOException) {
        e.printStackTrace()
        throw RuntimeException(e.message)
    }
}

fun createFolder(file: File) {
    execForFsAndCatch(file) {
        FileUtils.forceMkdir(file)
    }
}

fun createFolder(path: String) {
    createFolder(File(path))
}

fun deleteFolder(file: File) {
    execForFsAndCatch(file) {
        if (file.exists()) {
            FileUtils.forceDelete(file)
        }
    }
}

fun deleteFolder(path: String) {
    deleteFolder(File(path))
}

fun deleteAndCreateFolder(path: String) {
    val file = File(path)
    deleteFolder(file)
    createFolder(file)
}

fun ls(path: String): List<String> {
    val directory = File(path)
    return directory.list()!!
        .filter {
            File("$path/$it").isDirectory
        }
}
