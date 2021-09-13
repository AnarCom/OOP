import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SubstringFinder {
    public static ArrayList<Long> findSubstringInFile(String fileName, String substring) throws IOException {
        if (substring == null) {
            throw new NullPointerException("substring is null");
        }

        if (fileName == null) {
            throw new NullPointerException("file is null");
        }

        ArrayList<Long> ids = new ArrayList<>();

        InputStream in;
        InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);

        int retVal;
        StringBuilder buf = new StringBuilder();
        long id = 0;
        do {
            retVal = reader.read();
            id++;
            if (retVal != -1) {
                buf.append((char) retVal);
            }
        } while (retVal != -1 && buf.length() < substring.length());
        if (retVal == -1) {
            return ids;
        }

        do {
            if (buf.toString().equals(substring)) {
                ids.add(id - substring.length());
            }
            retVal = reader.read();
            id++;
            if (retVal != -1) {
                buf = new StringBuilder(buf.substring(1));
                buf.append((char) retVal);
            }
        } while (retVal != -1);
        reader.close();
        return ids;
    }
}
