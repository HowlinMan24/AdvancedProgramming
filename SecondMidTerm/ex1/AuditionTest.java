package mk.kolokvium2.ex1;

import java.util.*;


class Participant {
    public String code;
    public String name;
    public int age;

    public Participant(String code, String name, int age) {
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}

class Audition {
    // key = string (City)
    public Map<String, TreeSet<Participant>> participantsByCity = new HashMap<>();
    public Map<String, HashSet<String>> codesByCity = new HashMap<>();
    public void addParticipant(String city, String code, String name, int age) {
        /*
        The code bellow makes a key/value if there
        isn't a city inside the map to then add the element
         */
        participantsByCity.putIfAbsent(city,
                new TreeSet<>(Comparator.comparing(Participant::getName)
                        .thenComparing(Participant::getAge)
                        .thenComparing(Participant::getCode)));

        codesByCity.putIfAbsent(city, new HashSet<>());
        /*
        The code bellow makes a new participant
         */
        Participant participant = new Participant(code, name, age);
        /*
        The code bellow gets the city in
        the map and then adds the participant in that city
         */
        if (!codesByCity.get(city).contains(participant.code))
            participantsByCity.get(city).add(participant);

        codesByCity.get(city).add(participant.code);
    }

    public void listByCity(String city) {
        participantsByCity.get(city).forEach(System.out::println);
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticipant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
