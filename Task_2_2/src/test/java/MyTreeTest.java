import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class MyTreeTest {
    @Test
    void normalAddTest() throws PathNotFoundException {
        MyTree<Integer> tree = new MyTree<>();
        tree.add("", 12);
        tree.add("11", 72);
        tree.add("12", 73);
        tree.add("11-11", 75);
        ArrayList<Integer> et = new ArrayList<>(List.of(12, 72, 73, 75));
        for (var t : tree) {
            et.remove(t);
        }
        Assertions.assertTrue(et.isEmpty());
    }

    @Test
    void checkExceptionTest() {
        MyTree<Integer> tree = new MyTree<>();
        Assertions.assertThrows(PathNotFoundException.class,
                () -> tree.add("12-13-44", 12)
                );
        Assertions.assertThrows(
                KeyAlreadyExistsException.class,
                () -> {
                    tree.add("", 12);
                    tree.add("11", 13);
                    tree.add("11", 14);
                }
        );
        Assertions.assertThrows(
                PathNotFoundException.class,
                () -> tree.add("1-2-14-3-2", 22)
        );
    }

    @Test
    public void containsTest(){
        MyTree<Integer> tree = new MyTree<>();
        tree.add("", 12);
        tree.add("11", 72);
        tree.add("12", 73);
        tree.add("11-11", 75);
        Assertions.assertTrue(tree.contains(75));
        Assertions.assertFalse(tree.contains(-2));
    }
    @Test
    public void containsInEmptySetTest(){
        MyTree<Integer> tree = new MyTree<>();
        Assertions.assertFalse(tree.contains(-2));
    }

    @Test
    public void deleteTest(){
        MyTree<Integer> tree = new MyTree<>();
        tree.add("", 12);
        tree.add("11", 72);
        tree.add("12", 73);
        tree.add("11-11", 75);
        tree.deleteElementWithChildren("11");
        ArrayList<Integer> et = new ArrayList<>(List.of(12,  73));
        for (var t : tree) {
            Assertions.assertTrue(et.contains(t));
        }

        tree.deleteElementWithChildren("");
        int h = 0;
        for(var ignored : tree){
            h++;
        }
        Assertions.assertEquals(0, h);
    }
    @Test
    public void deleteExceptionsTest(){
        MyTree<Integer> tree = new MyTree<>();
        tree.add("", 12);
        tree.add("11", 72);
        tree.add("12", 73);
        tree.add("11-11", 75);
        Assertions.assertThrows(
                PathNotFoundException.class,
                () -> tree.deleteElementWithChildren("4")
        );
    }
}
