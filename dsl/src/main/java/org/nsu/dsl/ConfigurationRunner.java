package org.nsu.dsl;

import org.nsu.dsl.entities.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.nsu.dsl.entities.Task;
import org.nsu.dsl.entities.TaskResult;
import org.apache.commons.io.FileUtils;
import org.nsu.dsl.report.ReportWriter;

public class ConfigurationRunner {

    private final List<Student> students;
    private final String tmpFolder;
    private final String outputFolder;

    private final List<Task> tasks;

    private final List<String> excludedDirs;

    public ConfigurationRunner(
            List<Student> students,
            String tmpFolder,
            String outputFolder,
            List<Task> tasks,
            List<String> excludedDirs
    ) {
        this.students = students;
        this.tmpFolder = tmpFolder;
        this.outputFolder = outputFolder;
        this.tasks = tasks;
        this.excludedDirs = excludedDirs;
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
        Map<String, List<Student>> skippedDirs = new HashMap<>();
        threads.forEach((i) -> ans.put(i.getStudent().getName(), i.getTestResults()));
        threads.forEach((i) -> i.getStrangeDirs().forEach(
                (j) -> {
                    if(skippedDirs.containsKey(j)){
                        skippedDirs.get(j).add(i.getStudent());
                    } else {
                        skippedDirs.put(j, new ArrayList<>());
                        skippedDirs.get(j).add(i.getStudent());
                    }
                }
        ));

        prepareOutput(ans, skippedDirs);
    }



    private void prepareOutput(Map<String, Map<String, TaskResult>> res, Map<String, List<Student>> skippedDirs) {
        new ReportWriter(students, outputFolder, tasks, res, skippedDirs, excludedDirs).prepareReport();
    }
}
