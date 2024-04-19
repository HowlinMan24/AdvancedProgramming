package mk.kolokvium2.ex8;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidIDException extends Exception {
    public String id;

    public InvalidIDException(String id) {
        this.id = id;
    }

    public String getMessage() {
        return String.format("ID %s is not valid", id);
    }
}

class InvalidDimensionException extends Exception {
    public InvalidDimensionException() {
    }

    public String getMessage() {
        return "Dimension 0 is not allowed!";
    }

}

class Canvas {

    public Map<String, TreeSet<Shape>> canvasMap;

    public Canvas() {
        this.canvasMap = new HashMap<>();
    }

    public void readShapes(InputStream is) throws InvalidIDException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        br.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            boolean flag = false;

            try {
                if (checkId(parts[1]))
                    flag = true;
            } catch (InvalidIDException e) {
                System.out.println(e.getMessage());
            }
            if (parts[0].equals("1") && flag) {
                //Circle
                if (Double.parseDouble(parts[2]) <= 0) {
                    try {
                        throw new InvalidDimensionException();
                    } catch (InvalidDimensionException e) {
                        System.out.println(e.getMessage());
                    }
                }
                String id = parts[1];
                double radius = Double.parseDouble(parts[2]);
                canvasMap.putIfAbsent("Circle", new TreeSet<>(Comparator.comparing(Shape::areaCalculate)));
                canvasMap.get("Circle").add(new Circle(id, radius));
            } else if (parts[0].equals("2") && flag) {
                //Square
                if (Double.parseDouble(parts[2]) <= 0) {
                    try {
                        throw new InvalidDimensionException();
                    } catch (InvalidDimensionException e) {
                        System.out.println(e.getMessage());
                    }
                }
                String id = parts[1];
                double side = Double.parseDouble(parts[2]);
                canvasMap.putIfAbsent("Square", new TreeSet<>(Comparator.comparing(Shape::areaCalculate)));
                canvasMap.get("Square").add(new Square(id, side));
            } else if (parts[0].equals("3") && flag) {
                //Rectangle
                if (Double.parseDouble(parts[2]) <= 0 && Double.parseDouble(parts[3]) <= 0) {
                    try {
                        throw new InvalidDimensionException();
                    } catch (InvalidDimensionException e) {
                        System.out.println(e.getMessage());
                    }
                }
                String id = parts[1];
                double sideOne = Double.parseDouble(parts[2]);
                double sideTwo = Double.parseDouble(parts[3]);
                canvasMap.putIfAbsent("Rectangle", new TreeSet<>(Comparator.comparing(Shape::areaCalculate)));
                canvasMap.get("Rectangle").add(new Rectangle(id, sideOne, sideTwo));
            }


        });

    }

    public boolean checkId(String id) throws InvalidIDException {
        if (id.length() != 6)
            throw new InvalidIDException(id);
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isLetterOrDigit(id.charAt(i)))
                throw new InvalidIDException(id);
        }
        return true;
    }

    public void printAllShapes(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        canvasMap.values().forEach(shapes -> shapes.forEach(pw::println));
        pw.flush();
    }

    public void printByUserId(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        canvasMap.values().forEach(shapes -> shapes.forEach(pw::println));
        pw.flush();
    }

    public void statistics(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        canvasMap.values().forEach(i -> i.forEach(shape -> doubleSummaryStatistics.accept(shape.areaCalculate())));
//        canvasMap.entrySet().forEach(doubleSummaryStatistics.toString());
        printWriter.println(doubleSummaryStatistics);
        printWriter.flush();
    }

    public void scaleShapes(String number, double v) {
        canvasMap.values().forEach(shapes ->
                shapes.forEach(shape -> {
                    if (shape.getId().equals(number)) {
                        shape.setSide(shape.getSide() * v);
                    }
                })
        );
    }
}

abstract class Shape {
    public String id;
    public double side;

    public Shape(String id, double side) {
        this.id = id;
        this.side = side;
    }

    public String getId() {
        return id;
    }

    public double getSide() {
        return side;
    }

    public abstract double areaCalculate();

    public abstract double perimeterCalculate();

    public abstract char getType();

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "Kiro";
    }
}

class Circle extends Shape {

    public Circle(String id, double side) {
        super(id, side);
    }

    @Override
    public double areaCalculate() {
        return Math.PI * side * side;
    }

    @Override
    public double perimeterCalculate() {
        return 2 * side * Math.PI;
    }

    @Override
    public char getType() {
        return 'C';
    }

    @Override
    public String toString() {
        return String.format("Circle: -> Radius: %.2f Area: %.2f Perimeter: %.2f", side, areaCalculate(), perimeterCalculate());
    }
}

class Square extends Shape {

    public Square(String id, double side) {
        super(id, side);
    }

    @Override
    public double areaCalculate() {
        return side * side;
    }

    @Override
    public double perimeterCalculate() {
        return side * 4;
    }

    @Override
    public char getType() {
        return 'S';
    }

    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f", side, areaCalculate(), perimeterCalculate());
    }
}

class Rectangle extends Shape {
    public double sideTwo;

    public Rectangle(String id, double side, double sideTwo) {
        super(id, side);
        this.sideTwo = sideTwo;
    }

    public double getSideTwo() {
        return sideTwo;
    }

    @Override
    public double areaCalculate() {
        return side * sideTwo;
    }

    @Override
    public double perimeterCalculate() {
        return 2 * side + 2 * sideTwo;
    }

    @Override
    public char getType() {
        return 'R';
    }

    @Override
    public String toString() {
        return String.format("Rectangle: -> Sides: %.2f %.2f Area: %.2f Perimeter: %.2f", side, sideTwo, areaCalculate(), perimeterCalculate());
    }

}

public class CanvasTest {

    public static void main(String[] args) throws InvalidIDException {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        canvas.readShapes(System.in);

        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}
