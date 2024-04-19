package mk.myLabs.No_5.ex3;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

class ComplexNumber<T extends Number, U extends Number> implements Comparable<ComplexNumber<T, U>> {
    public T realPart;
    public U imaginaryPart;

    public ComplexNumber(T realPart, U imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public T getReal() {
        return realPart;
    }

    public U getImaginary() {
        return imaginaryPart;
    }

    public double modul() {
        return (Math.sqrt(Math.pow(realPart.doubleValue(), 2) + Math.pow(imaginaryPart.doubleValue(), 2)));
    }


    @Override
    public String toString() {
        if (imaginaryPart.doubleValue() < 0)
            return String.format("%.2f%.2fi", realPart.doubleValue(), imaginaryPart.doubleValue());
        else
            return String.format("%.2f+%.2fi", realPart.doubleValue(), imaginaryPart.doubleValue());
    }

    @Override
    public int compareTo(ComplexNumber<T, U> o) {
        return Double.compare(this.modul(), o.modul());
    }
}

public class ComplexNumberTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test simple functions int
            int r = jin.nextInt();
            int i = jin.nextInt();
            ComplexNumber<Integer, Integer> c = new ComplexNumber<Integer, Integer>(r, i);
            System.out.println(c);
            System.out.println(c.getReal());
            System.out.println(c.getImaginary());
            System.out.println(c.modul());
        }
        if (k == 1) { //test simple functions float
            float r = jin.nextFloat();
            float i = jin.nextFloat();
            ComplexNumber<Float, Float> c = new ComplexNumber<Float, Float>(r, i);
            System.out.println(c);
            System.out.println(c.getReal());
            System.out.println(c.getImaginary());
            System.out.println(c.modul());
        }
        if (k == 2) { //compareTo int
            LinkedList<ComplexNumber<Integer, Integer>> complex = new LinkedList<ComplexNumber<Integer, Integer>>();
            while (jin.hasNextInt()) {
                int r = jin.nextInt();
                int i = jin.nextInt();
                complex.add(new ComplexNumber<Integer, Integer>(r, i));
            }
            System.out.println(complex);
            Collections.sort(complex);
            System.out.println(complex);
        }
        if (k == 3) { //compareTo double
            LinkedList<ComplexNumber<Double, Double>> complex = new LinkedList<ComplexNumber<Double, Double>>();
            while (jin.hasNextDouble()) {
                double r = jin.nextDouble();
                double i = jin.nextDouble();
                complex.add(new ComplexNumber<Double, Double>(r, i));
            }
            System.out.println(complex);
            Collections.sort(complex);
            System.out.println(complex);
        }
        if (k == 4) { //compareTo mixed
            LinkedList<ComplexNumber<Double, Integer>> complex = new LinkedList<ComplexNumber<Double, Integer>>();
            while (jin.hasNextDouble()) {
                double r = jin.nextDouble();
                int i = jin.nextInt();
                complex.add(new ComplexNumber<Double, Integer>(r, i));
            }
            System.out.println(complex);
            Collections.sort(complex);
            System.out.println(complex);
        }
    }
}
