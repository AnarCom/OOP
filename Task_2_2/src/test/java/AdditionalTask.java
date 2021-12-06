import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AdditionalTask {
    @Test
    public void additionalTask(){
        MyTree<String> three = new MyTree<>();
        three.add("Hello world");
        three.add("Java");
        three.add("Kotlin");
        three.add("Cpp");

        List<String> subst = new ArrayList<>();
        subst.add("Hello");
        subst.add("pp");

        Stream.of(three)
                .map(i -> {
                    System.out.println(i);
                    return i;
                });
    }
}
