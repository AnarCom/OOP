import lombok.*;

import java.util.*;
import java.util.stream.Stream;

public class MyTree<T> implements Collection<T> {
    TreeNode<T> root;
    boolean unchanged = true;
    int size = 0;

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
        for (var i : this) {
            if (o.equals(i)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return DFS();
    }

    public Iterator<T> DFS() {
        return new Iterator<T>() {
            private boolean firstInit = true;
            Stack<TreeNode<T>> st = null;

            @Override
            public boolean hasNext() {
                if(firstInit){
                    firstInit = false;
                    unchanged = true;
                }
                if (!unchanged) {
                    throw new ConcurrentModificationException();
                }
                buildStack();
                return st.size() != 0;
            }

            @Override
            public T next() {
                buildStack();
                TreeNode<T> node = st.pop();
                if (node.getRight() != null) {
                    st.push(node.getRight());
                }
                if (node.getLeft() != null) {
                    st.push(node.getLeft());
                }
                return node.getData();
            }

            private void buildStack() {
                if (st != null) {
                    return;
                }
                st = new Stack<>();
                if (root != null) {
                    st.push(root);
                }
            }
        };
    }

    public Iterator<T> BFS() {
        return new Iterator<>() {
            private boolean firstInit = true;
            Queue<TreeNode<T>> st = null;

            @Override
            public boolean hasNext() {
                if(firstInit){
                    firstInit = false;
                    unchanged = true;
                }
                if (!unchanged) {
                    throw new ConcurrentModificationException();
                }
                buildStack();
                return st.size() != 0;
            }

            @Override
            public T next() {
                buildStack();
                TreeNode<T> node = st.peek();
                if (node.getRight() != null) {
                    st.add(node.getRight());
                }
                if (node.getLeft() != null) {
                    st.add(node.getLeft());
                }
                return node.getData();
            }

            private void buildStack() {
                if (st != null) {
                    return;
                }
                st = new LinkedList<>();
                if (root != null) {
                    st.add(root);
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
        size++;
        if (root == null) {
            root = new TreeNode<>(t);
            return true;
        }

        TreeNode<T> now = root;
        TreeNode<T> prev = root;
        while (now != null) {
            prev = now;
            if (
                    (
                            now.getLeft() == null ?
                                    -1 :
                                    now.getLeft().getMinDepth()
                    ) < (
                            now.getRight() == null ?
                                    -1 :
                                    now.getRight().getMinDepth()
                    )
            ) {
                now = now.getLeft();
            } else {
                now = now.getRight();
            }
        }

        if (prev.getLeft() == null) {
            prev.setLeft(new TreeNode<>(t));
        } else {
            prev.setRight(new TreeNode<>(t));
        }
        rebuildDepth();

        return true;
    }

    private void rebuildDepth() {
        depthCalc(root, 0);
    }

    private int depthCalc(TreeNode<?> node, int buf) {
        if (node == null) {
            return buf;
        }
        int a = depthCalc(node.getLeft(), buf + 1);
        int b = depthCalc(node.getRight(), buf + 1);
        node.setMinDepth(Math.min(a, b));
        return buf;
    }

    @Override
    public boolean remove(Object o) {
        unchanged = false;
        if (root == null)
            return false;

        if (root.getData().equals(o)) {
            size=0;
            if (root.getRight() == null) {
                root = root.getLeft();
            } else if (root.getLeft() == null) {
                root = root.getRight();
            } else {
                var t = root;
                root = null;
                addChildren(t);
            }
            return true;
        }

        if(root.getLeft() != null && root.getLeft().equals(o)){
            var t = root.getLeft();
            root.setLeft(null);
            addChildren(t);
            return true;
        }
        if(root.getRight() != null && root.getRight().equals(o)){
            var t = root.getRight();
            root.setRight(null);
            addChildren(t);
            return true;
        }

        return (root.getRight() != null && deleteRec(root.getRight(), o)) ||
                (root.getLeft() != null && deleteRec(root.getLeft(), o));
    }

    public boolean deleteRec(TreeNode<T> now, Object rm) {
        if (now.getLeft() != null && now.getLeft().equals(rm)) {
            var t = now.getLeft();
            size--;
            now.setLeft(null);
            addChildren(t);
            return true;
        }

        if (now.getRight() != null && now.getRight().equals(rm)) {
            var t = now.getRight();
            size--;
            now.setRight(null);
            addChildren(t);
            return true;
        }

        return (now.getLeft() != null && deleteRec(now.getLeft(), rm)) ||
                (now.getRight() != null && deleteRec(now.getRight(), rm));
    }

    private void addChildren(TreeNode<T> node) {
        addRecursive(node.getLeft());
        addRecursive(node.getRight());
    }

    private void addRecursive(TreeNode<T> node) {
        if (node == null) {
            return;
        }
        add(node.getData());
        addRecursive(node.getLeft());
        addRecursive(node.getRight());
    }


    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }
        boolean ans = true;
        for (var i : c) {
            ans &= contains(i);
        }
        return ans;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (var i : c) {
            add(i);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        unchanged = false;
        boolean flag = true;
        for(var j : c){
            flag &= remove(j);
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        unchanged = false;
        MyTree<T> tr = new MyTree<>();
        for(var j : this){
            if(c.contains(j)){
                tr.add(j);
            }
        }
        this.root = tr.root;
        return true;
    }

    @Override
    public void clear() {
        root = null;
    }

    public Stream<T> additionalTaskStream(){
        Stream.Builder<T> builder = Stream.builder();
        builderBack(builder, root);
        return builder.build();
    }

    public void builderBack(Stream.Builder<T> g, TreeNode<T> now){
        if(now == null)
            return;
        if(now.getRight() == null && now.getLeft() == null){
            g.add(now.getData());
        }
        builderBack(g, now.getLeft());
        builderBack(g, now.getRight());
    }
}

@Data
@RequiredArgsConstructor
class TreeNode<T> {
    private final T data;
    int minDepth = 0;
    private TreeNode<T> left;
    private TreeNode<T> right;
}
