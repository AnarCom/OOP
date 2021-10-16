import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreditBook {
    @Getter
    @Setter
    private List<Semester> semesters;

    public CreditBook() {
        semesters = new ArrayList<>();
    }

    public CreditBook(List<Semester> semesters) {
        this.semesters = semesters;
    }

    private List<Double> getListFromAllSemesters() {
        List<Double> marks = new ArrayList<>();
        for (var i : semesters) {
            marks.addAll(
                    Arrays.stream(i.getSubjectMarks())
                            .map(Double::valueOf)
                            .collect(Collectors.toSet())
            );
        }
        return marks;
    }

    /**
     * @return avg mark for all semesters. 0 if there are zero semesters or in all semesters 0 subjects
     */
    public Double getAvgScore() {
        if (semesters.size() == 0) {
            return 0D;
        }
        List<Double> marks = getListFromAllSemesters();
        if (marks.size() == 0) {
            return 0D;
        }
        return marks.stream().reduce(0D, Double::sum) / marks.size();
    }

    public boolean canGetRedDiploma() {
        if (semesters.size() == 0) {
            return false;
        }
        List<Double> marks = getListFromAllSemesters();
        if (
                marks.stream()
                        .anyMatch(i -> i <= 3)
        ) {
            return false;
        }

        return getAvgScore() >= 4.5;
    }

    public boolean canGetAdditionalMoney() {
        if (semesters.size() == 0) {
            return false;
        }
        var semester = semesters.get(semesters.size() - 1);
        List<Double> marks =
                Stream.of(
                                semester.getSubjectMarks()
                        )
                        .map(Double::valueOf).collect(Collectors.toList());
        return marks.stream().noneMatch(i -> i != 5);
    }
}
