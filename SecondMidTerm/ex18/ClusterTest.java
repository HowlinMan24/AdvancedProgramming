package mk.kolokvium2.ex18;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * January 2016 Exam problem 2
 */

class Cluster<T extends Point2D> {
    public List<T> listOfElements;
    public long id;
    public double distance;

    public Cluster() {
        this.listOfElements = new ArrayList<>();
    }

    public void addItem(T element) {
        listOfElements.add(element);
    }

    public void near(long id, int top) {
        Map<Long, Double> distance = new HashMap<>();
        List<Point2D> endPoints = listOfElements.stream().filter(x -> x.id == id).collect(Collectors.toList());
        listOfElements.stream().forEach(x -> {
            Point2D endPoint = endPoints.get(0);
            distance.put(x.id, Math.sqrt(Math.pow(endPoint.x - x.x, 2) + Math.pow(endPoint.y - x.y, 2)));
        });
        AtomicInteger counter = new AtomicInteger(1);
        distance.entrySet().stream().filter(x -> !(x.getValue() == 0.0)).sorted(Map.Entry.comparingByValue()).limit(top).forEach(x -> {
            DecimalFormat df = new DecimalFormat("#.000");
            System.out.printf(counter + ". %d -> %.3f%n", x.getKey(), x.getValue());
//            System.out.printf(counter + ". " + x.getKey() + " -> " + df.format(x.getValue()) + "%n");
            counter.incrementAndGet();
        });
    }
}

class Point2D {
    public long id;
    public float x;
    public float y;

    public Point2D(long id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

public class ClusterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cluster<Point2D> cluster = new Cluster<>();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            long id = Long.parseLong(parts[0]);
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);
            cluster.addItem(new Point2D(id, x, y));
        }
        int id = scanner.nextInt();
        int top = scanner.nextInt();
        cluster.near(id, top);
        scanner.close();
    }
}

// your code here
