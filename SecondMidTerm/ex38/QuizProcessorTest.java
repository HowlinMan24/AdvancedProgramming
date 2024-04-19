package mk.kolokvium2.ex38;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class LengthException extends Exception {
    public String message;

    public LengthException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class QuizProcessor {

    public static Map<String, Double> processAnswers(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Map<String, Double> studentAnswers = new TreeMap<>();
        br.lines().forEach(x -> {
            String[] parts = x.split(";");
            try {
                if (parts[1].length() != parts[2].length())
                    throw new LengthException("A quiz must have same number of correct and selected answers");
                String[] rightAnswer = parts[1].replace(",", "").split("\\s+");
                String[] studentAnswer = parts[2].replace(",", "").split("\\s+");
                double sumPoints = 0.0;
                for (int i = 0; i < rightAnswer.length; i++) {
                    if (rightAnswer[i].equalsIgnoreCase(studentAnswer[i])) {
                        sumPoints += 1;
                    } else {
                        sumPoints -= 0.25;
                    }
                }
                studentAnswers.put(parts[0], sumPoints);
            } catch (LengthException e) {
                System.out.println(e.message);
            }
        });
        return studentAnswers;
    }
}

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}
