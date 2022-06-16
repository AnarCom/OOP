package org.nsu.dsl.semaphore

import java.util.concurrent.Semaphore

class SingletonSemaphore {
    companion object {

        private var semaphore: Semaphore? = null

        fun setLimit(limit: Int = 5) {
            semaphore = Semaphore(limit)
        }

        fun getSemaphore(): Semaphore = semaphore!!
    }
}
