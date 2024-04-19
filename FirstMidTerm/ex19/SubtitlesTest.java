package mk.kolokvium.updatedEX.ex19;

import mk.myLabs.No_4.LocalDateTimeTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SubtitlesTest {
    public static void main(String[] args) throws IOException {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде

class Subtitles {

    public List<Subtitle> subtitleList;

    public Subtitles() {
        this.subtitleList = new ArrayList<>();
    }
    /*
    Ke tepam nekoj zadacata ne mi rabotila oti nemam konstruktor....
     */

    public int loadSubtitles(InputStream inputStream) throws IOException {
        Scanner sc = new Scanner(inputStream);
        int coutner = 0;
        while (sc.hasNextLine()) {
            int number = Integer.parseInt(sc.nextLine());
            String[] times = sc.nextLine().split("-->");
            String timeStart = times[0];
            String timeEnd = times[1];
            StringBuilder text = new StringBuilder();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty())
                    break;
                text.append(line).append("\n");
            }
            subtitleList.add(new Subtitle(number, timeStart, timeEnd, text.toString()));
            coutner++;
        }
        return coutner;
    }

    public void print() {
        subtitleList.forEach(System.out::println);
    }

    public void shift(int ms) {
        subtitleList.forEach(x -> x.changeTimes(ms));
    }

}

class Subtitle {
    public int orderNumber;
    public LocalTime timeStart;
    public LocalTime timeEnd;
    public String text;

    public Subtitle(int orderNumber, String timeStart, String timeEnd, String text) {
        this.orderNumber = orderNumber;
        String[] startTime = divideString(timeStart);
        String[] endTime = divideString(timeEnd);
        this.timeStart = LocalTime.of(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]), Integer.parseInt(startTime[2]), Integer.parseInt(startTime[3]) * 1000000);
        this.timeEnd = LocalTime.of(Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]), Integer.parseInt(endTime[2]), Integer.parseInt(endTime[3]) * 1000000);
        this.text = text;
    }

    public void changeTimes(int times) {
        if (times < 0) {
            timeStart = timeStart.plusNanos(times * 1000000L);
            timeEnd = timeEnd.plusNanos(times * 1000000L);
        } else {
            timeStart = timeStart.plusNanos(times * 1000000L);
            timeEnd = timeEnd.plusNanos(times * 1000000L);
        }
    }

    public String[] divideString(String strings) {
        return strings.trim().replaceFirst(",", ":").split(":");
    }

    public String formatTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");
        String changed = time.format(formatter);
        return changed.replace(">", ",");
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append(orderNumber)
                .append("\n")
                .append(formatTime(timeStart))
                .append(" --> ")
                .append(formatTime(timeEnd))
                .append("\n")
                .append(text);
        return st.toString();
    }

}
