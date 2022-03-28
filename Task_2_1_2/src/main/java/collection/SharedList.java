package collection;

import java.io.File;
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

    public boolean isEmpty(){
        synchronized (list){
            return list.isEmpty();
        }
    }


}
