public class Pair<T, L> {
    private T first;
    private L second;

    public Pair(T first, L second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public L getSecond() {
        return second;
    }

    public void setSecond(L second) {
        this.second = second;
    }
}
