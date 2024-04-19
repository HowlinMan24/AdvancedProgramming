package mk.kolokvium2.ex26;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
//        System.out.println("=== WRITING DISTRIBUTION ===");
//        studentRecords.writeDistribution(System.out);
    }
}

// your code here

class StudentRecords {


    public Map<String, TreeSet<Student>> studentsMap;

    public StudentRecords() {
        this.studentsMap = new TreeMap<>();
    }

    public int readRecords(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            String code = parts[0];
            String directionFax = parts[1];
            List<Integer> gradesList = new ArrayList<>();
            String[] grades = parts[2].split("\\s+");
            Arrays.stream(grades).mapToInt(Integer::parseInt).forEach(gradesList::add);
            if (studentsMap.containsKey(directionFax))
                studentsMap.get(directionFax).add(new Student(code, gradesList));
            else
                Objects.requireNonNull(studentsMap.put(directionFax, new TreeSet<>(Comparator.comparing(Student::averagePoints).thenComparing(Student::getCode)))).add(new Student(code, gradesList));
//            if (studentsMap.containsKey(directionFax))
//                Objects.requireNonNull(studentsMap.put(directionFax, new TreeSet<>(Comparator.comparing(Student::averagePoints)))).add(new Student(code, gradesList));
        });
        return studentsMap.size();

    }

    public void writeTable(PrintStream printStream) {
        PrintWriter pw = new PrintWriter(printStream);

//        studentsMap.forEach((x,y) -> {
//            pw.printf("%s %.2f",x,y.);
//        });
        for (Map.Entry<String, TreeSet<Student>> entry : studentsMap.entrySet()) {
            pw.printf("%s\n", entry.getKey());
            entry.getValue().forEach(x -> pw.println(String.format("%s %f", x.code, x.averagePoints())));
        }

        pw.flush();
    }


    public void writeDistribution(PrintStream printStream) {
        PrintWriter pw = new PrintWriter(printStream);

        List<String> sortedDirections = studentsMap.keySet().stream().sorted(Comparator.comparing(this::totalTens).reversed()).collect(Collectors.toList());
        sortedDirections.forEach(fax -> {
            pw.printf("%s: %d\n", fax, totalTens(fax));
        });
        pw.flush();
        pw.close();
    }

    public int totalTens(String directionFax) {
        return studentsMap.get(directionFax).stream().mapToInt(Student::totalTens).sum();
    }


}

class Student {
    public String code;
    public List<Integer> gradesList;

    public Student(String code, List<Integer> gradesList) {
        this.code = code;
        this.gradesList = gradesList;
    }

    public float averagePoints() {
        return (float) (gradesList.stream().mapToDouble(x -> x).sum() / gradesList.size());
    }


    public String getCode() {
        return code;
    }

    public int totalTens() {
        return (int) gradesList.stream().mapToInt(x -> x).filter(x -> x == 10).count();
    }
}