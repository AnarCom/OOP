import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MyTreeTest {
    @Test
    public void addTest() {
        MyTree<Integer> tree = new MyTree<>();
        List<Integer> arr = new ArrayList<>();
        Collections.addAll(arr, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (var i : arr) {
            Assertions.assertTrue(tree.add(i));
        }
        for (var j : tree) {
            arr.remove(j);
        }
        Assertions.assertTrue(arr.isEmpty());
    }

    @Test
    public void containsInEmptyTreeTest(){
        MyTree<Integer> tree = new MyTree<>();
        Integer[] contains = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for(var i : contains){
            Assertions.assertFalse(tree.contains(i));
        }
    }


    @Test
    public void containsTest() {
        MyTree<Integer> tree = new MyTree<>();
        Integer[] contains = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Collections.addAll(tree, contains);
        for (var j : contains) {
            Assertions.assertTrue(tree.contains(j));
        }
        Integer[] notContains = new Integer[]{17, 22, -3, 25};
        for (var j : notContains) {
            Assertions.assertFalse(tree.contains(j));
        }
    }

    @Test
    public void containsAllInEmptyTreeTest(){
        MyTree<Integer> tree = new MyTree<>();
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Assertions.assertFalse(tree.containsAll(contains));
    }

    @Test
    public void containsAllTest() {
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        MyTree<Integer> tree = new MyTree<>();
        Collections.addAll(tree, contains.toArray(new Integer[0]));
        Assertions.assertTrue(tree.containsAll(contains));

        Collection<Integer> notContains = new ArrayList<>();
        Assertions.assertFalse(tree.containsAll(notContains));
    }

    @Test
    public void emptyTreeTest() {
        MyTree<Integer> tree = new MyTree<>();
        Assertions.assertEquals(0, tree.size());
        Assertions.assertTrue(tree.isEmpty());
    }

    @Test
    public void notEmptyTreeTest() {
        MyTree<Integer> tree = new MyTree<>();
        tree.add(12);
        tree.add(13);
        Assertions.assertEquals(2, tree.size());
        Assertions.assertFalse(tree.isEmpty());
    }

    @Test
    public void removeTest() {
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        MyTree<Integer> tree = new MyTree<>();
        Collections.addAll(tree, contains.toArray(new Integer[0]));

        for (var j : contains) {
            Assertions.assertTrue(tree.remove(j));
        }
        Assertions.assertTrue(tree.isEmpty());
        Assertions.assertEquals(0, tree.size());
        Assertions.assertFalse(tree.remove(123213));
    }

    @Test
    public void removeAllTest() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, arr);
        MyTree<Integer> tree = new MyTree<>();
        Collections.addAll(tree, arr);
        Assertions.assertTrue(tree.removeAll(contains));
        Assertions.assertTrue(tree.isEmpty());
        Assertions.assertEquals(0, tree.size());
        Assertions.assertFalse(tree.remove(123213));
    }

    @Test
    public void retainAllTest() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer[] retain = new Integer[]{1, 2, -4};
        Collection<Integer> contains = new ArrayList<>();
        Collections.addAll(contains, arr);
        Collection<Integer> forRetain = new ArrayList<>();
        Collections.addAll(forRetain, retain);

        MyTree<Integer> tree = new MyTree<>();
        Collections.addAll(tree, arr);
        tree.retainAll(forRetain);
        for(var i : tree){
            Assertions.assertTrue(forRetain.contains(i));
        }
    }

    @Test
    public void toArrayTest(){
        MyTree<Integer> t = new MyTree<>();
        t.add(12);
        t.add(13);
        var g = t.toArray();
        Assertions.assertArrayEquals(new Integer[]{12, 13}, g);
    }
    @Test
    public void toEmptyArrayTest(){
        Assertions.assertArrayEquals(new Integer[0], new MyTree<Integer>().toArray());
    }

    @Test
    public void toArrayWithBaseTest(){
        MyTree<Integer> t = new MyTree<>();
        t.add(12);
        t.add(13);
        var g = t.toArray(new Integer[2]);
        Assertions.assertArrayEquals(new Integer[]{12, 13}, g);
    }
    @Test
    public void toEmptyArrayWithBaseTest(){
        Assertions.assertArrayEquals(new Integer[0], new MyTree<Integer>().toArray(new Integer[0]));
    }

    @Test
    public void exceptionTest(){
        Assertions.assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    MyTree<Integer> tr = new MyTree<>();
                    tr.add(123);
                    for (var t1: tr) {
                        tr.add(t1);
                    }
                }
        );
    }
}
