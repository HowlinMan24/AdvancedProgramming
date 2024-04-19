package mk.kolokvium.updatedEX.ex11;

import java.util.Scanner;

class ZeroDenominatorException extends Exception {
    public String getMessage() {
        return "Denominator cannot be zero";
    }
}

class GenericFraction<T extends Number, U extends Number> {
    public T numerator;
    public U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        if (denominator.equals(0)) {
            throw new ZeroDenominatorException();
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        double numerator2 = gf.numerator.doubleValue();
        double denominator2 = gf.denominator.doubleValue();
        double GCD = findGCD(numerator2, denominator2);
        double lcm = (numerator2 * denominator2) / GCD;
        return new GenericFraction<Double, Double>(numerator2 * lcm, denominator2 * lcm);
    }

    public double findGCD(double numerator2, double denominator2) {
        if (numerator2 > denominator2) {
            for (double i = denominator2; i >= 1; i--) {
                if ((numerator2 % i) == 0 && (denominator2 % i) == 0) {
                    return i;
                }
            }
        }
        return 1;
    }

    public double toDouble() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f", numerator.doubleValue(), denominator.doubleValue());
    }
}

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch (ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

// вашиот код овде

