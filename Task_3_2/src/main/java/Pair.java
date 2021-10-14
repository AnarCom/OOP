import lombok.Data;

/**
 * Class for pair. T is type of first argument, L - second.
 */
@Data
public class Pair<T, L> {
    private T first;
    private L second;
}
