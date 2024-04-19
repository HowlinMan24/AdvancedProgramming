package mk.myLabs.No_1.ex1;

import java.util.Arrays;

public final class IntegerArray {

    //variables
    private int[] Array;
    private int lenght;

    //constructor
    public IntegerArray(int[] array) {
        this.Array = new int[array.length];
        lenght = Array.length;
        for (int i = 0; i < array.length; i++)
            this.Array[i] = array[i];
    }

    //function for length
    int length() {
        return lenght;
    }

    //getElementAt
    int getElementAt(int i) {
        return Array[i];
    }

    //sum
    int sum() {
        int sumArray = 0;
        for (int i = 0; i < length(); i++)
            sumArray += Array[i];
        return sumArray;
    }

    //average
    double average() {
        return (double) sum() / length();
    }

    //getSorted
    IntegerArray getSorted() {
        IntegerArray newArray = new IntegerArray(Array);
        for (int i = 0; i < length(); i++)
            newArray.Array[i] = Array[i];
        Arrays.sort(newArray.Array);
        return newArray;
    }

    //concat()
    IntegerArray concat(IntegerArray ia) {
        int j = 0;
        IntegerArray combinedArray = new IntegerArray(Array);
        for (int i = 0; i < Array.length; i++)
            combinedArray.Array[i] = this.Array[i];
        combinedArray.lenght += ia.Array.length;
        for (int i = Array.length; i < combinedArray.length(); i++)
            combinedArray.Array[i] = ia.Array[j++];
        return combinedArray;
    }

    @Override
    public String toString() {
        return "IntegerArray{" +
                "Array=" + Arrays.toString(Array) +
                ", lenght=" + lenght +
                '}';
    }
}
