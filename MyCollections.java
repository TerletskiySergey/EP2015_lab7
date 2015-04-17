package EPAM2015_lab7;

import java.util.Comparator;

public class MyCollections {

    public static void sort(MyList list) {
        sort(list, null);
    }

    public static void sort(MyList list, Comparator comp) {
        if (list instanceof RandomAccess) {
            quickSort(list, 0, list.size() - 1, comp);
        } else {
            bubbleSort(list, comp);
        }
    }

    public static void swap(MyList list, int i, int j) {
        Object buf = list.get(i);
        list.set(i, list.get(j));
        list.set(j, buf);
    }

    public static void copy(MyList dest, MyList src) {
        if (src.size() > dest.size()) {
            throw new IndexOutOfBoundsException("Source does not fit in dest");
        }
        for (int i = 0; i < src.size(); i++) {
            dest.set(i, src.get(i));
        }
    }

    public static void reverse(MyList list) {
        for (int i = 0, j = list.size() - 1; i < list.size() >> 1; i++, j--) {
            swap(list, i, j);
        }
    }

    private static void quickSort(MyList list, int lBorder, int rBorder, Comparator comp) {
        if (lBorder >= rBorder) {
            return;
        }
        int sortedElementIndex = partition(list, lBorder, rBorder, comp);
        quickSort(list, lBorder, sortedElementIndex - 1, comp);
        quickSort(list, sortedElementIndex + 1, rBorder, comp);
    }

    private static int partition(MyList list, int lBorder, int rBorder, Comparator comp) {
        int left = lBorder - 1;
        int right = rBorder;
        Object pivot = list.get(rBorder);
        while (true) {
            while (compareElements(list.get(++left), pivot, comp) < 0) ;
            while (right > lBorder && compareElements(list.get(--right), pivot, comp) > 0) ;
            if (left >= right) {
                break;
            } else {
                swap(list, left, right);
            }
        }
        if (!pivot.equals(list.get(left))) {
            swap(list, left, rBorder);
        }
        return left;
    }

    private static void bubbleSort(MyList list, Comparator comp) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (compareElements(list.get(j), list.get(j + 1), comp) > 0) {
                    swap(list, j, j + 1);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static int compareElements(Object obj1, Object obj2, Comparator comp) {
        if (comp == null) {
            try {
                return ((Comparable) obj1).compareTo(obj2);
            } catch (ClassCastException ex) {
                throw new IllegalArgumentException(comparableCastMsg());
            }
        }
        try {
            return comp.compare(obj1, obj2);
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException(comparatorCastMsg());
        }
    }

    private static String comparableCastMsg() {
        return "List element does not implement Comparable";
    }

    private static String comparatorCastMsg() {
        return "List element does not correspond to Comparator type";
    }
}
