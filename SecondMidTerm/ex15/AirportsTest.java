package mk.kolokvium2.ex15;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


class Airports {

    public Map<Airport, TreeSet<FLight>> airportsAndTheirFLightDDestination;

    public Airports() {
        this.airportsAndTheirFLightDDestination = new HashMap<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        airportsAndTheirFLightDDestination.computeIfAbsent(new Airport(name, country, code, passengers), x -> new TreeSet<>(Comparator.comparing(FLight::getDurationOfFlight).thenComparing(FLight::getAirportDepartureCode)));
    }

    public void addFlights(String from, String to, int time, int duration) {
        airportsAndTheirFLightDDestination
                .entrySet()
                .stream()
                .filter(x -> x.getKey().code.equalsIgnoreCase(from))
                .forEach(x -> x.getValue().add(new FLight(from, to, time, duration)));
    }

    public void showFlightsFromAirport(String code) {
        //From the named airport
        AtomicInteger counter = new AtomicInteger(1);
        airportsAndTheirFLightDDestination
                .entrySet()
                .stream()
                .filter(x -> x.getKey().code.equalsIgnoreCase(code))
                .forEach(airport -> {
                    System.out.printf("%s (%s)%n", airport.getKey().airportName, airport.getKey().code);
                    System.out.println(airport.getKey().country);
                    System.out.println(airport.getKey().passengersPerYear);
                    Set<FLight> sortedFlights = new TreeSet<>(Comparator.comparing(FLight::getAirportArrival).thenComparing(FLight::getDurationOfFlight));
                    sortedFlights.addAll(airport.getValue());
                    sortedFlights.forEach(fLight -> {
                        System.out.println(counter + " " + fLight);
                        counter.incrementAndGet();
                    });
//                    airport.getValue().forEach(flight -> {
//                        System.out.println(counter + " " + flight);
//                        counter.incrementAndGet();
//                    });
                });
    }

    public void showDirectFlightsFromTo(String from, String to) {
        if (airportsAndTheirFLightDDestination.values().stream().flatMap(Collection::stream).noneMatch(x -> (x.airportDepartureCode.equalsIgnoreCase(from) && x.airportArrivalCOde.equalsIgnoreCase(to)))) {
            System.out.printf("No flights from %s  %s\n", from, to);
        } else {
            airportsAndTheirFLightDDestination.values().stream()
                    .flatMap(Collection::stream)
                    .filter(x -> (x.airportDepartureCode.equalsIgnoreCase(from) && x.airportArrivalCOde.equalsIgnoreCase(to)))
                    .forEach(System.out::println);
        }
    }

    public void showDirectFlightsTo(String to) {
        airportsAndTheirFLightDDestination.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(x -> x.getAirportArrival().equalsIgnoreCase(to))
                .forEach(System.out::println);
    }
}

class Airport {
    public String airportName;
    public String country;
    public String code;
    public int passengersPerYear;

    public Airport(String airportName, String country, String code, int passengersPerYear) {
        this.airportName = airportName;
        this.country = country;
        this.code = code;
        this.passengersPerYear = passengersPerYear;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public int getPassengersPerYear() {
        return passengersPerYear;
    }
}

class FLight {
    public String airportDepartureCode;
    public String airportArrivalCOde;
    public int timeOfDeparture;
    public int durationOfFlight;

    public FLight(String airportDeparture, String airportArrival, int timeOfDeparture, int durationOfFlight) {
        this.airportDepartureCode = airportDeparture;
        this.airportArrivalCOde = airportArrival;
        this.timeOfDeparture = timeOfDeparture;
        this.durationOfFlight = durationOfFlight;
    }

    public String getAirportDepartureCode() {
        return airportDepartureCode;
    }

    public String getAirportArrival() {
        return airportArrivalCOde;
    }

    public int getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public int getDurationOfFlight() {
        return durationOfFlight;
    }

    @Override
    public String toString() {
        return String.format(" %s-%s %s %dh%dm", airportDepartureCode, airportArrivalCOde, formatTime(), durationOfFlight / 60, durationOfFlight % 60);
    }

    public String formatTime() {
        int totalDuration = (timeOfDeparture + durationOfFlight + (timeOfDeparture + durationOfFlight) % 60) / 60;
        if (totalDuration > 24) {
//            return "+1d";
            return String.format("%02d:%02d-%02d:%02d +1d", timeOfDeparture / 60, timeOfDeparture % 60, Math.abs(24 - (timeOfDeparture + durationOfFlight) / 60), 60 - (timeOfDeparture + durationOfFlight) % 60);
        } else {
            return String.format("%02d:%02d-%02d:%02d", timeOfDeparture / 60, timeOfDeparture % 60, (timeOfDeparture + durationOfFlight) / 60, (timeOfDeparture + durationOfFlight) % 60);
        }
//        return "Kiro e car <3";
    }
}

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde

