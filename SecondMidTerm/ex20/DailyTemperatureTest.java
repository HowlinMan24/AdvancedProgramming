package mk.kolokvium2.ex20;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * I partial exam 2016
 */

class DailyTemperatures {

    public List<Temperature> listOfTemperatures;

    public DailyTemperatures() {
        this.listOfTemperatures = new ArrayList<>();
    }

    public void readTemperatures(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        br.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            if (parts[1].contains("C")) {
                listOfTemperatures.add(new Celsius(Integer.parseInt(parts[0]), Arrays.stream(parts).skip(1).collect(Collectors.toList())));
            } else {
                listOfTemperatures.add(new Fahrenheit(Integer.parseInt(parts[0]), Arrays.stream(parts).skip(1).collect(Collectors.toList())));
            }
        });


    }

    public void writeDailyStats(PrintStream printStream, char type) {
        PrintWriter pw = new PrintWriter(printStream);
        listOfTemperatures.stream()
                .sorted(Comparator.comparing(Temperature::getDay))
                .forEach(temperature -> {
                    char temperatureType = temperature.getType();
                    if (temperatureType == 'C') {
                        if (type == 'C') {
                            Celsius temperatureCasted = (Celsius) temperature;
                            printCasted(temperatureCasted.temperatures, pw, temperatureCasted.day);
                        } else {
                            printCastedChanged((Celsius) temperature, pw);
                        }
                    } else if (temperatureType == 'F') {
                        if (type == 'F') {
                            Fahrenheit temperatureCasted = (Fahrenheit) temperature;
                            printCasted(temperatureCasted.temperatures, pw, temperatureCasted.day);
                        } else {
                            printCastedChanged((Fahrenheit) temperature, pw);
                        }
                    }
                });
        pw.flush();
    }

    private static void printCastedChanged(Fahrenheit temperature, PrintWriter pw) {
        DoubleSummaryStatistics dss = temperature.temperatures.stream().mapToDouble(x -> Double.parseDouble(String.valueOf(x))).summaryStatistics();
        pw.println(String.format("%d: Count:%-3d Min:  %-3.2fF Max:  %-3.2fF Avg:  %-3.2fF", temperature.day, dss.getCount(), temperature.convertType(dss.getMin(), 'F'), temperature.convertType(dss.getMax(), 'F'), temperature.convertType(dss.getAverage(), 'F')));
    }

    private static void printCastedChanged(Celsius temperature, PrintWriter pw) {
        DoubleSummaryStatistics dss = temperature.temperatures.stream().mapToDouble(x -> Double.parseDouble(String.valueOf(x))).summaryStatistics();
        pw.println(String.format("%d: Count:%-3d Min:  %-3.2fF Max:  %-3.2fF Avg:  %-3.2fF", temperature.day, dss.getCount(), temperature.convertType(dss.getMin(), 'F'), temperature.convertType(dss.getMax(), 'F'), temperature.convertType(dss.getAverage(), 'F')));
    }

    private static void printCasted(List<Integer> temperatureCasted, PrintWriter pw, int temperatureCasted1) {
        DoubleSummaryStatistics dss = temperatureCasted.stream().mapToDouble(x -> Double.parseDouble(String.valueOf(x))).summaryStatistics();
        pw.println(String.format("%d: Count:%-3d Min:  %-3.2fC Max:  %-3.2fC Avg:  %-3.2fC", temperatureCasted1, dss.getCount(), dss.getMin(), dss.getMax(), dss.getAverage()));
    }


}

abstract class Temperature {
    public int day;
    public List<String> temperaturesThroughOutDay;

    public Temperature(int day, List<String> temperaturesThroughOutDay) {
        this.day = day;
        this.temperaturesThroughOutDay = temperaturesThroughOutDay;
    }

    public int getDay() {
        return day;
    }

    abstract public char getType();

    abstract public double convertType(double parameter, char type);
}

class Celsius extends Temperature {
    public List<Integer> temperatures;

    public Celsius(int day, List<String> temperaturesThroughOutDay) {
        super(day, temperaturesThroughOutDay);
        this.temperatures = new ArrayList<>();
        temperatures.addAll(temperaturesThroughOutDay
                .stream()
                .map(x -> x.replaceAll("C", ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList()));
    }

    @Override
    public char getType() {
        return 'C';
    }

    public double convertType(double parameter, char type) {

        return ((parameter - 32) * 5) / 9;

    }

}

class Fahrenheit extends Temperature {
    public List<Integer> temperatures;

    public Fahrenheit(int day, List<String> temperaturesThroughOutDay) {
        super(day, temperaturesThroughOutDay);
        this.temperatures = new ArrayList<>();
        temperatures.addAll(temperaturesThroughOutDay
                .stream()
                .map(x -> x.replaceAll("F", ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList()));

    }

    @Override
    public char getType() {
        return 'F';
    }


    public double convertType(double parameter, char type) {
        return ((parameter * 9) / 5) + 32;
    }

}

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

// Vashiot kod ovde