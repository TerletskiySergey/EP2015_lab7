package EPAM2015_lab7;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class MyLinkedList implements MyList, Stack, Queue {

    private static class Element {
        private Element next;
        private Element prev;
        private Object val;

        public Element() {
        }

        public Element(Object e) {
            this.val = e;
        }

        public boolean hasNext() {
            return next != null;
        }

        public Element next() {
            return next;
        }

        public void remove() {
            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.prev = prev;
            }
        }

        public Object get() {
            return val;
        }

        public void set(Object val) {
            this.val = val;
        }
    }

    private Element head;
    private int size;

    @Override
    public void add(Object e) {
        if (size == 0) {
            addFirst(e);
            return;
        }
        Element newElem = new Element(e);
        Element last = element(size - 1);
        last.next = newElem;
        newElem.prev = last;
        size++;
    }

    public void addFirst(Object e) {
        Element newElem = new Element(e);
        if (head == null) {
            head = newElem;
        } else {
            head.prev = newElem;
            newElem.next = head;
            head = newElem;
        }
        size++;
    }

    @Override
    public void add(int index, Object value) {
        checkPositionIndex(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        Element curElem = element(index);
        Element newElem = new Element(value);
        curElem.prev.next = newElem;
        newElem.prev = curElem.prev;
        newElem.next = curElem;
        curElem.prev = newElem;
        size++;
    }

    @Override
    public void addAll(Object[] c) {
        addAll(size, c);
    }

    @Override
    public void addAll(int index, Object[] toAdd) {
        checkPositionIndex(index);
        if (toAdd.length == 0) {
            return;
        }
        Element first = new Element(toAdd[0]);
        Element last = first;
        for (int i = 1; i < toAdd.length; last = last.next(), i++) {
            last.next = new Element(toAdd[i]);
            last.next().prev = last;
        }
        if (index == 0) {
            head.prev = last;
            last.next = head;
            head = first;
        } else {
            Element insertAfter = element(index - 1);
            Element insertBefore = insertAfter.next();
            insertAfter.next = first;
            first.prev = insertAfter;
            if (index != size) {
                last.next = insertBefore;
                insertBefore.prev = last;
            }
        }
        size += toAdd.length;
    }

    @Override
    public Object get(int index) {
        checkElementIndex(index);
        Element curElem = element(index);
        return curElem.get();
    }

    @Override
    public Object remove(int index) {
        checkElementIndex(index);
        if (index == 0) {
            return removeFirst();
        }
        Element curElement = element(index);
        curElement.remove();
        size--;
        return curElement.get();
    }

    public Object removeFirst() {
        checkIfEmpty();
        Object toReturn = head.get();
        if (size == 1) {
            head = null;
        } else {
            head = head.next();
            head.prev.remove();
        }
        size--;
        return toReturn;
    }

    public Object removeLast() {
        checkIfEmpty();
        if (size == 1) {
            return removeFirst();
        }
        Element last = element(--size);
        last.remove();
        return last.get();
    }

    @Override
    public void set(int index, Object value) {
        checkElementIndex(index);
        Element curElement = element(index);
        curElement.set(value);
    }

    @Override
    public int indexOf(Object value) {
        int index = 0;
        Element curElem = head;
        while (curElem != null) {
            if (curElem.get().equals(value)) {
                return index;
            }
            curElem = curElem.next();
            index++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] toReturn = new Object[size];
        if (size != 0) {
            Element curElement = head;
            for (int i = 0; i < size; curElement = curElement.next(), i++) {
                toReturn[i] = curElement.get();
            }
        }
        return toReturn;
    }

    @Override
    public void offer(Object elem) {
        if (elem == null) {
            throw new NullPointerException();
        }
        add(elem);
    }

    @Override
    public Object peek() {
        if (size == 0) {
            return null;
        }
        return get(0);
    }

    @Override
    public Object poll() {
        if (size == 0) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public void push(Object elem) {
        add(size, elem);
    }

    @Override
    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return removeLast();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[]");
        Element curElem = head;
        while (curElem != null) {
            sb.insert(sb.length() - 1, curElem.get());
            sb.insert(sb.length() - 1, ", ");
            curElem = curElem.next();
        }
        if (sb.length() > 2) {
            sb.delete(sb.length() - 3, sb.length() - 1);
        }
        return sb.toString();
    }

    private Element element(int index) {
        Element curElem = head;
        while (index-- != 0) {
            curElem = curElem.next();
        }
        return curElem;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkIfEmpty() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
    }
}
