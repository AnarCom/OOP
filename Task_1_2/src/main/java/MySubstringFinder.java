
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Finder of substring in large file.
 */
public class MySubstringFinder {

    private Map<Character, Integer> offsets;

    private Integer calcOffset(char c, Map<Character, Integer> offsets, Integer position) {
        if (offsets.containsKey(c)) {
            return offsets.get(c);
        }
        offsets.put(c, position);
        return calcOffset(c, offsets, position);
    }

    private void buildOffset(String substr) {
        Map<Character, Integer> offsets = new HashMap<>();
        String reversed = new StringBuilder(substr).reverse().toString();
        if (reversed.length() > 0) {
            for (int i = 1; i < reversed.length(); i++) {
                char c = reversed.charAt(i);
                calcOffset(c, offsets, i);
            }
            calcOffset(reversed.charAt(0), offsets, reversed.length());
        }
        this.offsets = offsets;
    }

    private Integer getOffset(Character a, int subStrLen) {
        if (offsets.containsKey(a)) {
            return offsets.get(a);
        }
        return subStrLen;
    }

    /**
     * Finds the substring positions.
     *
     * @param fileName  Name of file to read
     * @param substring Substring that finds alg
     * @return Array of start pos (of substring)
     * @throws IOException if problems with reading from file
     */
    public Long[] findSubPositions(String fileName, String substring) throws IOException {
        if (substring == null) {
            throw new IllegalArgumentException("substring is null");
        }

        if (fileName == null) {
            throw new IllegalArgumentException("path to file is null");
        }
        if (substring.isEmpty()) {
            return new Long[0];
        }
        buildOffset(substring);

        ArrayList<Long> ids = new ArrayList<>();
        Long count = 0L;

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("file not found");
        }


        char[] buffChar = new char[substring.length()];
        // prepare buffer
        if (reader.read(buffChar) < substring.length()) {
            reader.close();
            return ids.toArray(Long[]::new);
        }
        StringBuilder buff = new StringBuilder(String.valueOf(buffChar));
        while (true) {
            boolean eq = false;
            int s = 0;
            for (int i = substring.length() - 1; i >= 0; i--) {
                if (substring.toCharArray()[i] != buff.charAt(i)) {
                    if (i == buff.length() - 1) {
                        s = getOffset(buff.charAt(i), substring.length());
                    } else {
                        s = getOffset(substring.charAt(i), substring.length());
                    }
                    eq = false;
                    break;
                } else {
                    s = 1;
                    eq = true;
                }
            }
            if (eq) {
                ids.add(count);
            }

            for (int i = 0; i < s; i++) {
                int c = reader.read();
                if (c == -1) {
                    reader.close();
                    return ids.toArray(Long[]::new);
                }
                buff.append((char) c);
                buff.deleteCharAt(0);
                count++;
            }
        }
    }
}
