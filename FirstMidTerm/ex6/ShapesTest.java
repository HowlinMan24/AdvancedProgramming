package mk.kolokvium.updatedEX.ex6;

import java.sql.SQLData;
import java.util.*;

enum Color {
    RED, GREEN, BLUE
}

interface Scalable {
    void scale(float scaleFactor);
}

interface Stackable {
    float weight();
}

abstract class Shape implements Stackable {
    public String id;
    public Color color;
    public float radius;

    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public float getRadius() {
        return radius;
    }

    abstract char getType();

}

class Circle extends Shape implements Scalable, Stackable {
    public String id;
    public Color color;
    public float radius;

    public Circle(String id, Color color, float radius) {
        this.id = id;
        this.color = color;
        this.radius = radius;
    }

    @Override
    char getType() {
        return 'C';
    }

    @Override
    public void scale(float scaleFactor) {
        this.radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (Math.PI * radius * radius);
    }

    @Override
    public String toString() {
        return String.format("C:%-5s%10s%10.2f", id, color, weight());
    }
}

class Rectangle extends Shape implements Scalable, Stackable {
    public String id;
    public Color color;
    public float width;
    public float height;

    public Rectangle(String id, Color color, float width, float height) {
        this.id = id;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    @Override
    char getType() {
        return 'R';
    }

    @Override
    public void scale(float scaleFactor) {
        this.radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return width * height;
    }

    @Override
    public String toString() {
        return String.format("R:%-5s%10s%10.2f", id, color, weight());
    }
}

class Canvas {

    public String id;
    public Color color;
    public List<Shape> listCanvases;

    public Canvas() {
        this.listCanvases = new ArrayList<>();
    }

    public void add(String id, Color color, float radius) {
        boolean flag = true;
        Circle addCircle = new Circle(id, color, radius);
//        listCanvases.add();
        for (int i = 0; i < listCanvases.size(); i++) {
            if (listCanvases.get(i).weight() < addCircle.weight()) {
                listCanvases.add(i, addCircle);
                flag = false;
                break;
            }
        }
        if (flag)
            listCanvases.add(addCircle);
    }

    public void add(String id, Color color, float width, float height) {
        boolean flag = true;
        Rectangle addRectangle = new Rectangle(id, color, width, height);
//        listCanvases.add();
        for (int i = 0; i < listCanvases.size(); i++) {
            if (listCanvases.get(i).weight() < addRectangle.weight()) {
                listCanvases.add(i, addRectangle);
                flag = false;
                break;
            }
        }
        if (flag)
            listCanvases.add(addRectangle);
    }

    public void scale(String id, float scaleFactor) {
        for (int i = 0; i < listCanvases.size(); i++) {
            if (Objects.equals(listCanvases.get(i).id, id)) {
                if (listCanvases.get(i).getType() == 'R') {
                    Rectangle remake = (Rectangle) listCanvases.remove(i);
                    remake.scale(scaleFactor);
                    listCanvases.add(remake);
                } else {
                    Circle remake = (Circle) listCanvases.remove(i);
                    remake.scale(scaleFactor);
                    listCanvases.add(remake);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (Shape listCanvase : listCanvases) {
            st.append(listCanvase).append("\n");
        }
        return st.toString();
    }


}

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

