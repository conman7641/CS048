import java.util.Arrays;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
    private int size;
    private Node first;
    private Node last;

    @Override
    public boolean add(int index, T obj) {
        if (index >= size) {
            return add(obj);
        } else if (index == 0) {
            Node n = new Node(obj);
            n.setNext(first);
            first.setPrevious(n);
            n = first;
            size++;
            return true;
        } else {
            Node next = getNodeAt(index);
            Node p = next.getPrevious();
            link(p,new Node(obj));
            size++;
            return true;
        }
    }

    @Override
    public boolean add(T obj) {
        Node n = new Node(obj);
        if (size == 0) {
            first = last = n;  
        } else {
            link(last,n);
            last = n;
        }
        size++;
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getNodeAt(index).getValue();
    }

    @Override
    public int indexOf(T obj) {
        int index = 0;
        Node current = first;
        Node n = new Node(obj);
        while (current != null) {
            if (current.getValue() == n.getValue()) {
                return index;
            }
            index++;
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T obj) {
        int index = size - 1;
        Node n = new Node(obj);
        Node current = last;
        while (current != null) {
            if (current.getValue() == n.getValue()) {
                return index;
            }
            index--;
            current = current.getPrevious();
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
       return new ListIterator<T>() {
        static final int PREVIOUS = 1;
        static final int NEXT = 2;
        int lastCalled = 0;
        //LastCalled indicates if next or previous have been called.
        int nextIndex = 0;


            @Override
           public boolean hasNext() {
               return nextIndex < size;
           }

           @Override
           public T next() {
               if (!hasNext()) {
                   throw new NoSuchElementException();
               }
               lastCalled = NEXT;
               return getNodeAt(nextIndex++).getValue();
           }

           @Override
           public boolean hasPrevious() {
               return nextIndex > 0;
           }

           @Override
           public T previous() {
               if (!hasPrevious()) {
                   throw new NoSuchElementException();
               }
               lastCalled = PREVIOUS;
               return getNodeAt(--nextIndex).getValue();
           }

           @Override
           public int nextIndex() {
               return nextIndex;
           }

           @Override
           public int previousIndex() {
               return nextIndex - 1;
           }

           @Override
           public void remove() {
            if(lastCalled == PREVIOUS) { //Remove current postiion
                LinkedList.this.remove(nextIndex);
            } else if (lastCalled == NEXT) { //Remove last position
                LinkedList.this.remove(--nextIndex);
            } else {
                throw new IllegalStateException("removed called without next or previous");
            }
            lastCalled = 0; //Reset last called to prevent repeated calls.

           }

           @Override
           public void set(T e) {
            if (lastCalled == PREVIOUS) {
                getNodeAt(nextIndex).setValue(e);
            } else if (lastCalled == NEXT) {
                getNodeAt(nextIndex-1).setValue(e);
            } else {
                throw new IllegalStateException("set called without next or previous");
            }
           }

           @Override
           public void add(T e) {
                LinkedList.this.add(nextIndex++, e);
           }

       };
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node n = getNodeAt(index);
        T obj = n.getValue();
        if (n.getPrevious() == null) {
            first = n.getNext();
            if (first != null) {
                first.setPrevious(null);
            }
        } else {
            n.getPrevious().setNext(n.getNext());
        }
        if (n.getClass() == null) {
            last = n.getPrevious();
            if (last != null) {
                last.setNext(null);
            }
        } else {
            n.getNext().setPrevious(n.getPrevious());
        }
        size--;
        return obj;
    }

    @Override
    public T set(int index, T obj) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node old = getNodeAt(index);
        T original = old.getValue();
        Node n = new Node(obj);
        n.setPrevious(old.getPrevious());
        n.setNext(old.getNext());
        old.getPrevious().setNext(n);
        old.getNext().setPrevious(n);
        return original;
    }

    @Override
    public boolean contains(T obj) {
        return this.indexOf(obj) > 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        Object [] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = get(i);
        }
        return arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] a) {
        return (T[])Arrays.copyOf(toArray(), size, a.getClass());
    }

    private Node getNodeAt (int index) {
        if (index >= 0 && index < size) {
            Node c = first;
            for (int i = 0; i < index; i++) {
                c= c.getNext();
            }
            return c;
        }
        return null;
    }

    //insert node n after another node p
    //p is already in chain, n is next
    private void link (Node p, Node n) {
        n.setNext(p.getNext());
        if (n.getNext() != null) {
            n.getNext().setPrevious(n);  // (n+1).previous
        }
        p.setNext(n);
        n.setPrevious(p);
    }



    private class Node {
        private T value;
        private Node previous;
        private Node next;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getPrevious() {
            return previous;
        }

        public Node getNext() {
            return next;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
