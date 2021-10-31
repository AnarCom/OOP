import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class SemesterTest {

    public static Semester getStdSemesterForTest() {
        Semester sem = new Semester();
        sem.addSubject("Math", (byte) 5);
        sem.addSubject("Math1", (byte) 4);
        sem.addSubject("Math2", (byte) 3);
        return sem;
    }

    public static Semester getGoodSemesterForTest() {
        Semester sem = new Semester();
        sem.addSubject("Math", (byte) 5);
        return sem;
    }

    public static Semester getEmptySemesterForTest() {
        return new Semester();
    }

    @ParameterizedTest
    @MethodSource("existsSource")
    void existsBySubject(Semester sem, String name, boolean ans) {
        Assertions.assertEquals(ans, sem.existsBySubject(name));
    }

    @ParameterizedTest
    @MethodSource("removeSource")
    void removeSubjectByName(Semester sem, String nameToRemove, List<Pair<String, Boolean>> pairTest) {
        sem.removeSubjectByName(nameToRemove);
        for (var i : pairTest) {
            Assertions.assertEquals(i.getSecond(), sem.existsBySubject(i.getFirst()));
        }
    }

    @ParameterizedTest
    @MethodSource("subjectCountSource")
    void getSubjectCount(Semester sem, Long ans) {
        Assertions.assertEquals(ans, sem.getSubjectCount());
    }

    @ParameterizedTest
    @MethodSource("subjectMarksList")
    void getSubjectMarks(Semester sem, Byte[] ans) {
        Assertions.assertArrayEquals(ans, sem.getSubjectMarks());
    }

    public static Stream<Arguments> existsSource() {
        return Stream.of(
                Arguments.of(
                        getStdSemesterForTest(),
                        "Math",
                        true
                ),
                Arguments.of(
                        getStdSemesterForTest(),
                        "Math1",
                        true
                ),
                Arguments.of(
                        getStdSemesterForTest(),
                        "Math2",
                        true
                ),
                Arguments.of(
                        getStdSemesterForTest(),
                        "Math3",
                        false
                ),
                Arguments.of(
                        getEmptySemesterForTest(),
                        "Math3",
                        false
                )
        );
    }

    private static Stream<Arguments> subjectCountSource() {
        return Stream.of(
                Arguments.of(
                        getEmptySemesterForTest(),
                        0L
                ),
                Arguments.of(
                        getStdSemesterForTest(),
                        3L
                )
        );
    }


    private static Stream<Arguments> removeSource() {
        return Stream.of(
                Arguments.of(
                        getStdSemesterForTest(),
                        "Math1",
                        List.of(
                                new Pair<>("Math", true),
                                new Pair<>("Math1", false),
                                new Pair<>("Math2", true)
                        )
                ),
                Arguments.of(
                        getStdSemesterForTest(),
                        "Math",
                        List.of(
                                new Pair<>("Math", false),
                                new Pair<>("Math1", true),
                                new Pair<>("Math2", true)
                        )
                ),
                Arguments.of(
                        getEmptySemesterForTest(),
                        "Math1",
                        List.of(
                                new Pair<>("Math", false),
                                new Pair<>("Math1", false),
                                new Pair<>("Math2", false)
                        )
                )
        );
    }

    public static Stream<Arguments> subjectMarksList() {
        return Stream.of(
                Arguments.of(
                        getStdSemesterForTest(),
                        new Byte[]{5, 4, 3}
                ),
                Arguments.of(
                        getEmptySemesterForTest(),
                        new Byte[0]
                )
        );
    }
}
