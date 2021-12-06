import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class AdditionalTask {
    @Test
    public void additionalTask(){
        MyTree<String> tree = new MyTree<>();
        tree.add("Hello world");
        tree.add("Java");
        tree.add("Kotlin");
        tree.add("Cpp");

        String subs = "ava";

        var g = tree.stream()
                .filter(i -> i.contains(subs))
                .collect(Collectors.toList());
        Assertions.assertEquals(List.of("Java"), g);
    }
}
