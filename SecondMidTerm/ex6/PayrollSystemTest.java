package mk.kolokvium2.ex6;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


class PayrollSystem {

    public Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
    public Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();

    public List<Employee> employeeList;

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
        this.employeeList = new ArrayList<>();
    }

    public void readEmployees(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(line -> {
            String[] parts = line.split(";");
            if (parts[0].equals("H")) {
                String id = parts[1];
                String level = parts[2];
                double hours = Double.parseDouble(parts[3]);
                employeeList.add(new HourlyEmployee(id, level, hours, hourlyRateByLevel));
            } else {
                String id = parts[1];
                String level = parts[2];
                List<Integer> ticketPoints = Arrays.stream(parts).skip(3).map(Integer::parseInt).collect(Collectors.toList());
                employeeList.add(new FreelanceEmployee(id, level, ticketPoints, ticketRateByLevel));
            }
        });
        br.close();
    }


    public Map<String, Set<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {

        Map<String, Set<Employee>> employeeSet = new TreeMap<>();
        PrintWriter pw = new PrintWriter(os);

        levels.forEach(key -> employeeSet.put(key, new TreeSet<>(Comparator.comparing(Employee::salaryCalculator).thenComparing(Employee::getLevel))));

        employeeList.stream()
                .filter(employee -> employeeSet.containsKey(employee.level))
//                .sorted(Comparator.comparing(Employee::salaryCalculator).thenComparing(Employee::getLevel))
                .forEach(employee -> employeeSet.get(employee.level).add(employee));

        return employeeSet
                .entrySet()
                .stream()
                .filter(x -> !x.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x, TreeMap::new));
    }
}


abstract class Employee {
    public String id;
    public String level;

    public Employee(String id, String level) {
        this.id = id;
        this.level = level;
    }

    public abstract double salaryCalculator();

    public String getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Employee{";
    }
}

class HourlyEmployee extends Employee {
    public double hours;
    public Map<String, Double> hourlyRateByLevel;

    public HourlyEmployee(String id, String level, double hours, Map<String, Double> hourlyRateByLevel) {
        super(id, level);
        this.hours = hours;
        this.hourlyRateByLevel = hourlyRateByLevel;
    }

    @Override
    public double salaryCalculator() {
        if (hours < 40)
            return hourlyRateByLevel.get(getLevel()) * hours;
        return hourlyRateByLevel.get(getLevel()) * 40 + ((hours - 40) * 1.5 * hourlyRateByLevel.get(getLevel()));
    }

    public double getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f", id, level, salaryCalculator(), (hours > 40) ? 40 : hours, hours - 40);
    }
}

class FreelanceEmployee extends Employee {
    public List<Integer> ticketPoints;
    public Map<String, Double> ticketRateByLevel;

    public FreelanceEmployee(String id, String level, List<Integer> ticketPoints, Map<String, Double> ticketRateByLevel) {
        super(id, level);
        this.ticketPoints = ticketPoints;
        this.ticketRateByLevel = ticketRateByLevel;
    }

    public List<Integer> getTicketPoints() {
        return ticketPoints;
    }

    @Override
    public double salaryCalculator() {
        return ticketRateByLevel.get(level) * ticketPoints.stream().mapToInt(x -> x).sum();
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %d", id, level, salaryCalculator(), ticketPoints.size(), ticketPoints.stream().mapToInt(x -> x).sum());
    }

}

public class PayrollSystemTest {

    public static void main(String[] args) throws IOException {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();

        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
        }

        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);

        System.out.println("READING OF THE EMPLOYEES DATA");
        payrollSystem.readEmployees(System.in);

        System.out.println("PRINTING EMPLOYEES BY LEVEL");
        Set<String> levels = new LinkedHashSet<>();
        for (int i = 5; i <= 10; i++) {
            levels.add("level" + i);
        }
        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
        result.forEach((level, employees) -> {
            System.out.println("LEVEL: " + level);
            System.out.println("Employees: ");
            employees.forEach(System.out::println);
            System.out.println("------------");
        });


    }
}
