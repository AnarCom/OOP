import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if (args.length < 1) {
            System.out.println("parameters expected");
            return;
        }
        NotebookManager manager = new NotebookManager();
        switch (args[0]) {
            case "-add":
                if (validateParameters(args, 3)) {
                    manager.addRecord(args[1], args[2]);
                }
                break;
            case "-show":
                if (args.length == 1) {
                    printRecords(manager.getRecords());
                } else {
                    if (validateParameters(args, 2)) {
                        var from = getDate(args[1]);
                        var to = getDate(args[2]);
                        List<String> list = new ArrayList<>(Arrays.asList(args).subList(3, args.length));
                        printRecords(manager.getRecords(from, to,  list.toArray(new String[0])));
                    }
                }
                break;
            case "-rm":
                if (validateParameters(args, 2)) {
                    manager.deleteRecord(args[1]);
                }
                break;
            default:
                System.out.printf("Unexpected param %s\n", args[0]);
                break;
        }
        manager.save();
    }

    public static boolean validateParameters(String[] args, int expected) {
        if (args.length < expected) {
            System.out.println("parameters expected");
            return false;
        }
        return true;
    }

    public static void printRecords(List<NotebookRecord> records) {
        for (var i : records) {
            System.out.println(i.toString());
        }
    }

    private static Date getDate(String date) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        parser.setTimeZone(TimeZone.getTimeZone("Asia/Novosibirsk"));
        return parser.parse(date + "+0000");
    }
}
