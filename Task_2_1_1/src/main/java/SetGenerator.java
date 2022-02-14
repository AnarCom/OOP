import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SetGenerator {

    private boolean[] array;

    @Getter
    private final List<Integer> ans = new ArrayList<>();

    public SetGenerator(int max_array_size) {
        array = new boolean[max_array_size];
        calcPrimeNumbers();
    }

    public void calcPrimeNumbers() {
        array[0] = false;
        array[1] = false;
        for (int i = 2; i < array.length; i++) {
            array[i] = true;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                ans.add(i);
                for (int j = i+i; j < array.length; j += i) {
                    array[j] = false;
                }
            }
        }
        array = null;
    }

}
