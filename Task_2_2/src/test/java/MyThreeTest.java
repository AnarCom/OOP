import com.sun.tools.attach.VirtualMachine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyThreeTest {
    @Test
    public void addTest() {
        MyThree<Integer> three = new MyThree<>();
        List<Integer> arr = new ArrayList<>();
        Collections.addAll(arr, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (var i : arr) {
            Assertions.assertTrue(three.add(i));
        }
        for (var j : three) {
            arr.remove(j);
        }
        Assertions.assertTrue(arr.isEmpty());
    }

    @Test
    public void containsInEmptyThreeTest(){
        MyThree<Integer> three = new MyThree<>();
        Integer[] contains = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for(var i : contains){
            Assertions.assertFalse(three.contains(i));
        }
    }


    @Test
    public void containsTest() {
        MyThree<Integer> three = new MyThree<>();
        Integer[] contains = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Collections.addAll(three, contains);
        for (var j : contains) {
            Assertions.assertTrue(three.contains(j));
        }
        Integer[] notContains = new Integer[]{17, 22, -3, 25};
        for (var j : notContains) {
            Assertions.assertFalse(three.contains(j));
        }
    }

    @Test
    public void containsAllInEmptyThreeTest(){
        MyThree<Integer> three = new MyThree<>();
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        Assertions.assertFalse(three.containsAll(contains));
    }

    @Test
    public void containsAllTest() {
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        MyThree<Integer> three = new MyThree<>();
        Collections.addAll(three, contains.toArray(new Integer[0]));
        Assertions.assertTrue(three.containsAll(contains));

        Collection<Integer> notContains = new ArrayList<>();
        Assertions.assertFalse(three.containsAll(notContains));
    }

    @Test
    public void emptyThreeTest() {
        MyThree<Integer> three = new MyThree<>();
        Assertions.assertEquals(0, three.size());
        Assertions.assertTrue(three.isEmpty());
    }

    @Test
    public void notEmptyThreeTest() {
        MyThree<Integer> three = new MyThree<>();
        three.add(12);
        three.add(13);
        Assertions.assertEquals(2, three.size());
        Assertions.assertFalse(three.isEmpty());
    }

    @Test
    public void removeTest() {
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        MyThree<Integer> three = new MyThree<>();
        Collections.addAll(three, contains.toArray(new Integer[0]));

        for (var j : contains) {
            Assertions.assertTrue(three.remove(j));
        }
        Assertions.assertTrue(three.isEmpty());
        Assertions.assertEquals(0, three.size());
        Assertions.assertFalse(three.remove(123213));
    }

    @Test
    public void removeAllTest() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, arr);
        MyThree<Integer> three = new MyThree<>();
        Collections.addAll(three, arr);
        Assertions.assertTrue(three.removeAll(contains));
        Assertions.assertTrue(three.isEmpty());
        Assertions.assertEquals(0, three.size());
        Assertions.assertFalse(three.remove(123213));
    }

    @Test
    public void retainAllTest() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer[] retain = new Integer[]{1, 2, -4};
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, arr);
        Collection<Integer> forRetain = new ArrayList<>();
        Collections.addAll(forRetain, retain);

        MyThree<Integer> three = new MyThree<>();
        Collections.addAll(three, arr);
        three.retainAll(forRetain);
        for(var i : three){
            Assertions.assertTrue(forRetain.contains(i));
        }
    }

    @Test
    public void toArrayTest(){
        MyThree<Integer> t = new MyThree<>();
        t.add(12);
        t.add(13);
        var g = t.toArray();
        Assertions.assertArrayEquals(new Integer[]{12, 13}, g);
    }
    @Test
    public void toEmptyArrayTest(){
        Assertions.assertArrayEquals(new Integer[0], new MyThree<Integer>().toArray());
    }

    @Test
    public void toArrayWithBaseTest(){
        MyThree<Integer> t = new MyThree<>();
        t.add(12);
        t.add(13);
        var g = t.toArray(new Integer[2]);
        Assertions.assertArrayEquals(new Integer[]{12, 13}, g);
    }
    @Test
    public void toEmptyArrayWithBaseTest(){
        Assertions.assertArrayEquals(new Integer[0], new MyThree<Integer>().toArray(new Integer[0]));
    }
}
