package mk.kolokvium.updatedEX.ex9;

import java.util.*;

class Triple<T extends Number & Comparable<T>> {
    public T first;
    public T second;
    public T third;

    public Triple(T first, T second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public double max() {
        double firstT = first.doubleValue();
        double secondT = second.doubleValue();
        double thirdT = third.doubleValue();
        if (firstT >= secondT && firstT >= thirdT) {
            return firstT;
        } else if (secondT >= firstT && secondT >= thirdT) {
            return secondT;
        } else if (thirdT >= firstT && thirdT >= secondT) {
            return thirdT;
        }
        return 1;
    }

    public double avarage() {
        double firstT = first.doubleValue();
        double secondT = second.doubleValue();
        double thirdT = third.doubleValue();
        return (firstT + secondT + thirdT) / 3.0;
    }

    public void sort() {
        List<T> numbers = new ArrayList<>();
        numbers.add(first);
        numbers.add(second);
        numbers.add(third);
        Collections.sort(numbers);
        first = numbers.get(0);
        second = numbers.get(1);
        third = numbers.get(2);
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", first.doubleValue(), second.doubleValue(), third.doubleValue());
    }
}

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple





