//package mk.kolokvium.updatedEX.ex7;
//
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//class UnsupportedFormatException extends Exception {
//    public String message;
//
//    public UnsupportedFormatException(String message) {
//        this.message = message;
//    }
//
//    public String getMessage() {
//        return "UnsupportedFormatException: " + message;
//    }
//
//}
//
//class InvalidTimeException extends Exception {
//    public int hours;
//    public int minutes;
//
//    public InvalidTimeException(int hours, int minutes) {
//        this.hours = hours;
//        this.minutes = minutes;
//    }
//
//    public String getMessage() {
//        return "UnsupportedFormatException: ";
//    }
//}
//
//class TimeTable {
//    public List<Time> timeList;
//
//    public TimeTable() {
//        this.timeList = new ArrayList<>();
//    }
//
//    void readTimes(InputStream inputStream) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        br.lines().forEach(line -> {
//
//            String[] parts = line.replace(".", ":").split("\\s+");
//            System.out.println(Arrays.toString(parts));
//
//            for (String s : parts) {
//                if (s.charAt(2) != ':') {
//                    throw new UnsupportedFormatException(s);
//                }
//            }
//            for (String part : parts) {
//                timeList.add(new Time(part));
//            }
//
//
//        });
//        br.close();
//    }
//
//}
//
//class Time {
//    public LocalTime formatAM;
//    public LocalTime formatPM;
//    public int hours;
//    public int minutes;
//
//    public Time(String localTime) {
////        if (!localTime.contains(":")) {
////            throw new UnsupportedFormatException(localTime);
////        }
//        String[] parts = localTime.split(":");
//        this.hours = Integer.parseInt(parts[0]);
//        this.minutes = Integer.parseInt(parts[1]);
////        if ((hours > 23 || hours < 0) || (minutes > 59 || minutes < 0)) {
////            throw new InvalidTimeException(hours, minutes);
////        }
//    }
//
//    void writeTimes(OutputStream outputStream, TimeFormat format) {
//        PrintWriter pw = new PrintWriter(outputStream);
//    }
//
//}
//
//public class TimesTest {
//
//    public static void main(String[] args) {
//        TimeTable timeTable = new TimeTable();
//        try {
//            timeTable.readTimes(System.in);
//        } catch (UnsupportedFormatException e) {
//            System.out.println("UnsupportedFormatException: " + e.getMessage());
//        } catch (InvalidTimeException | IOException e) {
//            System.out.println("InvalidTimeException: " + e.getMessage());
//        }
////        System.out.println("24 HOUR FORMAT");
////        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
////        System.out.println("AM/PM FORMAT");
////        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
//    }
//
//}
//
//enum TimeFormat {
//    FORMAT_24, FORMAT_AMPM
//}