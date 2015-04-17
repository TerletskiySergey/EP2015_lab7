package EPAM2015_lab7;

import java.util.Arrays;

public class MyArrayList implements MyList, RandomAccess {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;

    private Object[] container;
    private int size;
    private int capacity;


    public MyArrayList() {
        this.container = new Object[DEFAULT_INITIAL_CAPACITY];
        this.capacity = DEFAULT_INITIAL_CAPACITY;
    }

    public MyArrayList(int initialCapacity) {
        this.container = new Object[initialCapacity];
        this.capacity = initialCapacity;
    }

    public void add(Object elem) {
        int incrementQuantity = 1;
        checkIfExpandNecessary(incrementQuantity);
        container[size++] = elem;
    }

    public void add(int index, Object element) {
        checkPositionIndex(index);
        int incrementQuantity = 1;
        checkIfExpandNecessary(incrementQuantity);
        if (index == size) {
            add(element);
            return;
        }
        System.arraycopy(container, index, container, index + 1, size - index);
        container[index] = element;
        size++;
    }

    public void addAll(Object[] toAdd) {
        addAll(size, toAdd);
    }

    public void addAll(int index, Object[] toAdd) {
        checkPositionIndex(index);
        int incrementQuantity = toAdd.length;
        checkIfExpandNecessary(incrementQuantity);
        if (index != size) {
            System.arraycopy(container, index, container, index + incrementQuantity, size - index);
        }
        System.arraycopy(toAdd, 0, container, index, incrementQuantity);
        size += incrementQuantity;
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > capacity) {
            expandContainer(capacity = minCapacity);
        }
    }

    public Object get(int index) {
        checkElementIndex(index);
        return container[index];
    }

    public Object remove(int index) {
        checkElementIndex(index);
        Object toReturn = container[index];
        if (index != size - 1) {
            System.arraycopy(container, index + 1, container, index, size - index - 1);
        }
        container = Arrays.copyOf(container, --size);
        return toReturn;
    }

    public void set(int index, Object element) {
        checkElementIndex(index);
        container[index] = element;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (container[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(container, size);
    }

    public String toString() {
        return Arrays.toString(toArray());
    }

    private void checkIfExpandNecessary(int incrementQuantity) {
        int requiredCapacity = size + incrementQuantity;
        while (requiredCapacity > capacity) {
            capacity += capacity >> 1;
        }
        if (capacity < DEFAULT_INITIAL_CAPACITY) {
            capacity = DEFAULT_INITIAL_CAPACITY;
        }
        expandContainer(capacity);
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

    private void expandContainer(int newCapacity) {
        Object[] oldContainer = container;
        container = new Object[newCapacity];
        System.arraycopy(oldContainer, 0, container, 0, size);
    }

    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.indexOf(4));
    }
}
