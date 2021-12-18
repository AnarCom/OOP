import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manager for notebook.
 */
public class NotebookManager {
    @Getter
    List<NotebookRecord> records;
    private final ObjectMapper mapper = new ObjectMapper();
    private String filename;

    /**
     * Sets default path to file.
     */
    public NotebookManager() throws IOException {
        this("./data.json");
    }

    /**
     * Reads notebook from setted json file.
     *
     * @param filename Name of file to read json.
     */
    public NotebookManager(String filename) throws IOException {
        File f = new File(filename);
        if (f.exists() && !f.isDirectory()) {
            records = mapper.readValue(
                    new File(filename),
                    new TypeReference<>() {
                    }
            );
        } else {
            records = new ArrayList<>();
        }
        this.filename = filename;
    }

    /**
     * Adds record.
     *
     * @param header Header of record.
     * @param body   Body (text) of record.
     * @return new Record that have been added.
     */
    public NotebookRecord addRecord(String header, String body, Date created) {
        NotebookRecord record = new NotebookRecord(header, body, created);
        records.add(record);
        sortRecords();
        return record;
    }

    public NotebookRecord addRecord(String header, String body) {
        return addRecord(header, body, new Date());
    }

    /**
     * Deletes record by header.
     *
     * @param header header of record to delete.
     */
    public void deleteRecord(String header) {
        records = records.stream()
                .filter(i -> !i.getHeader().equals(header))
                .collect(Collectors.toList());
    }

    private void sortRecords() {
        records.sort((o1, o2) -> o1.getCreated().before(o2.getCreated()) ? 1 : 0);
    }

    /**
     * Deletes all records from notebook.
     */
    public void deleteAll() {
        records.clear();
    }

    /**
     * @param from
     * @param to
     * @param headers
     * @return List of NotebookRecords that's date in [from, to], and header contains words from header
     */
    public List<NotebookRecord> getRecords(Date from, Date to, String[] headers) {
        List<String> headerWords = new ArrayList<>();
        Collections.addAll(headerWords, headers);

        return records.stream()
                .filter(i -> i.getCreated().after(from))
                .filter(i -> i.getCreated().before(to))
                .filter(
                        i -> headerWords.stream()
                                .anyMatch(j -> i.getHeader().contains(j))
                )
                .collect(Collectors.toList());
    }

    public void save() throws IOException {
        File l = new File(filename);
        if (l.exists()) {
            l.delete();
        }

        mapper.writeValue(new File(filename), records);
    }
}
