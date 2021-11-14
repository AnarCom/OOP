import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import util.ResourceReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class NotebookManagerTest {

    private static void writeFromFileToDefaultFile(String path) throws IOException {
        File fileTo = new File("./data.json");
        if(fileTo.exists()){
            Assertions.assertTrue(fileTo.delete());
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("./data.json", true));
        writer.write(ResourceReader.getResource(path));
        writer.close();
    }

    private static void deleteDefaultFile() {
        File f = new File("./data.json");
        f.delete();
    }

    private static SimpleDateFormat getParser() {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        parser.setTimeZone(TimeZone.getTimeZone("Asia/Novosibirsk"));
        return parser;
    }

    @Test
    public void getEmptyNotebookRecordsTest() throws IOException {
        writeFromFileToDefaultFile("empty.json");
        NotebookManager manager = new NotebookManager();
        var l = manager.getRecords();
        Assertions.assertArrayEquals(new NotebookRecord[0], manager.getRecords().toArray());
        deleteDefaultFile();
    }

    @Test
    public void getNonEmptyNotebookRecordsTest() throws IOException, ParseException {
        writeFromFileToDefaultFile("one_note.json");
        NotebookManager manager = new NotebookManager();
        var l = manager.getRecords();
        var date = getParser().parse("2021-11-14 07:06:12+0000");
        Assertions.assertArrayEquals(
                new NotebookRecord[]{
                        new NotebookRecord("header", "body", date)
                },
                manager.getRecords().toArray()
        );

        deleteDefaultFile();
    }

    @Test
    public void addRecordTest() throws IOException, ParseException {
        writeFromFileToDefaultFile("one_note.json");
        NotebookManager manager = new NotebookManager();
        var date = getParser().parse("2021-11-14 07:06:12+0000");
        manager.addRecord("header1", "body1", date);

        Assertions.assertArrayEquals(
                new NotebookRecord[]{
                        new NotebookRecord("header", "body", date),
                        new NotebookRecord("header1", "body1", date)
                },
                manager.getRecords().toArray()
        );

        deleteDefaultFile();
    }

    @Test
    public void getRecordsWithFilterTest() throws IOException, ParseException {
        writeFromFileToDefaultFile("ten_notes.json");
        NotebookManager manager = new NotebookManager();
        var from = getParser().parse("2021-01-01 00:00:00+0000");
        var to = getParser().parse("2021-12-25 00:00:00+0000");
        var headerWords = new String[] {"1", "2", "3", "14", "25"};
        Assertions.assertArrayEquals(
                new NotebookRecord[]{
                        new NotebookRecord("1", "11", getParser().parse("2021-12-14 07:06:12+0000")),
                        new NotebookRecord("2", "21", getParser().parse("2021-10-14 07:06:12+0000")),
                        new NotebookRecord("342", "31", getParser().parse("2021-11-14 07:06:12+0000"))
                },
                manager.getRecords(from, to, headerWords).toArray()
        );

        deleteDefaultFile();
    }

    @Test
    public void deleteAllTest() throws IOException {
        writeFromFileToDefaultFile("ten_notes.json");
        NotebookManager manager = new NotebookManager();
        manager.deleteAll();

        Assertions.assertArrayEquals(
                new NotebookRecord[]{},
                manager.getRecords().toArray()
        );

        deleteDefaultFile();
    }

    @Test
    public void saveToFileTest() throws IOException, ParseException, JSONException {
        writeFromFileToDefaultFile("one_note.json");
        NotebookManager manager = new NotebookManager();
        manager.addRecord("record", "body", getParser().parse("2021-12-15 07:06:12+0000"));
        manager.save();

        String content = Files.readString(Paths.get("./data.json"), StandardCharsets.UTF_8);

        JSONAssert.assertEquals(
                ResourceReader.getResource("ans.json"),
                content,
                JSONCompareMode.STRICT
        );

        deleteDefaultFile();
    }

    @Test
    public void saveToFileEmptyListTest() throws IOException, JSONException {
        writeFromFileToDefaultFile("one_note.json");
        NotebookManager manager = new NotebookManager();
        manager.deleteAll();
        manager.save();

        String content = Files.readString(Paths.get("./data.json"), StandardCharsets.UTF_8);
        JSONAssert.assertEquals(
                "[]",
                content,
                JSONCompareMode.STRICT
        );

        deleteDefaultFile();
    }
}
