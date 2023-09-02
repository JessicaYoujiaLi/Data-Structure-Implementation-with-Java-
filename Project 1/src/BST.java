import java.util.Iterator;
import java.util.Stack;

// Binary Search Tree implementation
public class BST<E extends Comparable<E>> implements Iterable<E> {
    private BSTNode<E> root; //Root of the BST
    private int nodecount; //Number of nodes in the BST

    //constructor
    BST() {
        root = null;
        nodecount = 0;
    }
    //Reinitialize tree
    public void clear() {
        root = null;
        nodecount = 0;
    }

    //Insert a record into the tree
    //Records can be anything, but they must be Comparable
    //e: The record to insert
    public void insert(E e) {
        root = inserthelp(root, e);
        nodecount++;
    }
    private BSTNode<E> inserthelp(BSTNode<E> rt, E key) {
        if (rt == null) {return new BSTNode<E>(key);}
        if (rt.value().compareTo(key) > 0){
            rt.setLeft(inserthelp(rt.left(), key));
        }
        else{
            rt.setRight(inserthelp(rt.right(), key));
        }
        return rt;
    }

    //Remove a record from the tree
    //key: They key value of record to remove
    //Returns the record removed, null if there is non
    public E remove(E key) {
        E temp = findhelp(root, key); //First find it
        if (temp != null) {
            root = removehelp(root, key); //Now remove it
            nodecount--;
        }
        return temp;
    }

    public E nodeRemove(BSTNode<E> rt) {
        E temp = rt.value();
        if (temp != null) {
            root = removehelp(root, temp);
            nodecount--;
        }
        return temp;
    }
    private BSTNode<E> removehelp(BSTNode<E> rt, E key) {
        if (rt == null) {
            return null;
        }
        if (rt.value().compareTo(key) > 0) {
            rt.setLeft(removehelp(rt.left(), key));
        } else if (rt.value().compareTo(key) < 0) {
            rt.setLeft(removehelp(rt.left(), key));
        } else { //Found it, remove it
            if (rt.value().equals(key)) { //added
                if (rt.left() == null) {
                    return rt.right();
                } else if (rt.right() == null) {
                    return rt.left();
                } else { //Two children
                    BSTNode<E> temp = getMax(rt.left());
                    rt.setValue(temp.value());
                    rt.setLeft(deleteMax(rt.left()));
                    return rt; //added
                }
            } //add
            rt.setLeft(this.removehelp(rt.left(), key)); //add
        }
        return rt;
    }

    //Find the record with key value k, null if none exists
    //key: The key value to find
    public E find(E key) {
        return findhelp(root, key);
    }
    private E findhelp(BSTNode<E> rt, E key) {
        if (rt == null) {
            return null;
        }
        if (rt.value().compareTo(key) > 0) {
            return findhelp(rt.left(), key);
        } else if (rt.value().compareTo(key) == 0) {
            return rt.value();
        } else {
            return findhelp(rt.right(), key);
        }
    }

    //Return the number of Nodes in the BST
    public int size() {
        return nodecount;
    }
    //Get the depth of the node
    public int getDepth(BSTNode<E> rt, E key) { //input the root and the node
        int start = 0;
        int depth =  getDepthHelp(rt, key, start);
        return depth;
    }
    public int getDepthHelp(BSTNode<E> rt, E key, int depth) {
        if (rt.value().compareTo(key) == 0) { //if the node is the root
            return depth;
        }
        if (rt.value().compareTo(key) > 0) {
            depth++;
            depth = getDepthHelp(rt.left(), key, depth);
        } else {
            depth++;
            depth = getDepthHelp(rt.right(), key, depth);
        }
        return depth;
    }

    //get the max value in the BST
    private BSTNode<E> getMax(BSTNode<E> rt) {
        if (rt.right() == null) {
            return rt;
        } else {
            return getMax(rt.right());
        }
    }
    //delete the max in the BST
    private BSTNode<E> deleteMax(BSTNode<E> rt) {
        if (rt.right() == null) {
            return rt.right();
        } else {
            rt.setRight(deleteMax(rt.right()));
            return rt;
        }
    }

    //print the root
    public void printRoot() {
        System.out.println(root.getElement().toString());
    }
    //get the root
    public BSTNode<E> getRoot() {
        return root;
    }

    //iterator constructor
    @Override
    public Iterator iterator() {
        return new BSTIterator();
    }

    //iterator class
    private class BSTIterator implements Iterator<E> {
        private BSTNode<E> node = root;
        private Stack<BSTNode<E>> nodeStack;
        @Override
        public boolean hasNext() {
            if (!nodeStack.empty() ) {
                return true;
            } else {
                return false;
            }
        }
        private BSTIterator() {
            this.nodeStack = new Stack<BSTNode<E>>();
            this.goLeftMost(root);
        }
        private void goLeftMost(BSTNode<E> t) {
            while (t != null) {
                nodeStack.push(t);
                t = t.left();
            }
        }
        @Override
        public E next() {
            BSTNode<E> top = nodeStack.pop();
            if (top.right()!=null){
                goLeftMost(top.right());
            }
            return top.value();
        }
    }
}


