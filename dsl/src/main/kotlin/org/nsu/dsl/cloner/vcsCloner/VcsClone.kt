package org.nsu.dsl.cloner.vcsCloner

interface VcsClone {

    /**
     * Clones repository content to the folder.
     * @param repUrl url to vcs repository.
     * @param branch name of the branch that should be at the end of clone process.
     * @param cloneFolder path to folder where rep. should be cloned.
     * @return true if success, false otherwise.
     */
    fun clone(
        repUrl: String,
        branch: String,
        cloneFolder: String
    ): Boolean
}