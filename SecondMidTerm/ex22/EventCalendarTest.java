package mk.kolokvium2.ex22;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class WrongDateException extends Exception {
    public Date date;

    public WrongDateException(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return "Wrong date: " + date;
    }
}

class EventCalendar {
    public int yearOnTheCalendar;
    public TreeSet<Event> setOfEvents;

    public EventCalendar(int year) {
        this.setOfEvents = new TreeSet<>(Comparator.comparing(Event::getTime).thenComparing(Event::getName));
        this.yearOnTheCalendar = year;
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int yearOfDate = calendar.get(Calendar.YEAR);
        if (yearOfDate != yearOnTheCalendar) {
            throw new WrongDateException(date);
        }
        setOfEvents.add(new Event(name, location, date));
    }

    public void listEvents(Date date) {
        AtomicBoolean flag = new AtomicBoolean(true);
        setOfEvents.stream()
                .filter(x -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(x.getTime());
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(date);
                    return (calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)) && (calendar.get(Calendar.MONTH) + 1 == calendar1.get(Calendar.MONTH) + 1) && (calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH));
                })
                .forEach(time -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(time.getTime());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy HH:mm");
                    String formatTime = dateFormat.format(calendar.getTime());
                    System.out.println(formatTime + " at " + time.location + ", " + time.name);
                    flag.set(false);
                });
        if (flag.get())
            System.out.println("No events on this day!");
    }

    public void listByMonth() {
        Map<Integer, Integer> eventsPerMonth = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toMap(i -> i, i -> 0, (a, b) -> b, TreeMap::new));
        setOfEvents.forEach(event -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(event.getTime());
            if (eventsPerMonth.containsKey(calendar.get(Calendar.MONTH) + 1)) {
                int freqency = eventsPerMonth.get(calendar.get(Calendar.MONTH) + 1);
                freqency++;
                eventsPerMonth.put(calendar.get(Calendar.MONTH) + 1, freqency);
            }
        });
//        AtomicInteger counter = new AtomicInteger(1);
        eventsPerMonth.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
    }
}

class Event {
    public String name;
    public String location;
    public Date time;

    public Event(String name, String location, Date time) {
        this.name = name;
        this.location = location;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getTime() {
        return time;
    }
}


public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde