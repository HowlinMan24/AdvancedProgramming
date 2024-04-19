package mk.kolokvium.updatedEX.ex23;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class InvalidOperationException extends Exception {
    public String nonValid;

    public InvalidOperationException(String nonValid) {
        this.nonValid = nonValid;
    }

    public String getMessage() {
        return nonValid + " is not allowed option for this question";
    }
}

abstract class Question implements Comparable<Question> {
    public String text;
    public int points;

    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    abstract String getType();
}

class TrueFalse extends Question {
    public String answerTF;

    public TrueFalse(String text, int points, String answerTF) {
        super(text, points);
        this.answerTF = answerTF;
    }

    @Override
    public String toString() {
        return "True/False Question: " + text + " Points: " + points + " Answer: " + answerTF;
    }

    @Override
    String getType() {
        return "TF";
    }

    @Override
    public int compareTo(Question o) {
        return Integer.compare(this.points, o.points);
    }
}

class MultipleChoice extends Question {
    public String answerChoice;

    public MultipleChoice(String text, int points, String answerChoice) throws InvalidOperationException {
        super(text, points);
        if (!answerChoice.equals("A") && !answerChoice.equals("B") && !answerChoice.equals("C") && !answerChoice.equals("D") && !answerChoice.equals("E"))
            throw new InvalidOperationException(answerChoice);
        this.answerChoice = answerChoice;
    }

    @Override
    public String toString() {
        return "Multiple Choice Question: " + text + " Points " + points + " Answer: " + answerChoice;
    }

    @Override
    String getType() {
        return "MC";
    }

    @Override
    public int compareTo(Question o) {
        return Integer.compare(this.points, o.points);
    }
}

class Quiz {
    public List<Question> questionList;

    public Quiz() {
        this.questionList = new ArrayList<>();
    }

    public void addQuestion(String questionData) {
        String[] parts = questionData.split(";");
        if (parts[0].equals("TF")) {
            questionList.add(new TrueFalse(parts[1], Integer.parseInt(parts[2]), parts[3]));
        } else {
            try {
                questionList.add(new MultipleChoice(parts[1], Integer.parseInt(parts[2]), parts[3]));
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void printQuiz(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
//        Collections.sort(questionList);
//        StringBuilder st = new StringBuilder();
//        for (int i = questionList.size() - 1; i >= 0; i--)
//            st.append(questionList.get(i)).append("\n");
//        pw.println(st);
        questionList.sort(Comparator.reverseOrder());
        questionList.forEach(System.out::println);
        pw.flush();
//        pw.close();
    }

    void answerQuiz(List<String> answers, OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        float sumPoints = 0;
        if (questionList.size() != answers.size()) {
            System.out.println("Answers and questions must be of same length!");
        } else {
            for (int i = 0; i < answers.size(); i++) {
                if (questionList.get(i).getType().equals("TF")) {
                    TrueFalse question = (TrueFalse) questionList.get(i);
                    if (question.answerTF.equals(answers.get(i))) {
                        pw.println(String.format("%d. %d.00", (i + 1), question.points));
                        sumPoints += question.points;
                    } else {
                        pw.println(String.format("%d. 0.00", (i + 1)));
                    }
                } else {
                    MultipleChoice question = (MultipleChoice) questionList.get(i);
                    if (question.answerChoice.equals(answers.get(i))) {
                        pw.println(String.format("%d. %d.00", (i + 1), question.points));
                        sumPoints += question.points;
                    } else {
                        pw.println(String.format("%d. -%.2f", (i + 1), question.points * 0.20));
                        sumPoints += (float) (question.points * 0.20 * -1);
                    }
                }
            }
            pw.println(String.format("Total points: %.2f", sumPoints));
        }
        pw.flush();
        pw.close();
    }

}

public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < questions; i++) {
            quiz.addQuestion(sc.nextLine());
        }

        List<String> answers = new ArrayList<>();

        int answersCount = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < answersCount; i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) {
            quiz.printQuiz(System.out);
        } else if (testCase == 2) {
            quiz.answerQuiz(answers, System.out);
        } else {
            System.out.println("Invalid test case");
        }
    }
}