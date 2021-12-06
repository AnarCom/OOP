import lombok.*;

import java.util.*;

public class MyTree<T extends Comparable<T>> implements Collection<T> {
    private TreeNode<T> root;
    private int size;
    private boolean unchanged = true;

    public MyTree() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        T element = (T) o;

        TreeNode<T> now = root;
        while (now != null) {
            int res = now.getData().compareTo(element);
            if (res < 0) {
                now = now.getLeft();
            } else if (res > 0) {
                now = now.getRight();
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private final Stack<TreeNode<T>> stack = new Stack<>();
            private boolean flag = false;
            private boolean firstInit = true;

            @Override
            public boolean hasNext() {
                if(firstInit){
                    firstInit = false;
                    unchanged = true;
                }
                if (!unchanged) {
                    throw new ConcurrentModificationException();
                }
                prepareData();
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                prepareData();
                var now = stack.pop();

                addChildrenStack(now);
                return now.getData();
            }

            private void addChildrenStack(TreeNode<T> node) {
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                }
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                }
            }

            private void prepareData() {
                if (!flag) {
                    flag = true;
                    if (root != null) {
                        stack.push(root);
                    }
                }
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] genericArray = new Object[size];
        int i = 0;
        for (var j : this) {
            genericArray[i] = j;
            i++;
        }
        return genericArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        int i = 0;
        for (var j : this) {
            a[i] = (T1) j;
            i++;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        unchanged = false;
        // if root is empty -> add to root
        if (root == null) {
            root = new TreeNode<>(t);
            size++;
            return true;
        }

        // if three is not empty -> find vertex where we can add;
        TreeNode<T> buff = root;
        int res;
        while (true) {
            res = buff.getData().compareTo(t);
            if (res < 0) {
                if (buff.getLeft() == null) {
                    break;
                } else {
                    buff = buff.getLeft();
                }
            } else {
                if (buff.getRight() == null) {
                    break;
                } else {
                    buff = buff.getRight();
                }
            }
        }

        var node = new TreeNode<>(t);
        if (res < 0) {
            buff.setLeft(node);
        } else {
            buff.setRight(node);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        unchanged = false;
        T data = (T) o;
        MyTree<T> three = new MyTree<>();
        boolean deleted = false;
        for (var i : this) {
            if (data.compareTo(i) == 0 && !deleted) {
                deleted = true;
            } else {
                three.add(i);
            }
        }
        if (deleted) {
            root = three.root;
            size = three.size;
        }
        return deleted;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (var i : c) {
            if (!contains(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        unchanged = false;
        for (var i : c) {
            add(i);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        unchanged = false;
        boolean res = true;
        for (var i : c) {
            res &= remove(i);
        }
        return res;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        unchanged = false;
        if (isEmpty()) {
            return false;
        }
        boolean flag = true;
        while (flag) {
            for (var i : this) {
                if (!c.contains(i)) {
                    flag = true;
                    remove(i);
                    break;
                } else {
                    flag = false;
                }
            }
        }
        return true;
    }

    @Override
    public void clear() {
        unchanged = false;
        root = null;
    }
}

@Data
@RequiredArgsConstructor
class TreeNode<T extends Comparable<T>> {
    private final T data;
    private TreeNode<T> left;
    private TreeNode<T> right;
}
