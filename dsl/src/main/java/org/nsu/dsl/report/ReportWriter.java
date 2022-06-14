package org.nsu.dsl.report;

import org.nsu.dsl.entities.Student;
import org.nsu.dsl.entities.Task;
import org.nsu.dsl.entities.TaskResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReportWriter {
    private final List<Student> students;
    private final String outputFolder;
    private final List<Task> tasks;

    private final Map<String, Map<String, TaskResult>> res;

    private final Map<String, List<Student>> skippedDirs;

    private final List<String> excludedDirectories;

    public ReportWriter(
            List<Student> students,
            String outputFolder,
            List<Task> tasks,
            Map<String, Map<String, TaskResult>> res,
            Map<String, List<Student>> skippedDirs,
            List<String> excludedDirectories) {
        this.students = students;
        this.outputFolder = outputFolder;
        this.tasks = tasks;
        this.res = res;
        this.skippedDirs = skippedDirs;
        this.excludedDirectories = excludedDirectories;
    }

    private void tableBeginWriter(StringBuilder builder) {
        builder.append("<table border='1'>");
    }

    private void tableEndWriter(StringBuilder builder) {
        builder.append("</table>");
    }

    private void tdWriter(StringBuilder builder, String content) {
        builder.append("<td>")
                .append(content)
                .append("</td>");
    }

    private int criteriaWriter(StringBuilder builder, int sum, boolean criteria) {
        if (criteria) {
            tdWriter(builder, "+");
            sum += 1;
        } else {
            tdWriter(builder, "-");
        }
        return sum;
    }

    public void prepareReport() {
        StringBuilder builder = new StringBuilder();

        tableBeginWriter(builder);

        builder.append("<tr>");

        // header of table
        tdWriter(builder, "name");
        for (var i : tasks) {
            builder.append("<td colspan='4'>")
                    .append(i.getName())
                    .append("</td>");
        }
        tdWriter(builder, "total");
        builder.append("</tr>");

        //body of table
        builder.append("<tr>");
        tdWriter(builder, "");
        for (var i : tasks) {
            tdWriter(builder, "Tests");
            tdWriter(builder, "Javadoc");
            tdWriter(builder, "In time");
            tdWriter(builder, "Total");
        }
        tdWriter(builder, "");
        builder.append("</tr>");

        for (var student : students) {
            builder.append("<tr>");
            tdWriter(builder, student.getName());
            int totalSum = 0;
            for (var task : tasks) {
                int sum = 0;
                boolean exists = res.get(student.getName()).containsKey(task.getName());
                var ans = res.get(student.getName()).get(task.getName());
                sum = criteriaWriter(builder, sum, exists && ans.isTestPassed());
                sum = criteriaWriter(builder, sum, exists && ans.isTestPassed() && ans.isJavadocPassed());
                sum = criteriaWriter(builder, sum, exists && ans.isTestPassed() && ans.isInTime());
                tdWriter(builder, Integer.valueOf(sum).toString());
                totalSum += sum;
            }
            tdWriter(builder, Integer.valueOf(totalSum).toString());
            builder.append("</tr>");
        }

        tableEndWriter(builder);

        tableBeginWriter(builder);

        builder.append("<br> <h2>Unknown Dirs</h2>");

        builder.append("<tr>");
        tdWriter(builder, "Dir");
        tdWriter(builder, "Student(s)");
        builder.append("</tr>");

        for (var dir : skippedDirs.keySet()) {
            if(excludedDirectories.contains(dir)){
                continue;
            }
            builder.append("<tr>");
            tdWriter(builder, dir);
            var studentListBuilder = new StringBuilder();
            skippedDirs.get(dir).forEach((i) -> {
                studentListBuilder.append(i.getName());
                studentListBuilder.append("<br>");
            });
            tdWriter(
                    builder,
                    studentListBuilder.toString()
            );
            builder.append("</tr>");
        }

        tableEndWriter(builder);

        builder.append("</body>");

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(outputFolder + "report.html"));
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
