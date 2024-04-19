package mk.kolokvium2.ex21;

import java.util.*;


class DuplicateNumberException extends Exception {
    public String number;

    public DuplicateNumberException(String number) {
        this.number = number;
    }

    public String getMessage() {
        return number;
    }
}

class PhoneBook {

    public Map<String, String> contactsByNumber;
    public Map<String, String> contactsByName;

    public PhoneBook() {
        this.contactsByNumber = new TreeMap<>();
        this.contactsByName = new TreeMap<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if (contactsByNumber.containsValue(number))
            throw new DuplicateNumberException("Duplicate number: " + number);
        contactsByNumber.put(number, name);
        contactsByName.put(name, number);
    }

    public void contactsByNumber(String number) {
        contactsByName.entrySet().stream().filter(line -> line.getValue().contains(number)).forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));
//        for (Map.Entry<String, String> entry : contactsByNumber.entrySet()) {
//            if (entry.getValue().contains(number)) {
//                System.out.println(entry.getKey() + " " + entry.getValue());
//            }
//        }
    }

    public void contactsByName(String name) {
        contactsByName.entrySet().stream().filter(line -> line.getKey().equalsIgnoreCase(name)).sorted(Map.Entry.comparingByValue()).forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));
//        for (Map.Entry<String, String> entry : contactsByNumber.entrySet()) {
//            if (entry.getKey().equalsIgnoreCase(name)) {
//                System.out.println(entry.getKey() + " " + entry.getValue());
//            }
//        }
//        if (contactsByName.containsKey(name)) {
//            String number = contactsByName.get(name);
//            System.out.println(number + ": " + name);
//        } else {
//            System.out.println("Contact not found for name: " + name);
//        }
    }

}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде


