package mk.myLabs.No_5.ex1;

import java.util.*;
import java.util.stream.Collectors;

class ResizableArray<T> {
    private T[] array;
    public int size;

    @SuppressWarnings({"unchecked"})
    public ResizableArray() {
        this.size = 0;
        this.array = (T[]) new Object[size];
    }

    @SuppressWarnings({"unchecked"})
    public void addElement(T element) {
        if (size >= array.length) {
            T[] newArray = (T[]) new Object[size + 1];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size] = element;
        size++;
    }

    @SuppressWarnings({"unchecked"})
    public boolean removeElement(T element) {
        boolean flag = false;
        int counter = 0;
        T[] array2 = (T[]) new Object[array.length];
        for (T t : array) {
            if (Objects.equals(t, element)) {
                flag = true;
                continue;
            }
            array2[counter++] = t;
        }
        array = array2;
        size = counter;
        return flag;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element)
                return true;
        }
        return false;
    }

    @SuppressWarnings({"unchecked"})
    public T[] toArray() {
        T[] obj = (T[]) new Objects[size];
        System.arraycopy(array, 0, obj, 0, size);
        return obj;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int count() {
        return size;
    }

    public T elementAt(int idx) {
        if (idx < 0 && idx > size)
            throw new ArrayIndexOutOfBoundsException();
        return array[idx];
    }

    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src) {
        for (int i = 0; i < src.size; i++) {
            dest.addElement(src.elementAt(i));
        }
    }

    public T[] getArray() {
        return array;
    }
}

class IntegerArray extends ResizableArray<Integer> {

    public double sum() {
        double sum = 0.0;
        for (int i = 0; i < getArray().length; i++) {
            sum += getArray()[i].doubleValue();
        }
        return sum;
    }

    public double mean() {
        return (float) sum() / getArray().length;
    }

    public int countNonZero() {
        return (int) Arrays.stream(getArray()).map(Object::toString).filter(x -> x.equalsIgnoreCase("0")).count();
    }

    public IntegerArray distinct() {
        List<Integer> list = Arrays.stream(getArray()).distinct().collect(Collectors.toList());
        IntegerArray distinctArray = new IntegerArray();
        for (Integer integer : list) {
            distinctArray.addElement(integer);
        }
        return distinctArray;
    }

    public IntegerArray increment(int count) {
        IntegerArray newaArray = new IntegerArray();
        for (int i = 0; i < getArray().length; i++) {
            newaArray.addElement(getArray()[i] + count);
        }
        return newaArray;
    }
}

public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if (test == 0) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while (jin.hasNextInt()) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if (test == 1) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for (int i = 0; i < 4; ++i) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if (test == 2) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while (jin.hasNextInt()) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if (a.sum() > 100)
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if (test == 3) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for (int w = 0; w < 500; ++w) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k = 2000;
                int t = 1000;
                for (int i = 0; i < k; ++i) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for (int i = 0; i < t; ++i) {
                    a.removeElement(k - i - 1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}

