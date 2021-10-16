import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class CreditBookTest {

    @ParameterizedTest
    @MethodSource("avgSource")
    public void avgTest(Double value, TestDataProvider provider) {
        Assertions.assertEquals(
                value,
                provider.provideCreditBook().getAvgScore()
        );
    }

    @ParameterizedTest
    @MethodSource("redDiplomaSource")
    public void redDiplomaTest(Boolean ans, TestDataProvider provider) {
        Assertions.assertEquals(
                ans,
                provider.provideCreditBook().canGetRedDiploma()
        );
    }

    @ParameterizedTest
    @MethodSource("canGetAdditionalMoneySource")
    public void additionalMoneyTest(Boolean ans, TestDataProvider provider) {
        Assertions.assertEquals(
                ans,
                provider.provideCreditBook().canGetAdditionalMoney()
        );
    }

    private static Stream<Arguments> canGetAdditionalMoneySource() {
        return Stream.of(
                Arguments.of(
                        true,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        SemesterTest.getStdSemesterForTest(),
                                        SemesterTest.getGoodSemesterForTest()
                                )
                        )
                ),
                Arguments.of(
                        false,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        SemesterTest.getGoodSemesterForTest(),
                                        SemesterTest.getStdSemesterForTest()
                                )
                        )
                ),
                Arguments.of(
                        false,
                        (TestDataProvider) CreditBook::new
                ),
                Arguments.of(
                        true,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        SemesterTest.getEmptySemesterForTest()
                                )
                        )
                )
        );
    }

    private static Stream<Arguments> redDiplomaSource() {
        return Stream.of(
                Arguments.of(
                        false,
                        (TestDataProvider) CreditBook::new
                ),
                Arguments.of(
                        false,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        new Semester(),
                                        new Semester(),
                                        new Semester()
                                )
                        )
                ),
                Arguments.of(
                        false,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        SemesterTest.getGoodSemesterForTest(),
                                        SemesterTest.getStdSemesterForTest(),
                                        SemesterTest.getEmptySemesterForTest()
                                )
                        )
                ),
                Arguments.of(
                        true,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        SemesterTest.getGoodSemesterForTest(),
                                        SemesterTest.getGoodSemesterForTest()
                                )
                        )
                ),
                Arguments.of(
                        false,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        SemesterTest.getEmptySemesterForTest(),
                                        SemesterTest.getEmptySemesterForTest(),
                                        SemesterTest.getEmptySemesterForTest()
                                )
                        )
                )
        );
    }

    private static Stream<Arguments> avgSource() {
        return Stream.of(
                Arguments.of(
                        0D,
                        (TestDataProvider) CreditBook::new
                ),
                Arguments.of(
                        0D,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        new Semester(),
                                        new Semester(),
                                        new Semester()
                                )
                        )
                ),
                Arguments.of(
                        4D,
                        (TestDataProvider) () -> new CreditBook(
                                List.of(
                                        SemesterTest.getStdSemesterForTest(),
                                        SemesterTest.getStdSemesterForTest(),
                                        SemesterTest.getStdSemesterForTest(),
                                        SemesterTest.getEmptySemesterForTest()
                                )
                        )
                )
        );
    }

    @FunctionalInterface
    private interface TestDataProvider {
        CreditBook provideCreditBook();
    }
}
