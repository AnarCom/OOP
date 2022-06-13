package org.nsu.dsl.block;

import java.util.concurrent.Semaphore;

public class SingletonSemaphore {

    private static Semaphore semaphore;

    public static void setLimit(int limit){
        semaphore = new Semaphore(limit);
    }

    public static Semaphore getSemaphore() {
        return semaphore;
    }
}
