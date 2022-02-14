import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ParallelFinder implements Finder {
    private final List<Integer> list;
    private final int n;

    public ParallelFinder(List<Integer> list, int n) {
        this.list = list;
        this.n = n;
    }

    @Override
    public boolean checkThatAllIsPrime() {
        List<List<Integer>> dataForThreads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            AtomicInteger c = new AtomicInteger();
            int finalI = i;
            dataForThreads.add(
                    list.stream()
                            .filter((it) -> {
                                if (c.get() % n == finalI) {
                                    c.getAndIncrement();
                                    return true;
                                } else {
                                    c.getAndIncrement();
                                    return false;
                                }
                            })
                            .collect(Collectors.toList())
            );
        }

        List<Checker> threadsList = new ArrayList<>();
        for(var it : dataForThreads){
            threadsList.add(new Checker(it));
            threadsList.get(threadsList.size() - 1).start();
        }

        boolean data = true;
        for(var it : threadsList){
            try {
                it.join();
            } catch (Exception e){
                e.printStackTrace();
            }
            data &= it.getResult();
        }
        return data;
    }

    public static class Checker extends Thread {
        private final List<Integer> list;
        private boolean result;

        public Checker(List<Integer> g) {
            this.list = g;
            this.result = true;
        }

        @Override
        public void run() {
            for (var t : list) {
                result &=SinglePrimeChecker.isPrime(t);
            }
        }

        public boolean getResult() {
            return result;
        }
    }
}
