package mk.kolokvium.updatedEX.ex5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//class MinMax<T extends Comparable<T>> {
//    public T min;
//    public T max;
//
//    public int counter;
//
//    public MinMax() {
//        this.counter = 0;
//    }
//
//    public void update(T element) {
//        if (max == null) {
//            max = element;
//        } else if (element.compareTo(max) > 0) {
//            max = element;
//            counter++;
//        }
//        if (min == null) {
//            min = element;
//        } else if (element.compareTo(min) < 0) {
//            min = element;
//            counter++;
//        }
//    }
//
//    public T max() {
//        return max;
//    }
//
//    public T min() {
//        return min;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder st = new StringBuilder();
//        st.append(String.format(min() + " " + max() + " " + counter)).append("\n");
//        return st.toString();
//    }
//}

class MinMax<T extends Comparable<T>> {
    public T max;
    public T min;
    public int counter;
    public int counterMin;
    public int counterMax;

    public MinMax() {
        this.min = null;
        this.max = null;
        this.counter = 0;
        this.counterMin = 0;
        this.counterMax = 0;
    }

    void update(T element) {
        if (counter == 0) {
            min = element;
            max = element;
        }
        if (max.compareTo(element) == 0) {
            counterMax++;
        } else if (min.compareTo(element) == 0) {
            counterMin++;
        }
        if (max.compareTo(element) < 0) {
            max = element;
            counterMax = 1;
        }
        if (min.compareTo(element) > 0) {
            min = element;
            counterMin = 1;
        }
        counter++;
    }

    @Override
    public String toString() {
        return min + " " + max + " " + (counter - counterMin - counterMax) + "\n";
    }

    public T min() {
        return min;
    }

    public T max() {
        return max;
    }


}

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for (int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}
