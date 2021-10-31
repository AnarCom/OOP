package Util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Pair <T, S> {
    private T first;
    private S second;
}
