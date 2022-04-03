package collection;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SharedList<T> {
    private final Queue<T> list = new LinkedList<>();
    private final Integer limitSize;
    private final Object FULL = new Object();
    private final Object EMPTY = new Object();


    public SharedList(int limitSize) {
        this.limitSize = limitSize;
    }

    public void add(T d) throws InterruptedException {
        synchronized (FULL) {
            while (list.size() >= limitSize) {
                FULL.wait();
            }
            synchronized (list) {
                list.add(d);
            }
            synchronized (EMPTY) {
                EMPTY.notify();
            }
        }
    }

    public T get() throws InterruptedException {
        synchronized (EMPTY) {
            while (list.size() == 0) {
                EMPTY.wait();
            }
            T tmp;
            synchronized (list) {
                tmp = list.poll();
            }
            synchronized (FULL) {
                FULL.notify();
            }
            return tmp;
        }
    }

    public boolean isEmpty() {
        synchronized (list) {
            return list.isEmpty();
        }
    }

    public List<T> reserveAndGet(int reserve) throws InterruptedException {
        synchronized (EMPTY) {
            while (list.isEmpty()) {
                EMPTY.wait();
            }
            List<T> val = new LinkedList<>();
            synchronized (list) {
                int n = Math.min(reserve, list.size());
                for (int i = 0; i < n; i++) {
                    val.add(list.poll());
                }
            }
            synchronized (FULL) {
                FULL.notify();
            }
            return val;
        }
    }
}
