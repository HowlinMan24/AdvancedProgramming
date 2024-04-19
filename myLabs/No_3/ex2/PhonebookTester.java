//package mk.myLabs.No_3.ex2;
//
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.Random;
//import java.util.Scanner;
//
//class InvalidNameException extends Exception {
//
//}
//
//class InvalidNumberException extends Exception {
//
//}
//
//class MaximumSizeExceddedException extends Exception {
//
//}
//
//class Contact {
//
//    String name;
//
//    String[] phoneNumber;
//
//    public Contact(String name, String[] phonenumber) throws InvalidNameException, InvalidNumberException, MaximumSizeExceddedException {
//        if (name.length() < 4 || name.length() > 10)
//            throw new InvalidNameException();
//        this.name = name;
//        for (String s : phonenumber) {
//            if (!s.matches("\\d+"))
//                throw new InvalidNumberException();
//        }
//        if (phonenumber.length > 5)
//            throw new MaximumSizeExceddedException();
//        this.phoneNumber = phonenumber;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String[] getNumbers() {
//        String[] orderedNumbers = new String[phoneNumber.length];
//        orderedNumbers = phoneNumber;
//        for (int i = 0; i < orderedNumbers.length; i++) {
//            if (orderedNumbers[i].compareTo(orderedNumbers[i + 1]) > 0) {
//                String temp = orderedNumbers[i];
//                orderedNumbers[i] = orderedNumbers[i + 1];
//                orderedNumbers[i + 1] = temp;
//            }
//        }
//        return orderedNumbers;
//    }
//
//    public void addNumber(String phonenumber) throws InvalidNumberException, MaximumSizeExceddedException {
//        if (!phonenumber.matches("\\d+"))
//            throw new InvalidNumberException();
//        if (phonenumber.length() > 5)
//            throw new MaximumSizeExceddedException();
//        String[] update = new String[phoneNumber.length + 1];
//        for (int i = 0; i < phoneNumber.length; i++) {
//            update[i] = phoneNumber[i];
//        }
//        update[phoneNumber.length] = phonenumber;
//        phoneNumber = update;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder st = new StringBuilder();
//        String[] sorted = getNumbers();
//        st.append(String.format("%s\n%d\n", name, phoneNumber.length));
//        for (int i = 0; i < phoneNumber.length; i++) {
//            st.append(phoneNumber[i]).append("\n");
//        }
//        return st.toString();
//    }
//
//}
//
//class PhoneBook {
//
//    public Contact[] listOfContacts;
//    // TODO remember it can be a max of 250
//
//    public int numberOfContacts;
//
//    public PhoneBook() {
//        this.listOfContacts = new Contact[0];
//        this.numberOfContacts = 0;
//    }
//
//    public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {
//        if (numberOfContacts + 1 > 250)
//            throw new MaximumSizeExceddedException();
//        // TODO the code below might not work because strings need to be compared
//        if (Arrays.asList(listOfContacts).contains(contact))
//            throw new InvalidNameException();
//        Contact[] updatedList = new Contact[numberOfContacts + 1];
//        for (int i = 0; i < numberOfContacts; i++) {
//            updatedList[i] = listOfContacts[i];
//        }
//        updatedList[numberOfContacts] = contact;
//        numberOfContacts++;
//        listOfContacts = updatedList;
//    }
//
//    public Contact getContactForName(String name) {
//        for (Contact c : listOfContacts) {
//            if (c.name.equals(name))
//                return c;
//        }
//        return null;
//    }
//
//    public int getNumberOfContacts() {
//        return numberOfContacts;
//    }
//
//    public Contact[] getContacts() {
//        Contact[] orderedNumbers;
//        orderedNumbers = listOfContacts;
//        Arrays.sort(orderedNumbers, new Comparator<>() {
//            @Override
//            public int compare(Contact contact1, Contact contact2) {
//                return contact1.getName().compareTo(contact2.getName());
//            }
//        });
//        return orderedNumbers;
//    }
//
//    public boolean removeContact(String name) {
//        int counter = 0;
//        boolean flag = false;
//        Contact[] updated = new Contact[listOfContacts.length - 1];
//        for (int i = 0; i < listOfContacts.length; i++) {
//            if (listOfContacts[i].name.equals(name)) {
//                flag = true;
//                continue;
//            }
//            updated[counter++] = listOfContacts[i];
//        }
//        listOfContacts = updated;
//        return flag;
//    }
//
//    @Override
//    public String toString() {
//        return "PhoneBook{" +
//                "listOfContacts=" + Arrays.toString(listOfContacts)
//                + "\n";
//    }
//}
//
//public class PhonebookTester {
//
//    public static void main(String[] args) throws Exception {
//        Scanner jin = new Scanner(System.in);
//        String line = jin.nextLine();
//        switch (line) {
//            case "test_contact":
//                testContact(jin);
//                break;
//            case "test_phonebook_exceptions":
//                testPhonebookExceptions(jin);
//                break;
//            case "test_usage":
//                testUsage(jin);
//                break;
//        }
//    }
//
////    private static void testFile(Scanner jin) throws Exception {
////        PhoneBook phonebook = new PhoneBook();
////        while (jin.hasNextLine())
////            phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
////        String text_file = "phonebook.txt";
//////        PhoneBook.saveAsTextFile(phonebook, text_file);
//////        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
////        if (!pb.equals(phonebook)) System.out.println("Your file saving and loading doesn't seem to work right");
////        else System.out.println("Your file saving and loading works great. Good job!");
////    }
//
//    private static void testUsage(Scanner jin) throws Exception {
//        PhoneBook phonebook = new PhoneBook();
//        while (jin.hasNextLine()) {
//            String command = jin.nextLine();
//            switch (command) {
//                case "add":
//                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
//                    break;
//                case "remove":
//                    phonebook.removeContact(jin.nextLine());
//                    break;
//                case "print":
//                    System.out.println(phonebook.numberOfContacts());
//                    System.out.println(Arrays.toString(phonebook.getContacts()));
//                    System.out.println(phonebook.toString());
//                    break;
//                case "get_name":
//                    System.out.println(phonebook.getContactForName(jin.nextLine()));
//                    break;
//                case "get_number":
//                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
//                    break;
//            }
//        }
//    }
//
//    private static void testPhonebookExceptions(Scanner jin) {
//        PhoneBook phonebook = new PhoneBook();
//        boolean exception_thrown = false;
//        try {
//            while (jin.hasNextLine()) {
//                phonebook.addContact(new Contact(jin.nextLine()));
//            }
//        } catch (InvalidNameException e) {
//            System.out.println(e.name);
//            exception_thrown = true;
//        } catch (Exception e) {
//        }
//        if (!exception_thrown) System.out.println("Your addContact method doesn't throw InvalidNameException");
//        /*
//		exception_thrown = false;
//		try {
//		phonebook.addContact(new Contact(jin.nextLine()));
//		} catch ( MaximumSizeExceddedException e ) {
//			exception_thrown = true;
//		}
//		catch ( Exception e ) {}
//		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
//        */
//    }
//
//    private static void testContact(Scanner jin) throws Exception {
//        boolean exception_thrown = true;
//        String names_to_test[] = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
//        for (String name : names_to_test) {
//            try {
//                new Contact(name);
//                exception_thrown = false;
//            } catch (InvalidNameException e) {
//                exception_thrown = true;
//            }
//            if (!exception_thrown) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
//        }
//        String numbers_to_test[] = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
//        for (String number : numbers_to_test) {
//            try {
//                new Contact("Andrej", number);
//                exception_thrown = false;
//            } catch (InvalidNumberException e) {
//                exception_thrown = true;
//            }
//            if (!exception_thrown)
//                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
//        }
//        String nums[] = new String[10];
//        for (int i = 0; i < nums.length; ++i) nums[i] = getRandomLegitNumber();
//        try {
//            new Contact("Andrej", nums);
//            exception_thrown = false;
//        } catch (MaximumSizeExceddedException e) {
//            exception_thrown = true;
//        }
//        if (!exception_thrown)
//            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
//        Random rnd = new Random(5);
//        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
//        System.out.println(contact.getName());
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//        contact.addNumber(getRandomLegitNumber(rnd));
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//        contact.addNumber(getRandomLegitNumber(rnd));
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//    }
//
//    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
//    static Random rnd = new Random();
//
//    private static String getRandomLegitNumber() {
//        return getRandomLegitNumber(rnd);
//    }
//
//    private static String getRandomLegitNumber(Random rnd) {
//        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
//        for (int i = 3; i < 9; ++i)
//            sb.append(rnd.nextInt(10));
//        return sb.toString();
//    }
//
//
//}
//
