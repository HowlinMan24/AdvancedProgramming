package mk.kolokvium2.ex35;// package mk.kolokvium2.ex35;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class OnlinePayments {
    public Map<String, TreeSet<Student>> studentMap;

    public OnlinePayments() {
        this.studentMap = new HashMap<>();
    }

    public void readItems(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(line -> {
            String[] parts = line.split(";");
            String index = parts[0];
            String itemToPay = parts[1];
            String itemPrice = parts[2];
            studentMap.computeIfAbsent(index, key -> new TreeSet<>(Comparator.comparing(Student::getItemPrice))).add(new Student(index, itemToPay, itemPrice));
        });
    }


    public void printStudentReport(String index, PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        AtomicInteger counter = new AtomicInteger(0);
        studentMap.entrySet().stream()
                .filter(x -> x.getKey().equals(index))
                .forEach(student -> {
                    pw.println(String.format("Student: %s Net: %d Fee: %d Total: %d", index, calculateNet(index), calculateFee(index), calculateFee(index) + calculateNet(index)));
                    pw.println("Items:");
                    student.getValue().forEach(itemPay -> {
                        int currentCounter = counter.incrementAndGet();
                        pw.println(String.format("%d. %s %d", currentCounter, itemPay.getItemToPay(), Integer.parseInt(itemPay.getItemPrice())));
                    });
                });
        if (!studentMap.containsKey(index))
            pw.println("Student " + index + " not found!");

//        studentMap.entrySet().stream()
//                .filter(x -> !x.getKey().equals(index))
//                .forEach(x -> {
//                    pw.println("Student " + x.getKey() + " not found!");
//                });
        pw.flush();
//        pw.close();
    }

    public int calculateNet(String index) {
        return studentMap.entrySet()
                .stream()
                .filter(x -> x.getKey().equals(index))
                .mapToInt(x -> x.getValue().stream()
                        .map(Student::getItemPrice)
                        .mapToInt(Integer::valueOf)
                        .sum())
                .sum();
    }

    public int calculateFee(String index) {
        return (int) Math.round(Math.min(300, Math.max(3, calculateNet(index) * 0.0114)));
    }

}

class Student {
    public String index;
    public String itemToPay;
    public String itemPrice;

    public Student(String index, String itemTiPay, String itemPrice) {
        this.index = index;
        this.itemToPay = itemTiPay;
        this.itemPrice = itemPrice;
    }

    public String getIndex() {
        return index;
    }

    public String getItemToPay() {
        return itemToPay;
    }

    public String getItemPrice() {
        return itemPrice;
    }

}

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}
