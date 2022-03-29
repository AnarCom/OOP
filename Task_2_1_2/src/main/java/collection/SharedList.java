package collection;

import order.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SharedList<T> {
    private final List<T> list = new LinkedList<>();
    private final Object EMPTY = new Object();
    private final Object FULL = new Object();

    Integer limitSize;

    public SharedList(int limitSize) {
        this.limitSize = limitSize;
    }

    public void add(T d) throws InterruptedException {
        boolean isNeedWait = false;
        synchronized (list) {
            if (list.size() > limitSize) {
                isNeedWait = true;
            }
        }

        if (isNeedWait) {
            synchronized (FULL) {
                FULL.wait();
            }
        }

        synchronized (list) {
            list.add(d);
            synchronized (EMPTY) {
                EMPTY.notify();
            }
        }
    }

    public T get() throws InterruptedException {
        boolean isNeedWait = false;
        synchronized (list) {
            if (list.isEmpty()) {
                isNeedWait = true;
            }
        }

        if (isNeedWait) {
            synchronized (EMPTY) {
                EMPTY.wait();
            }
        }

        synchronized (list) {
            var tmp = list.get(0);
            list.remove(0);
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
        List<T> orders = new ArrayList<>();
        boolean needWait = false;
        synchronized (list) {
            if (list.isEmpty()) {
                needWait = true;
            }
        }
        synchronized (EMPTY) {
            EMPTY.wait();
        }

        synchronized (list) {
            int t = Math.min(list.size(), reserve);
            for (int i = 0; i < t; i++) {
                var order = list.get(i);
                orders.add(order);
            }
            if (t > 0) {
                list.subList(0, t).clear();
            }
            synchronized (FULL) {
                FULL.notify();
            }
        }

        return orders;
    }


}
