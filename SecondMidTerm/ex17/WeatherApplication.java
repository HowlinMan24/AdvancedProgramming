package mk.kolokvium2.ex17;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class WeatherDispatcher {
    public float temperature;
    public float humidity;
    public float pressure;
    public List<Dispatcher> dispatcherList;
    public Dispatcher latestRemoved;

    public WeatherDispatcher() {
        this.humidity = 0f;
        this.pressure = 0f;
        this.temperature = 0f;
        this.dispatcherList = new ArrayList<>();
        this.latestRemoved = null;
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void register(Dispatcher dispatcher) {
        this.dispatcherList.remove(dispatcher);
    }

    public void remove(Dispatcher dispatcher) {
        if (dispatcher.getType().equals("C")) {
            CurrentConditionsDisplay display = (CurrentConditionsDisplay) dispatcher;
            dispatcherList.remove(display);
            System.out.println(display);
            latestRemoved = dispatcher;
        } else {
            ForecastDisplay display = (ForecastDisplay) dispatcher;
            if (latestRemoved == null) {
                System.out.print(display);
                if (0.0 > display.dispatcher.pressure) {
                    System.out.println("Forecast: Improving");
                } else if (0.0 == display.dispatcher.pressure) {
                    System.out.println("Forecast: Same");
                }
            } else {
                System.out.print(display);
                if (latestRemoved.dispatcher.pressure > display.dispatcher.pressure) {
                    System.out.println("Forecast: Improving");
                } else if (latestRemoved.dispatcher.pressure == display.dispatcher.pressure) {
                    System.out.println("Forecast: Same");
                } else {
                    System.out.println("Forecast: Cooler");
                }
            }
        }
    }
}

abstract class Dispatcher {
    public WeatherDispatcher dispatcher;

    public Dispatcher(WeatherDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    abstract public String getType();
}

class CurrentConditionsDisplay extends Dispatcher {
    public CurrentConditionsDisplay(WeatherDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public String getType() {
        return "C";
    }

    @Override
    public String toString() {
        return "Temperature: " + dispatcher.temperature + "F" + "\n" +
                "Humidity: " + dispatcher.humidity + "%" + "\n";
    }
}

class ForecastDisplay extends Dispatcher {

    public ForecastDisplay(WeatherDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public String getType() {
        return "F";
    }

    @Override
    public String toString() {
        return "Temperature: " + dispatcher.temperature + "F" + "\n" +
                "Humidity: " + dispatcher.humidity + "%" + "\n";
    }
}

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if (parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if (operation == 1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if (operation == 2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if (operation == 3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if (operation == 4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}