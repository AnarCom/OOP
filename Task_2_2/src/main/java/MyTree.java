import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;

public class MyTree<T> implements Iterable<T> {
    private boolean changed = false;
    private TreeNode<T> root;

    /**
     * adds value to tree
     *
     * @param path path to add (separated with -)
     * @param data data to add
     * @throws PathNotFoundException if path is not accessed
     */
    public void add(String path, T data) throws PathNotFoundException {
        changed = true;
        var a = parsePath(path);
        if (root == null && a.length != 0) {
            throw new PathNotFoundException();
        } else if (root == null) {
            root = new TreeNode<>(data);
            return;
        }

        addRec(a, 0, data, root);

    }

    /**
     * Checks that value exists in tree
     *
     * @param data data to check
     * @return true if value exists
     */
    public boolean contains(T data) {
        for (var i : this) {
            if (i.equals(data)) {
                return true;
            }
        }
        return false;
    }

    private void addRec(String[] arr, int i, T data, TreeNode<T> node) throws PathNotFoundException {
        if (i == arr.length - 1) {
            if (node.getMap().containsKey(arr[i])) {
                throw new KeyAlreadyExistsException();
            }
            node.getMap().put(arr[i], new TreeNode<>(data));
            return;
        }
        if (!node.getMap().containsKey(arr[i])) {
            throw new PathNotFoundException();
        }
        addRec(arr, i + 1, data, node.getMap().get(arr[i]));
    }

    private String[] parsePath(String path) {
        if (Objects.equals(path, "")) {
            return new String[0];
        }
        return path.split("-");
    }

    public Iterator<T> DFS() {
        return new Iterator<>() {
            private boolean init = false;
            private final Stack<TreeNode<T>> stack = new Stack<>();

            @Override
            public boolean hasNext() {
                if (!init) {
                    init();
                    changed = false;
                }
                if (changed) {
                    throw new ConcurrentModificationException();
                }
                return !stack.isEmpty();
            }

            private void init() {
                if (root != null) {
                    stack.push(root);
                }
                init = true;
            }

            @Override
            public T next() {
                var g = stack.pop();
                for (var i : g.getMap().keySet()) {
                    stack.push(g.getMap().get(i));
                }
                return g.getData();
            }
        };
    }

    public Iterator<T> BFS() {
        return new Iterator<>() {
            private boolean init = false;
            private final Queue<TreeNode<T>> queue = new ArrayDeque<>();

            @Override
            public boolean hasNext() {
                if (!init) {
                    init();
                    changed = false;
                }
                if (changed) {
                    throw new ConcurrentModificationException();
                }
                return !queue.isEmpty();
            }

            private void init() {
                if (root != null) {
                    queue.add(root);
                }
                init = true;
            }

            @Override
            public T next() {
                var g = queue.peek();
                for (var i : g.getMap().keySet()) {
                    queue.add(g.getMap().get(i));
                }
                return g.getData();
            }
        };
    }

    @Override
    public Iterator<T> iterator() {
        return DFS();
    }

    public void deleteElementWithChildren(String path) {
        var t = parsePath(path);
        if(t.length == 0){
            root = null;
            return;
        }
        deleteElement(t, 0, root);
    }

    private void deleteElement(String[] path, int i, TreeNode<T> now){
        if(path.length == i+1){
            if(!now.getMap().containsKey(path[i])){
                throw new PathNotFoundException();
            }
            now.getMap().remove(path[i]);
            return;
        }
        deleteElement(path, i+1, now.getMap().get(path[i]));
    }

}

class TreeNode<T> {
    public TreeNode(T data) {
        this.data = data;
    }

    private final T data;
    Map<String, TreeNode<T>> map = new HashMap<>();

    public T getData() {
        return data;
    }

    public Map<String, TreeNode<T>> getMap() {
        return map;
    }
}