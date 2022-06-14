package org.nsu.dsl.gradle;

import org.nsu.dsl.entities.TaskResult;
import org.nsu.dsl.block.SingletonSemaphore;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GradleWorker extends Thread {
    private final File taskDirectory;
    private final String taskName;
    private TaskResult result;

    public GradleWorker(File taskDirectory, String taskName) {
        this.taskDirectory = taskDirectory;
        this.taskName = taskName;
    }

    public boolean checkJavadoc() {
        try {
            ProcessBuilder builder = new ProcessBuilder();
            // TODO implement for diff OS
            builder.command(List.of("cmd.exe", "/c","gradle", "javadoc"));
            builder.directory(taskDirectory);

            SingletonSemaphore.getSemaphore().acquire();
            System.out.println("Starting: " + taskDirectory.getName());
            Process process = builder.start();
            if(!process.waitFor(1, TimeUnit.MINUTES)){
                System.out.println("Timeout");
                process.destroy();
            }
            int exitCode = process.exitValue();
            SingletonSemaphore.getSemaphore().release();
            System.out.println("Finished: " + taskDirectory.getName());
            process.destroy();
            return exitCode == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkTests() {
        try {
            ProcessBuilder builder = new ProcessBuilder();
            // TODO implement for diff OS
            builder.command(List.of("cmd.exe", "/c","gradle", "test"));
            builder.directory(taskDirectory);

            SingletonSemaphore.getSemaphore().acquire();
            System.out.println("Starting: " + taskDirectory.getName());
            Process process = builder.start();
            if(!process.waitFor(1, TimeUnit.MINUTES)){
                System.out.println("Timeout");
                process.destroy();
            }
            int exitCode = process.exitValue();
            SingletonSemaphore.getSemaphore().release();
            System.out.println("Finished: " + taskDirectory.getName());
            process.destroy();
            return exitCode == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPassTime() {
        return false;
    }

    @Override
    public void run() {
        result = new TaskResult(checkTests(), checkJavadoc(), checkPassTime(), taskName);
    }

    public TaskResult getResult() {
        return result;
    }
}
