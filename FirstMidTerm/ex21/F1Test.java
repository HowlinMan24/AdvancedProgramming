package mk.kolokvium.updatedEX.ex21;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) throws IOException {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race {
    // vashiot kod ovde
    public List<Driver> drivers;

    public F1Race() {
        this.drivers = new ArrayList<>();
    }

    void readResults(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            String name = parts[0];
//            System.out.println(Arrays.toString(parts));
            List<LocalTime> drivers2 = new ArrayList<>();
            for (int i = 1; i < parts.length; i++) {
                String[] dividedParts = parts[i].split(":");
                int mm = Integer.parseInt(dividedParts[0]);
                int ss = Integer.parseInt(dividedParts[1]);
                int nnn = Integer.parseInt(dividedParts[2]);
                LocalTime time = LocalTime.of(0, mm, ss, nnn * 1000000);
                drivers2.add(time);
            }
//            System.out.println(drivers2);
            drivers.add(new Driver(name, drivers2));
        });

        br.close();
    }

    void printSorted(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        Collections.sort(drivers);
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < drivers.size(); i++) {
            st.append(i + 1).append(". ").append(drivers.get(i)).append("\n");
        }
        pw.println(st);
        pw.flush();
        pw.close();
    }


}

class Driver implements Comparable<Driver> {
    public String name;
    public List<LocalTime> times;

    public Driver(String name, List<LocalTime> times) {
        this.name = name;
        this.times = times;
    }

    public LocalTime getBestTime() {
        LocalTime maxTime = times.get(0);
        for (int i = 1; i < times.size(); i++) {
            if (maxTime.isAfter(times.get(i))) {
                maxTime = times.get(i);
            }
        }
        return maxTime;
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s", name, getBestTime().toString().substring(3).replace(".", ":"));
    }

    @Override
    public int compareTo(Driver o) {
        return this.getBestTime().compareTo(o.getBestTime());
    }

}
