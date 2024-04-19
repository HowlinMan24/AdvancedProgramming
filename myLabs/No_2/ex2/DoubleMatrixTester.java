package mk.myLabs.No_2.ex2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.lang.Exception;

//class InvalidColumnNumberException extends Throwable {
//
//    public String message;
//
//    public InvalidColumnNumberException(String message) {
//        this.message = message;
//    }
//
//    public String message() {
//        return message;
//    }
//}
//
//class InvalidRowNumberException extends Throwable {
//
//    public String message;
//
//    public InvalidRowNumberException(String message) {
//        this.message = message;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//}
//
//class InsufficientElementsException extends Throwable {
//
//    public String message;
//
//    public InsufficientElementsException(String message) {
//        this.message = message;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//}

final class DoubleMatrix {

    private final double a[];
    private final int m;
    private final int n;

    public DoubleMatrix(double a[], int m, int n) throws InsufficientElementsException {
        this.a = a;
        this.m = m;
        this.n = n;
        if (m * n < a.length)
            throw new InsufficientElementsException("Insufficient number of elements");
    }

    public String getDimensions() {
        return "[" + m + ", " + n + "]";
    }

    public int rows() {
        return m;
    }

    public int columns() {
        return n;
    }

    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if (row > m)
            throw new InvalidRowNumberException("Invalid row number");
        double maxElement = a[0];
        for (double i : a) {
            if (maxElement < i) {
                maxElement = i;
            }
        }
        return maxElement;
    }

    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        if (column > n)
            throw new InvalidColumnNumberException("Invalid column number");
        double maxElement = a[0];
        for (double i : a) {
            if (maxElement < i) {
                maxElement = i;
            }
        }
        return maxElement;
    }

    public double sum() {
        double sum = 0.0;
        for (double i : a) {
            sum += i;
        }
        return sum;
    }

    public double[] toSortedArray() {
        double[] Array = new double[a.length];
        for (int i = a.length - 1; i >= 0; i--) {
            Array[i] = a[i];
        }
        return Array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DoubleMatrix that = (DoubleMatrix) o;
        return m == that.m && n == that.n && Arrays.equals(a, that.a);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(m, n);
        result = 31 * result + Arrays.hashCode(a);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                st.append(a[j]).append('\t').append('\n');
            }
        }
        return st.toString();
    }
}

class MatrixReader {

    public static double[] arrayDouble;

    public static DoubleMatrix read(InputStream input) {
        Scanner userInput = new Scanner(input);
        int counter = 0;
        int firstInput = userInput.nextInt();
        int secondInput = userInput.nextInt();
        while (userInput.hasNextDouble()) {
            arrayDouble = new double[counter];
            arrayDouble[counter++] = userInput.nextDouble();
        }
        try {
            return new DoubleMatrix(arrayDouble, firstInput, secondInput);
        } catch (InsufficientElementsException e) {
            e.getMessage();
        }
        return null;
    }


}

public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception, InsufficientElementsException {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        try {
                            DoubleMatrix f1 = fm;
                            DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(), fm.columns());
                            System.out
                                    .println("Equals check 1: "
                                            + f1.equals(f2)
                                            + " "
                                            + f2.equals(f1)
                                            + " "
                                            + (f1.hashCode() == f2.hashCode() && f1
                                            .equals(f2)));
                        } catch (InsufficientElementsException e) {
                            System.out.println("Exception caught: " + e.getMessage());
                        }

                    }

                    if (maxOps % 2 == 0) {
                        try {
                            DoubleMatrix f1 = fm;
                            DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                    7.5}, 1, 1);

                            System.out
                                    .println("Equals check 2: "
                                            + f1.equals(f2)
                                            + " "
                                            + f2.equals(f1)
                                            + " "
                                            + (f1.hashCode() == f2.hashCode() && f1
                                            .equals(f2)));
                        } catch (InsufficientElementsException e) {
                            System.out.println("Exception caught: " + e.getMessage());
                        }
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}
