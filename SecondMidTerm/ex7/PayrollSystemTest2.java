package mk.kolokvium2.ex7;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BonusNotAllowedException extends Throwable {
    public BonusNotAllowedException() {
    }
}

class PayrollSystem {

    public Map<String, Double> hourlyRateByLevel;
    public Map<String, Double> ticketRateByLevel;
    public List<Employee> listOfEmployees;

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
        this.listOfEmployees = new ArrayList<>();
    }

    public Employee createEmployee(String line) throws BonusNotAllowedException {
        String[] parts = line.split(";");
        if (parts[0].equals("H")) {
            String id = parts[1];
            String level = parts[2];
            double hours = Double.parseDouble(parts[3]);
            if (parts[4].contains("%")) {
                if (Double.parseDouble(parts[4].trim().replace("%", "").substring(5)) > 20)
                    throw new BonusNotAllowedException();
            } else if (Double.parseDouble(parts[4].trim().replace("%", "").substring(5)) > 1000) {
                throw new BonusNotAllowedException();
            } else {
                listOfEmployees.add(new HourlyEmployee(id, level, hours, parts[4].trim().replace("%", "").substring(5)));
                return new HourlyEmployee(id, level, hours, parts[4].trim().replace("%", "").substring(5));
            }
        } else {
            String id = parts[1];
            String level = parts[2];
            List<Integer> points = Arrays.stream(parts).skip(3).limit(parts.length - 3).map(Integer::parseInt).collect(Collectors.toList());

//            double bonus = Double.parseDouble(parts[parts.length - 1].trim().replace("%", ""));
            if (parts[4].contains("%")) {
                if (Double.parseDouble(parts[parts.length - 1].trim().replace("%", "").substring(5)) > 20)
                    throw new BonusNotAllowedException();
            } else if (Double.parseDouble(parts[parts.length - 1].trim().replace("%", "").substring(5)) > 1000) {
                throw new BonusNotAllowedException();
            } else {
                listOfEmployees.add(new FreelanceEmployee(id, level, points, parts[parts.length - 1].trim().substring(5)));
                return new FreelanceEmployee(id, level, points, parts[parts.length - 1].trim().substring(5));
            }
        }
        return null;
    }

    public Map<String, Double> getOvertimeSalaryForLevels() {
        Map<String, Double> peopleMap = new HashMap<>();
        listOfEmployees.stream()
                .filter(employee -> employee.getType().equals("H"))
                .forEach(employee -> {
                    HourlyEmployee hourlyEmployee = (HourlyEmployee) employee;
//                    double calculateSalary = (hourlyEmployee.getHours() > 40) ? hourlyEmployee.getHours() * hourlyRateByLevel.get(hourlyEmployee.getLevel()) : (40 * hourlyRateByLevel.get(hourlyEmployee.getLevel())) + ((hourlyEmployee.getHours() - 40) * (hourlyRateByLevel.get(hourlyEmployee.level) * 1.5));
                    double calculatesSalary = (40 * hourlyRateByLevel.get(hourlyEmployee.getLevel())) + ((hourlyEmployee.getHours() - 40) * (hourlyRateByLevel.get(hourlyEmployee.level) * 1.5));
                    if (peopleMap.containsKey(hourlyEmployee.getLevel())) {
                        double currentTotal = peopleMap.get(hourlyEmployee.getLevel());
                        currentTotal += calculatesSalary;
                        peopleMap.put(hourlyEmployee.getLevel(), currentTotal);
                    } else {
                        peopleMap.put(hourlyEmployee.getLevel(), calculatesSalary);
                    }
                });
        return peopleMap;
    }

    public void printStatisticsForOvertimeSalary() {
        DoubleSummaryStatistics dss = getOvertimeSalaryForLevels().values().stream().mapToDouble(Double::valueOf).summaryStatistics();
        System.out.printf("Statistics for overtime salary: Min: %.2f Average:%.2f Max:%.2f Sum:%.2f%n", dss.getMin(), dss.getAverage(), dss.getMax(), dss.getSum());
    }

    public Map<String, Integer> ticketsDoneByLevel() {
        Map<String, Integer> peopleMap = new HashMap<>();
        listOfEmployees.stream()
                .filter(employee -> employee.getType().equals("F"))
                .forEach(employee -> {
                    FreelanceEmployee freelanceEmployee = (FreelanceEmployee) employee;
                    if (peopleMap.containsKey(freelanceEmployee.getLevel())) {
                        int currentTotal = peopleMap.get(freelanceEmployee.getLevel());
                        currentTotal += freelanceEmployee.getPoints();
                        peopleMap.put(freelanceEmployee.getLevel(), currentTotal);
                    } else {
                        peopleMap.put(freelanceEmployee.getLevel(), freelanceEmployee.getPoints());
                    }
                });
        return peopleMap;
    }

    public Collection<Employee> getFirstNEmployeesByBonus(int n) {
        return listOfEmployees.stream().sorted(Comparator.comparing(this::calculateBonus).reversed()).limit(n).collect(Collectors.toList());
    }

    private double calculateBonus(Employee x) {
        if (x.getType().equals("H")) {
            HourlyEmployee hourlyEmployee = (HourlyEmployee) x;
            if (x.getBonus().contains("%")) {
                return (Double.parseDouble(hourlyEmployee.getBonus()) / 100) * ((hourlyEmployee.getHours() > 40) ? hourlyEmployee.getHours() * hourlyRateByLevel.get(hourlyEmployee.getLevel()) : (40 * hourlyRateByLevel.get(hourlyEmployee.getLevel())) + ((hourlyEmployee.getHours() - 40) * (hourlyRateByLevel.get(hourlyEmployee.level) * 1.5)));
            } else {
                return Double.parseDouble(hourlyEmployee.getBonus());
            }
        } else {
            FreelanceEmployee freelanceEmployee = (FreelanceEmployee) x;
            if (x.getBonus().contains("%")) {
                return freelanceEmployee.getPoints() * (Double.parseDouble(freelanceEmployee.getBonus()) / 100);
            } else {
                return Double.parseDouble(freelanceEmployee.bonus);
            }
        }
    }
}

abstract class Employee {
    public String name;
    public String level;

    public Employee(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    abstract public String getType();

    abstract public String getBonus();
}

class HourlyEmployee extends Employee {
    public double hours;
    public String bonus;

    public HourlyEmployee(String name, String level, double hours, String bonus) {
        super(name, level);
        this.hours = hours;
        this.bonus = bonus;
    }

    public double getHours() {
        return hours;
    }

    @Override
    public String getType() {
        return "H";
    }

    @Override
    public String getBonus() {
        return bonus;
    }
}

class FreelanceEmployee extends Employee {
    public List<Integer> points;
    public String bonus;

    public FreelanceEmployee(String name, String level, List<Integer> pointsArray, String bonusPercent) {
        super(name, level);
        this.points = new ArrayList<>();
        points.addAll(pointsArray);
        this.bonus = bonusPercent;
    }

    public int getPoints() {
        return points.stream().mapToInt(x -> x).sum();
    }

    @Override
    public String getType() {
        return "F";
    }

    @Override
    public String getBonus() {
        return bonus;
    }
}

public class PayrollSystemTest2 {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
        }

        Scanner sc = new Scanner(System.in);

        int employeesCount = Integer.parseInt(sc.nextLine());

        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
        Employee emp = null;
        for (int i = 0; i < employeesCount; i++) {
            try {
                emp = ps.createEmployee(sc.nextLine());
            } catch (BonusNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }

        int testCase = Integer.parseInt(sc.nextLine());

        switch (testCase) {
            case 1: //Testing createEmployee
                if (emp != null)
                    System.out.println(emp);
                break;
            case 2: //Testing getOvertimeSalaryForLevels()
                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
                });
                break;
            case 3: //Testing printStatisticsForOvertimeSalary()
                ps.printStatisticsForOvertimeSalary();
                break;
            case 4: //Testing ticketsDoneByLevel
                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
                });
                break;
            case 5: //Testing getFirstNEmployeesByBonus (int n)
                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
                break;
        }

    }
}