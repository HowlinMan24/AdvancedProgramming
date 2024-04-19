package mk.kolokvium.updatedEX.ex1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Square {

    int size;

    public Square(int size) {
        this.size = size;
    }

    public int perimeter() {
        return 4 * size;
    }

}

class Canvas implements Comparable<Canvas> {

    String id;
    List<Square> squares;

    public Canvas(String id, List<Square> squares) {
        this.id = id;
        this.squares = squares;
    }

    public static Canvas create(String line) {

        String[] parts = line.split("\\s+");
        String id = parts[0];

        List<Square> squares1 = new ArrayList<>();

        squares1 = Arrays.stream(parts).skip(1).
                map(sizeStr -> new Square(Integer.parseInt(sizeStr)))
                .collect(Collectors.toList());

        return new Canvas(id, squares1);

    }

    public int totalSquares() {
        return squares.size();
    }

    public int totalPerimeter() {
        return squares.stream().mapToInt(Square::perimeter)
                .sum();
    }

    @Override
    public int compareTo(Canvas o) {
        return Integer.compare(this.totalPerimeter(), o.totalPerimeter());
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", id, totalSquares(), totalPerimeter());
    }
}

class ShapesApplication {

    public List<Canvas> canvas;

    public ShapesApplication() {
        this.canvas = new ArrayList<>();
    }

    public int readCanvases(InputStream inputStream) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        canvas = br.lines().map(Canvas::create)
                .collect(Collectors.toList());

        br.close();

        return canvas.stream().mapToInt(Canvas::totalSquares).sum();

    }

    public void printLargestCanvasTo(OutputStream outputStream) {
        PrintWriter print = new PrintWriter(outputStream);

        Canvas max = canvas.stream()
                .max(Comparator.naturalOrder()).orElse(null);

        print.println(max);

        print.flush();

    }

}

public class Shapes1Test {

    public static void main(String[] args) throws IOException {
        ShapesApplication shapesApplication = new ShapesApplication();

        try {
            System.out.println("===READING SQUARES FROM INPUT STREAM===");
            System.out.println(shapesApplication.readCanvases(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
