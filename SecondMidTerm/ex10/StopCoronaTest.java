package mk.kolokvium2.ex10;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

interface ILocation {
    double getLongitude();

    double getLatitude();

    LocalDateTime getTimestamp();
}

class UserAlreadyExistException extends Throwable {
    public UserAlreadyExistException() {
    }
}

class StopCoronaApp {
    public Map<String, User> peopleWithCorona;

    public StopCoronaApp() {
        this.peopleWithCorona = new HashMap<>();
    }

    public void addUser(String name, String id) throws UserAlreadyExistException {
        if (peopleWithCorona.containsKey(name)) {
            throw new UserAlreadyExistException();
        } else {
            peopleWithCorona.put(name, new User(name, id));
        }
    }

    public void addLocations(String id, List<ILocation> locations) {
        peopleWithCorona.get(id).locationsPerson.addAll(locations);
    }

    public void detectNewCase(String id, LocalDateTime timestamp) {
        peopleWithCorona.get(id).setTimeAndDateCaughtCorona(timestamp);
    }

    public Map<User, Integer> getDirectContacts(User u) {
        Map<User, Integer> directContacts = new HashMap<>();
        peopleWithCorona.entrySet().forEach(x -> {

        });
        return directContacts;
    }

    public void createReport() {
        //TODO
    }
}

class User {
    public String name;
    public String id;
    public List<ILocation> locationsPerson;
    public LocalDateTime timeAndDateCaughtCorona;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
        this.locationsPerson = new ArrayList<>();
        this.timeAndDateCaughtCorona = null;
    }

    public void setTimeAndDateCaughtCorona(LocalDateTime timeAndDateCaughtCorona) {
        this.timeAndDateCaughtCorona = timeAndDateCaughtCorona;
    }
}

public class StopCoronaTest {

    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StopCoronaApp stopCoronaApp = new StopCoronaApp();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "REG": //register
                    String name = parts[1];
                    String id = parts[2];
                    try {
                        stopCoronaApp.addUser(name, id);
                    } catch (UserAlreadyExistException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "LOC": //add locations
                    id = parts[1];
                    List<ILocation> locations = new ArrayList<>();
                    for (int i = 2; i < parts.length; i += 3) {
                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
                    }
                    stopCoronaApp.addLocations(id, locations);

                    break;
                case "DET": //detect new cases
                    id = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    stopCoronaApp.detectNewCase(id, timestamp);

                    break;
                case "REP": //print report
                    stopCoronaApp.createReport();
                    break;
                default:
                    break;
            }
        }
    }

    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
        return new ILocation() {
            @Override
            public double getLongitude() {
                return Double.parseDouble(lon);
            }

            @Override
            public double getLatitude() {
                return Double.parseDouble(lat);
            }

            @Override
            public LocalDateTime getTimestamp() {
                return LocalDateTime.parse(timestamp);
            }
        };
    }
}
