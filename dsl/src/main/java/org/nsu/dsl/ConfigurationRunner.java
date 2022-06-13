package org.nsu.dsl;

import entities.Student;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import entities.Task;
import entities.TaskResult;
import org.apache.commons.io.FileUtils;
import org.nsu.dsl.report.ReportWriter;

public class ConfigurationRunner {

    private final List<Student> students;
    private final String tmpFolder;
    private final String outputFolder;

    private final List<Task> tasks;

    public ConfigurationRunner(List<Student> students, String tmpFolder, String outputFolder, List<Task> tasks) {
        this.students = students;
        this.tmpFolder = tmpFolder;
        this.outputFolder = outputFolder;
        this.tasks = tasks;
    }

    void deleteAndCreate(String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                FileUtils.forceDelete(f);
            }
            FileUtils.forceMkdir(f);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void createTmpFolder() {
        deleteAndCreate(tmpFolder);
    }

    private void createOutputFolder() {
        deleteAndCreate(outputFolder);
    }

    public void executeCheck() {
        createTmpFolder();
        createOutputFolder();
        var threads = students
                .stream()
                .map(i -> new SingleTaskWorker(i, tmpFolder, tasks))
                .collect(Collectors.toList());

        threads.forEach((i) -> i.start());
        threads.forEach((i) -> {
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Map<String, Map<String, TaskResult>> ans = new HashMap<>();
        threads.forEach((i) -> ans.put(i.getStudent().getName(), i.getTestResults()));


        prepareOutput(ans);
    }



    private void prepareOutput(Map<String, Map<String, TaskResult>> res) {
        new ReportWriter(students, outputFolder, tasks, res).prepareReport();
    }
}
