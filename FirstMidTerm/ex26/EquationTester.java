package mk.kolokvium.updatedEX.ex26;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

class Line {
    Double coeficient;
    Double x;
    Double intercept;

    public Line(Double coeficient, Double x, Double intercept) {
        this.coeficient = coeficient;
        this.x = x;
        this.intercept = intercept;
    }

    public static Line createLine(String line) {
        String[] parts = line.split("\\s+");
        return new Line(
                Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2])
        );
    }

    public double calculateLine() {
        return coeficient * x + intercept;
    }

    @Override
    public String toString() {
        return String.format("%.2f * %.2f + %.2f", coeficient, x, intercept);
    }
}

class Equation<in, out> {
    public Supplier<in> supplier;
    public Function<in, out> function;

    public Equation(Supplier<in> supplier, Function<in, out> function) {
        this.supplier = supplier;
        this.function = function;
    }

    public Optional<out> calculate() {
        return Optional.of(function.apply(supplier.get()));
    }

}

class EquationProcessor {
    public static <in, out> void process(List<in> inputs, List<Equation<in, out>> equations) {
        List<Optional<out>> optionalList = new ArrayList<>();
        for (in input : inputs) {
            System.out.println(String.format("Input: %s", input));
            for (Equation<in, out> equation : equations) {
                Optional<out> result = equation.calculate();
                if (result.isPresent()) {
                    optionalList.add(result);
                }
            }
        }
        for (int i = 0; i < 2; i++) {
            System.out.println("Result: " + optionalList.get(i).get());
        }
    }
}

public class EquationTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { // Testing with Integer, Integer
            List<Equation<Integer, Integer>> equations1 = new ArrayList<>();
            List<Integer> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(Integer.parseInt(sc.nextLine()));
            }

            // TODO: Add an equation where you get the 3rd integer from the inputs list, and the result is the sum of that number and the number 1000.

            equations1.add(new Equation<>(
                    () -> inputs.get(2),
                    x -> x + 1000)
            );

            // TODO: Add an equation where you get the 4th integer from the inputs list, and the result is the maximum of that number and the number 100.

            equations1.add(new Equation<>(
                    () -> inputs.get(3),
                    x -> {
                        if (x > 100)
                            return x;
                        return 100;
                    }
            ));

            EquationProcessor.process(inputs, equations1);

        } else { // Testing with Line, Integer
            List<Equation<Line, Double>> equations2 = new ArrayList<>();
            List<Line> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(Line.createLine(sc.nextLine()));
            }

            //TODO Add an equation where you get the 2nd line, and the result is the value of y in the line equation.

            equations2.add(new Equation<>(
                    () -> inputs.get(1),
                    Line::calculateLine
            ));

            //TODO Add an equation where you get the 1st line, and the result is the sum of all y values for all lines that have a greater y value than that equation.

            equations2.add(new Equation<>(
                    () -> inputs.get(0),
                    x -> inputs.stream()
                            .filter(y -> y.calculateLine() > x.calculateLine())
                            .mapToDouble(Line::calculateLine).sum()
            ));

            EquationProcessor.process(inputs, equations2);
        }
    }
}

