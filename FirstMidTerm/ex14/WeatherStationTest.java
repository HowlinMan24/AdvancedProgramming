package mk.kolokvium.updatedEX.ex14;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


class WeatherStation {
    public int days;

    public List<Weather> weatherList;

    public WeatherStation(int days) {
        this.days = days;
        this.weatherList = new ArrayList<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
//        days++;
        weatherList.add(new Weather(temperature, wind, humidity, visibility, date));
    }

    public int total() {
        return days;
    }

    public void status(Date from, Date to) {
        int counter = (int) weatherList.stream().filter(weather -> {
            if (weather.dateMeasured.after(from) && weather.dateMeasured.before(to)) {
                return true;
            }
            return false;
        }).count();
        if (counter == 0) {
            throw new RuntimeException("java.lang.RuntimeException");
        }
        if (counter != 0) {
            weatherList.stream().filter(weather -> weather.dateMeasured.after(from) && weather.dateMeasured.before(to)).forEach(System.out::println);
            System.out.println("Average temperature: " + weatherList.stream().mapToDouble(i -> i.temperature).average());
        }
        boolean flag = true;
        for (int i = 0; i < weatherList.size(); i++) {
            if (weatherList.get(i).dateMeasured.after(from) && weatherList.get(i).dateMeasured.before(to)) {

            }
        }
    }

}

class Weather {
    public float temperature;
    public float wind;
    public float humidity;
    public float visibility;
    public Date dateMeasured;

    public Weather(float temperature, float wind, float humidity, float visibility, Date dateMeasured) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.dateMeasured = dateMeasured;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km Tue Dec ", temperature, wind, humidity, visibility) + dateMeasured;
//        return temperature + " " + wind + " km/h " + humidity + "% "
    }
}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde
