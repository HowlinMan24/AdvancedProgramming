package mk.kolokvium2.ex31;


import java.util.*;
import java.util.stream.Collectors;

class Student {
    public String index;
    public String nameStudent;
    public int firstMidtermPoints;
    public int secondMidtermPoints;
    public int labs;
    public int totalPoints;

    public Student(String index, String nameStudent) {
        this.index = index;
        this.nameStudent = nameStudent;
        this.totalPoints = 0;
    }

    public String getIndex() {
        return index;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public int getFirstMidtermPoints() {
        return firstMidtermPoints;
    }

    public int getSecondMidtermPoints() {
        return secondMidtermPoints;
    }

    public int getLabs() {
        return labs;
    }

    public void setLabs(int labs) {
        this.labs = labs;
    }

    public void setFirstMidtermPoints(int firstMidtermPoints) {
        this.firstMidtermPoints = firstMidtermPoints;
    }

    public void setSecondMidtermPoints(int secondMidtermPoints) {
        this.secondMidtermPoints = secondMidtermPoints;
    }

    public double totalPoints() {
        return (getFirstMidtermPoints() * 0.45) + (secondMidtermPoints * 0.45) + getLabs();
    }

    public int getGrade() {
        if (totalPoints() < 50) {
            return 5;
        } else if (totalPoints() >= 50 && totalPoints() <= 60) {
            return 6;
        } else if (totalPoints() >= 60 && totalPoints() <= 70) {
            return 7;
        } else if (totalPoints() >= 70 && totalPoints() <= 80) {
            return 8;
        } else if (totalPoints() >= 80 && totalPoints() <= 90) {
            return 9;
        } else if (totalPoints() >= 90 && totalPoints() <= 100) {
            return 10;
        }
        return -1;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d", getIndex(), getNameStudent(), getFirstMidtermPoints(), getSecondMidtermPoints(), getLabs(), totalPoints(), getGrade());
    }
}

class AdvancedProgrammingCourse {

    public Map<String, Student> studentMap;

    public AdvancedProgrammingCourse() {
        this.studentMap = new HashMap<>();
    }

    public void addStudent(Student student) {
        this.studentMap.put(student.getIndex(), student);
    }

    public void updateStudent(String idNumber, String activity, int points) {
        if (studentMap.containsKey(idNumber)) {
            if (activity.equals("midterm1")) {
                studentMap.get(idNumber).setFirstMidtermPoints(points);
            } else if (activity.equals("midterm2")) {
                studentMap.get(idNumber).setSecondMidtermPoints(points);
            } else if (activity.equals("labs")) {
                studentMap.get(idNumber).setLabs(points);
            }
        }
    }

    public List<Student> getFirstNStudents(int n) {
        return studentMap.values().stream().sorted(Comparator.comparing(Student::totalPoints).reversed()).limit(n).collect(Collectors.toList());
    }


    public Map<Integer, Integer> getGradeDistribution() {
        Map<Integer, Integer> gradesMap = new TreeMap<>();
        gradesMap.put(5, 0);
        gradesMap.put(6, 0);
        gradesMap.put(7, 0);
        gradesMap.put(8, 0);
        gradesMap.put(9, 0);
        gradesMap.put(10, 0);
        studentMap.values().forEach(x -> {
            gradesMap.compute(x.getGrade(), (key, value) -> (value == null) ? 1 : value + 1);
        });
        return gradesMap;
    }

    public void printStatistics() {
        DoubleSummaryStatistics dss = this.getStats();
        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f%n", dss.getCount(), dss.getMin(), dss.getAverage(), dss.getMax());
    }

    public DoubleSummaryStatistics getStats() {
        return studentMap
                .values()
                .stream()
                .filter(x -> x.totalPoints() >= 50)
                .mapToDouble(Student::totalPoints)
                .summaryStatistics();
    }

}

public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}

