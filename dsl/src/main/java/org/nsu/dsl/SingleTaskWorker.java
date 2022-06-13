package org.nsu.dsl;

import entities.Student;
import entities.Task;
import entities.TaskResult;
import org.apache.commons.io.FileUtils;
import org.nsu.dsl.gradle.GradleWorker;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SingleTaskWorker extends Thread {

    private final Student student;
    private final String tmpFolder;

    private final List<Task> tasks;

    private Map <String, TaskResult> testResults;

    public SingleTaskWorker(Student student, String tmpFolder, List<Task> tasks) {
        this.student = student;
        this.tmpFolder = tmpFolder;
        this.tasks = tasks;
    }

    public void debugPrintOutput(InputStream stream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line;
        while (true) {
            try {
                if ((line = in.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(line);
        }
        try {
            in.close();
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean cloneGitRep(String userTmpFolder) {
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(List.of("git", "clone", student.getUrl(), "."));

            builder.directory(new File(userTmpFolder));

            Process process = builder.start();
            int exitCode = process.waitFor();
            process.destroy();
            return exitCode == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String createFolderForStudent() throws IOException {
        String newTmpFolder =
                tmpFolder +
                        student.getName().toLowerCase().replaceAll(" ", "");
        FileUtils.forceMkdir(new File(newTmpFolder));
        return newTmpFolder;
    }

    public List<String> getListOfDirectories(String userTmpFolder) {
        File directory = new File(userTmpFolder);
        return Stream.of(Objects.requireNonNull(directory.list()))
                .filter((i) -> new File(userTmpFolder + "/" + i).isDirectory())
                .collect(Collectors.toList());

    }

    @Override
    public void run() {
        //step -> create folder for student
        String userTmpFolder;
        try {
            userTmpFolder = createFolderForStudent();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //step -> clone rep
        if (!this.cloneGitRep(userTmpFolder)) {
            return;
        }
        //step -> get lists of folders
        var studentDirsList = getListOfDirectories(userTmpFolder);

        //step -> merge with existing information about tasks\folders
        var availableTasks = studentDirsList.stream()
                .filter(i ->
                        tasks.stream()
                                .map(Task::getName)
                                .anyMatch(j -> j.equals(i))
                )
                .collect(Collectors.toList());
        System.out.println(availableTasks);
        //step -> run tests
        var gradleThreads = availableTasks
                .stream()
                .map((i) -> new GradleWorker(new File(userTmpFolder + "/" + i + "/"), i))
                .collect(Collectors.toList());

        gradleThreads.forEach(Thread::start);

        Map <String, TaskResult> studentTestRes = new HashMap<>();
        gradleThreads.forEach(i -> {
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            studentTestRes.put(i.getResult().getTaskName(), i.getResult());
        });

        System.out.println(studentTestRes);
        //step -> return results
        testResults = studentTestRes;
    }

    public Map<String, TaskResult> getTestResults() {
        return testResults;
    }

    public Student getStudent() {
        return student;
    }
}
