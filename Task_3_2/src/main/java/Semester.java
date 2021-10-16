import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Semester {
    @Getter
    private List<Pair<String, Byte>> subjects;

    public Semester() {
        subjects = new ArrayList<>();
    }

    /**
     * Added subject of semester.
     *
     * @param subject name of subject
     * @param mark    mark of subject
     */
    public void addSubject(String subject, Byte mark) {
        subjects.add(new Pair<>(subject, mark));
    }

    /**
     * Checks that subject (by name) exists.
     *
     * @param subject name of subject
     * @return true if exists, else otherwise
     */
    public boolean existsBySubject(String subject) {
        return subjects
                .stream()
                .map(Pair::getFirst)
                .anyMatch(i -> Objects.equals(i, subject));
    }

    /**
     * Removes subject by name.
     *
     * @param name name of subject to remove
     */
    public void removeSubjectByName(String name) {
        subjects = subjects.stream()
                .filter(i -> !Objects.equals(i.getFirst(), name))
                .collect(Collectors.toList());
    }

    /**
     * @return Size of subject list.
     */
    public long getSubjectCount() {
        return subjects.size();
    }

    /**
     *
     * @return All subjects marks (with no subject name).
     */
    public Byte[] getSubjectMarks() {
        return subjects.stream()
                .map(Pair::getSecond)
                .toArray(Byte[]::new);
    }
}
