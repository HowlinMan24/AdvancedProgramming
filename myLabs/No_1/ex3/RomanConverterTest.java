package mk.myLabs.No_1.ex3;

import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n)
                .forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {
    /**
     * Roman to decimal converter
     *
     * @param n number in decimal format
     * @return string representation of the number in Roman numeral
     */
    public static String toRoman(int n) {
        // your solution here
        int temp = n;
        StringBuilder romanNumber = new StringBuilder();
        while (n >= 1000) {
            romanNumber.append("M");
            n -= 1000;
        }
        while (n >= 900) {
            romanNumber.append("CM");
            n -= 900;
        }
        while (n >= 500) {
            romanNumber.append("D");
            n -= 500;
        }
        while (n >= 400) {
            romanNumber.append("CD");
            n -= 400;
        }
        while (n >= 100) {
            romanNumber.append("C");
            n -= 100;
        }
        while (n >= 90) {
            romanNumber.append("XC");
            n -= 90;
        }
        while (n >= 50) {
            romanNumber.append("L");
            n -= 50;
        }
        while (n >= 40) {
            romanNumber.append("XL");
            n -= 40;
        }
        while (n >= 10) {
            romanNumber.append("X");
            n -= 10;
        }
        while (n >= 9) {
            romanNumber.append("IX");
            n -= 9;
        }
        while (n >= 5) {
            romanNumber.append("V");
            n -= 5;
        }
        while (n >= 4) {
            romanNumber.append("IV");
            n -= 4;
        }
        while (n >= 1) {
            romanNumber.append("I");
            n -= 1;
        }
        return romanNumber.toString();
    }


}
