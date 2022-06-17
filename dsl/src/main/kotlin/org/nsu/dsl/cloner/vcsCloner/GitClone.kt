package org.nsu.dsl.cloner.vcsCloner

import java.io.File

class GitClone : VcsClone {
    override fun clone(repUrl: String, branch: String, cloneFolder: String): Boolean {
        return try {
            val builder = ProcessBuilder()
            builder.command(listOf("git", "clone", "--branch", branch, repUrl, "."))
            builder.directory(File(cloneFolder))
            val process = builder.start()
            val exitCode = process.waitFor()
            process.destroy()
            exitCode == 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}