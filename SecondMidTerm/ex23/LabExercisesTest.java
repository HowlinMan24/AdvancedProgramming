package mk.kolokvium2.ex23;

import java.util.*;
import java.util.stream.Collectors;


class Student {
    public String index; // total of 6 numbers
    public List<Integer> pointsPerLab;

    public Student(String index, List<Integer> pointsPerLab) {
        this.index = index;
        this.pointsPerLab = pointsPerLab;
    }

    public int sumPoints() {
        return pointsPerLab.stream().mapToInt(x -> x).sum();
    }

    public double averagePoints() {
        return (double) sumPoints() / 10;
    }

    public String getIndex() {
        return index;
    }

    public boolean getSignature() {
        int counter = 0;
        for (Integer integer : pointsPerLab) {
            if (integer == 0)
                counter++;
        }
        return counter > 2 && pointsPerLab.size() >= 8;
    }

    public boolean getSignature2() {
        int counter = 0;
        for (Integer integer : pointsPerLab) {
            if (integer == 0)
                counter++;
        }
        return counter > 2;
    }

    @Override
    public String toString() {
        return index + ((getSignature()) ? " YES " : " NO ") + (sumPoints() / 10);
    }
}


class LabExercises {
    public List<Student> studentsList;

    public LabExercises() {
        this.studentsList = new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentsList.add(student);
    }

    public void printByAveragePoints(boolean b, int i) {
        if (b)
            studentsList.stream()
                    .sorted(Comparator.comparing(Student::averagePoints).thenComparing(Student::getIndex))
                    .limit(i)
                    .forEach(x -> System.out.println(x.index + ((!x.getSignature()) ? " YES " : " NO ") + (x.sumPoints() + 0.0) + 0));
        else
            studentsList.stream()
                    .sorted(Comparator.comparing(Student::averagePoints).thenComparing(Student::getIndex).reversed())
                    .limit(i)
                    .forEach(x -> System.out.println(x.index + ((!x.getSignature()) ? " YES " : " NO ") + (x.averagePoints() + 0.0) + 0));
    }

    public List<Student> failedStudents() {
        return studentsList.stream()
                .filter(Student::getSignature2)
                .sorted(Comparator.comparing(Student::getIndex).thenComparing(Student::sumPoints))
                .collect(Collectors.toList());
    }


    public Map<Integer, Double> getStatisticsByYear() {

        Map<Integer, Double> statsPerYear = new HashMap<>();
        studentsList.forEach(x -> statsPerYear.putIfAbsent(Character.getNumericValue(x.index.charAt(1)), averagePerYear(x.index.charAt(1))));
//        studentsList.stream()
//                .filter(Student::getSignature)
//                .forEach(x -> statsPerYear.get(Character.getNumericValue(x.index.charAt(1))).));
        return statsPerYear;
    }

    public double averagePerYear(char x) {
        return (double) studentsList.stream()
                .filter(Student::getSignature)
                .filter(y -> y.index.charAt(1) == x)
                .mapToInt(Student::sumPoints).sum()
                / studentsList.stream()
                .filter(y -> y.index.charAt(1) == x)
                .count();
    }
}

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}
