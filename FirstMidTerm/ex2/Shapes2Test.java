package mk.kolokvium.updatedEX.ex2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class IrregularCanvasException extends Exception {
    public String message;
    public double maxArea;

    public IrregularCanvasException(String id, double maxArea) {
        this.message = id;
        this.maxArea = maxArea;
    }

    public String getMessage() {
        return "Canvas " + message + " has a shape with area larger than " + maxArea;
    }
}

abstract class Shape implements Comparable<Shape> {
    public int size;

    public Shape(int size) {
        this.size = size;
    }

    abstract public float totalArea();

    abstract public char getType();

}

class Square extends Shape {
    public Square(int size) {
        super(size);
    }

    @Override
    public float totalArea() {
        return size * size;
    }

    @Override
    public char getType() {
        return 'S';
    }

    @Override
    public int compareTo(Shape o) {
        return Float.compare(this.totalArea(), o.totalArea());
    }
}

class Circle extends Shape {

    public Circle(int size) {
        super(size);
    }

    @Override
    public float totalArea() {
        return (float) (size * size * Math.PI);
    }

    @Override
    public char getType() {
        return 'C';
    }

    @Override
    public int compareTo(Shape o) {
        return Float.compare(this.totalArea(), o.totalArea());
    }
}

class Canvas implements Comparable<Canvas> {
    public String id;
    public List<Shape> shapeList;

    public Canvas(String id, List<Shape> shapeList, double maxArea) throws IrregularCanvasException {
        this.id = id;
        for (Shape shape : shapeList) {
            if (shape.totalArea() > maxArea)
                throw new IrregularCanvasException(id, maxArea);
        }
        this.shapeList = shapeList;
    }

    public int totalShapes() {
        return shapeList.size();
    }

    public int totalCircles() {
        return (int) shapeList.stream().filter(shape -> shape.getType() == 'C').count();
    }

    public int totalSquares() {
        return (int) shapeList.stream().filter(shape -> shape.getType() == 'S').count();
    }

    public float minArea() {
        return shapeList.stream().map(Shape::totalArea).min(Comparator.naturalOrder()).orElse(0.0f);
    }

    public float maxArea() {
        return shapeList.stream().map(Shape::totalArea).max(Comparator.naturalOrder()).orElse(0.0f);
    }

    public float averageArea() {
        float totalSum = (float) shapeList.stream().mapToDouble(Shape::totalArea).sum();
        return (totalSum / shapeList.size());
    }

    public float sumArea() {
        return (float) shapeList.stream().mapToDouble(Shape::totalArea).sum();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f\n", id, totalShapes(), totalCircles(), totalSquares(), minArea(), maxArea(), averageArea());
    }

    @Override
    public int compareTo(Canvas o) {
        return Double.compare(this.sumArea(), o.sumArea());
    }
}

class ShapesApplication implements Comparator<Canvas> {
    public double maxArea;

    public List<Canvas> canvasList;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        this.canvasList = new ArrayList<>();
    }

    void readCanvases(InputStream inputStream) throws IOException, IrregularCanvasException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {

            String[] parts = line.split("\\s+");
            String id = parts[0];

            List<Shape> shapes = new ArrayList<>();

            for (int i = 1; i < parts.length - 1; i++) {
                if (Objects.equals(parts[i], "C")) {
                    shapes.add(new Circle(Integer.parseInt(parts[i + 1])));
                } else if (Objects.equals(parts[i], "S")) {
                    shapes.add(new Square(Integer.parseInt(parts[i + 1])));
                }
            }

            try {
                canvasList.add(new Canvas(id, shapes, maxArea));
            } catch (IrregularCanvasException e) {
                System.out.println(e.getMessage());
            }

        });

        // TODO make canvas stop when entering a canvas that has more area then the maxArea
        br.close();
    }


    void printCanvases(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
//        Comparator<Canvas> canvasComparator = this;
//        canvasList.sort(Comparator.naturalOrder());
        Collections.sort(canvasList);
        canvasList.forEach(pw::print);
        pw.flush();
        pw.close();
    }

    @Override
    public int compare(Canvas o1, Canvas o2) {
        return Double.compare(o1.sumArea(), o2.sumArea());
    }
}


public class Shapes2Test {

    public static void main(String[] args) throws IOException, IrregularCanvasException {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}

