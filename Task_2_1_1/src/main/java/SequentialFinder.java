import java.util.List;

public class SequentialFinder implements Finder {
    private final List<Integer> list;

    public SequentialFinder(List<Integer> list) {
        this.list = list;
    }

    @Override
    public boolean checkThatAllIsPrime() {
        boolean t = true;
        for (var i : list) {
            t &= SinglePrimeChecker.isPrime(i);
        }
        return t;
    }


}
