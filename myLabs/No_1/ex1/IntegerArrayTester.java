package mk.myLabs.No_1.ex1;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

class ArrayReader {
    public static IntegerArray readIntegerArray(InputStream input) {
        Scanner jin = new Scanner(input);
        int n = jin.nextInt();
        int a[] = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = jin.nextInt();
        jin.close();
        return new IntegerArray(a);
    }
}

class IntegerArray {

    //variables
    private int[] Array;

    //constructor
    public IntegerArray(int[] array) {
        this.Array = new int[array.length];
        for (int i = 0; i < array.length; i++)
            this.Array[i] = array[i];
    }

    //function for length
    public int length() {
        return Array.length;
    }

    //getElementAt
    public int getElementAt(int i) {
        return Array[i];
    }

    //sum
    public int sum() {
        int sumArray = 0;
        for (int i = 0; i < length(); i++)
            sumArray += Array[i];
        return sumArray;
    }

    //average
    public double average() {
        return (double) sum() / length();
    }

    //getSorted
    public IntegerArray getSorted() {
        int[] sortedArray = Arrays.copyOf(Array, length());
        Arrays.sort(sortedArray);
        return new IntegerArray(sortedArray);
    }

    //concat()
    public IntegerArray concat(IntegerArray ia) {
//        int j = 0;
//        IntegerArray combinedArray = new IntegerArray(Array);
//        for (int i = 0; i < Array.length; i++)
//            combinedArray.Array[i] = this.Array[i];
//        combinedArray.lenght += ia.Array.length;
//        for (int i = Array.length; i < combinedArray.length(); i++)
//            combinedArray.Array[i] = ia.Array[j++];
//        return combinedArray;
        int combinedArray[] = new int[Array.length + ia.Array.length];
        int x = 0, j = 0;
        for (int i = 0; i < Array.length; i++) {
            combinedArray[i] = Array[i];
            x++;
        }
        for (int i = x; i < Array.length + ia.Array.length; i++) {
            combinedArray[i] = ia.Array[j++];
        }
        return new IntegerArray(combinedArray);
    }

    @Override
    public String toString() {
        return Arrays.toString(Array);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerArray that = (IntegerArray) o;
        return Arrays.equals(Array, that.Array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(Array);
    }
}


public class IntegerArrayTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        IntegerArray ia = null;
        switch (s) {
            case "testSimpleMethods":
                ia = new IntegerArray(generateRandomArray(scanner.nextInt()));
                testSimpleMethods(ia);
                break;
            case "testConcat":
                testConcat(scanner);
                break;
            case "testEquals":
                testEquals(scanner);
                break;
            case "testSorting":
                testSorting(scanner);
                break;
            case "testReading":
                testReading(new ByteArrayInputStream(scanner.nextLine().getBytes()));
                break;
            case "testImmutability":
                int a[] = generateRandomArray(scanner.nextInt());
                ia = new IntegerArray(a);
                testSimpleMethods(ia);
                testSimpleMethods(ia);
                IntegerArray sorted_ia = ia.getSorted();
                testSimpleMethods(ia);
                testSimpleMethods(sorted_ia);
                sorted_ia.getSorted();
                testSimpleMethods(sorted_ia);
                testSimpleMethods(ia);
                a[0] += 2;
                testSimpleMethods(ia);
                ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(integerArrayToString(ia).getBytes()));
                testSimpleMethods(ia);
                break;
        }
        scanner.close();
    }

    static void testReading(InputStream in) {
        IntegerArray read = ArrayReader.readIntegerArray(in);
        System.out.println(read);
    }

    static void testSorting(Scanner scanner) {
        int[] a = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        System.out.println(ia.getSorted());
    }

    static void testEquals(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        int[] c = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        IntegerArray ib = new IntegerArray(b);
        IntegerArray ic = new IntegerArray(c);
        System.out.println(ia.equals(ib));
        System.out.println(ia.equals(ic));
        System.out.println(ib.equals(ic));
    }

    static void testConcat(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        IntegerArray array1 = new IntegerArray(a);
        IntegerArray array2 = new IntegerArray(b);
        IntegerArray concatenated = array1.concat(array2);
        System.out.println(concatenated);
    }

    static void testSimpleMethods(IntegerArray ia) {
        System.out.print(integerArrayToString(ia));
        System.out.println(ia);
        System.out.println(ia.sum());
        System.out.printf("%.2f\n", ia.average());
    }


    static String integerArrayToString(IntegerArray ia) {
        StringBuilder sb = new StringBuilder();
        sb.append(ia.length()).append('\n');
        for (int i = 0; i < ia.length(); ++i)
            sb.append(ia.getElementAt(i)).append(' ');
        sb.append('\n');
        return sb.toString();
    }

    static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = scanner.nextInt();
        }
        return a;
    }


    static int[] generateRandomArray(int k) {
        Random rnd = new Random(k);
        int n = rnd.nextInt(8) + 2;
        int a[] = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = rnd.nextInt(20) - 5;
        }
        return a;
    }

}
