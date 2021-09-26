import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MySubstringFinder {

    private Integer[] offsets;

    private Integer calcOffset(char c, Map<Character, Integer> offsets, Integer position) {
        if (offsets.containsKey(c)) {
            return offsets.get(c);
        }
        offsets.put(c, position);
        return calcOffset(c, offsets, position);
    }

    @VisibleForTesting
    Integer[] buildOffset(String substr) {
        Map<Character, Integer> offsets = new HashMap<>();
        String reversed = new StringBuilder(substr).reverse().toString();
        Integer[] ret = new Integer[substr.length()];

        if(reversed.length() > 0){
            for (int i = 1; i < reversed.length(); i++) {
                char c = reversed.charAt(i);
                ret[i] = calcOffset(c, offsets, i);
            }
            ret[0] = calcOffset(reversed.charAt(0), offsets, reversed.length());
        }
        return Lists.reverse(Arrays.asList(ret)).toArray(new Integer[0]);
    }

    public List<Long> findSubPositions(String fileName, String substring) throws IOException {
        if (substring == null) {
            throw new NullPointerException("substring is null");
        }

        if (fileName == null) {
            throw new NullPointerException("file is null");
        }

        ArrayList<Long> ids = new ArrayList<>();

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("file not found");
        }

        StringBuilder buff = new StringBuilder(substring.length() * 2);


        reader.close();
        return ids;
    }

}
