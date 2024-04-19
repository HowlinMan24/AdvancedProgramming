package mk.myLabs.No_2.ex1;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

enum Operator {
    VIP,
    ONE,
    TMOBILE
}

abstract class Contact {

    public String date;

    public boolean castingBoolean;
    // 0-Email 1-Phone

    public Contact(String date) {
        this.date = date;
        this.castingBoolean = false; // default
    }

    public boolean isNewerThan(Contact c) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(this.date);
            Date date2 = dateFormat.parse(c.date);
//            int comp = date1.compareTo(date2);
            return date1.compareTo(date2) < 0;
        } catch (ParseException e) {
            return false;
        }
    }

    abstract String getType();

    abstract boolean getCast();
}

class EmailContact extends Contact {

    public String date;
    public String eMail;
    public boolean castingBoolean;

    public EmailContact(String date, String eMail) {
        super(date);
        this.date = date;
        this.eMail = eMail;
        this.castingBoolean = false;
    }

    public boolean isNewerThan(Contact c) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(this.date);
            Date date2 = dateFormat.parse(c.date);
//            int comp = date1.compareTo(date2);
            return date1.compareTo(date2) < 0;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean getCast() {
        return castingBoolean;
    }

    public String getEmail() {
        return eMail;
    }

    @Override
    public String toString() {
        return ", \"emailKontakti\"[" +
//                "date='" + date + '\'' +
                "" + eMail + '\"' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailContact that = (EmailContact) o;
        return Objects.equals(date, that.date) && Objects.equals(eMail, that.eMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, eMail);
    }

    @Override
    String getType() {
        return "Email";
    }
}

class PhoneContact extends Contact {

    public String date;
    public String phone;
    public boolean castingBoolean;


    public PhoneContact(String date, String phone) {
        super(date);
        this.date = date;
        this.phone = phone;
        this.castingBoolean = true;
    }

    public boolean isNewerThan(Contact c) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = dateFormat.parse(this.date);
            Date date2 = dateFormat.parse(c.date);
//            int comp = date1.compareTo(date2);
            return date1.compareTo(date2) < 0;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean getCast() {
        return castingBoolean;
    }

    public String getPhone() {
        return phone;
    }

    public Operator getOperator() {
        if (phone.charAt(2) == '0' || phone.charAt(2) == '1' || phone.charAt(2) == '2')
            return Operator.TMOBILE;
        else if (phone.charAt(2) == '5' || phone.charAt(2) == '6')
            return Operator.ONE;
        else
            return Operator.VIP;
    }

    public String getType() {
        return "Phone";
    }

    @Override
    public String toString() {
        return ", \"telefonskiKontakti\"[" +
//                "date='" + date + '\'' +
                phone + '\"' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneContact that = (PhoneContact) o;
        return Objects.equals(date, that.date) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, phone);
    }
}

class Student {

    public String firstName;
    public String lastName;
    public String city;
    public int age;
    public long index;

    public Contact[] listOfContacts;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        this.listOfContacts = new Contact[0];
    }

    public void addEmailContact(String date, String email) {
        EmailContact temp = new EmailContact(date, email);
        Contact[] Array = new Contact[listOfContacts.length + 1];
        for (int i = 0; i < listOfContacts.length; i++)
            Array[i] = listOfContacts[i];
        Array[listOfContacts.length] = temp;
        listOfContacts = Array;
    }

    public void addPhoneContact(String date, String phone) {
        PhoneContact temp = new PhoneContact(date, phone);
        Contact[] Array = new Contact[listOfContacts.length + 1];
        for (int i = 0; i < listOfContacts.length; i++)
            Array[i] = listOfContacts[i];
        Array[listOfContacts.length] = temp;
        listOfContacts = Array;
    }

    public Contact[] getEmailContacts() {
        int j = 0;
        for (Contact listOfContact : listOfContacts) {
            if (listOfContact instanceof EmailContact)
                j++;
        }
        Contact[] Array = new Contact[j];
        j = 0;
        for (int i = 0; i < listOfContacts.length; i++) {
            if (listOfContacts[i] instanceof EmailContact)
                Array[j++] = listOfContacts[i];
        }
        return Array;
    }

    public Contact[] getPhoneContacts() {
        int j = 0;
        for (Contact listOfContact : listOfContacts) {
            if (listOfContact instanceof PhoneContact)
                j++;
        }
        Contact[] Array = new Contact[j];
        j = 0;
        for (int i = 0; i < listOfContacts.length; i++) {
            if (listOfContacts[i] instanceof PhoneContact)
                Array[j++] = listOfContacts[i];
        }
        return Array;
    }

    public int getLengthListOfContacts() {
        return listOfContacts.length;
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public long getIndex() {
        return index;
    }

    public Contact getLatestContact() {
        Contact maxContact = listOfContacts[0];
        for (int i = 1; i < listOfContacts.length; i++) {
            if (listOfContacts[i].isNewerThan(maxContact))
                maxContact = listOfContacts[i];
        }
        if (maxContact instanceof EmailContact)
            return (EmailContact) maxContact;
        else
            return (PhoneContact) maxContact;
    }

    @Override
    public String toString() {
        return "{" +
                "\"ime\":\"" + firstName + '\"' +
                ", \"prezime\":\"" + lastName + '\"' +
                ", \"vozrast\":" + age +
                ", \"grad\":\"" + city +
                "\", \"indeks\":" + index +
                Arrays.toString(listOfContacts) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && index == student.index && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(city, student.city) && Arrays.equals(listOfContacts, student.listOfContacts);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(firstName, lastName, city, age, index);
        result = 31 * result + Arrays.hashCode(listOfContacts);
        return result;
    }
}

class Faculty {

    public String name;

    public Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = new Student[students.length];
        for (int i = 0; i < students.length; i++) {
            this.students[i] = students[i];
        }
    }

    public int countStudentsFromCity(String cityName) {
        int counter = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i].city.compareTo(cityName) == 0) {
                counter++;
            }
        }
        return counter;
    }

    public Student getStudent(long index) {
        for (Student s : students) {
            if (s.index == index)
                return s;
        }
        return null;
    }

    public double getAverageNumberOfContacts() {
        int total = 0;
        for (int i = 0; i < students.length; i++) {
            total += getNumContacts(i);
        }
        return (double) total / students.length;
    }

    public int getNumContacts(int x) {
        return students[x].listOfContacts.length;
    }

    public Student getStudentWithMostContacts() {
        Student maxContacts = students[0];
        for (int i = 1; i < students.length; i++) {
            if (maxContacts.listOfContacts.length < students[i].listOfContacts.length) {
                maxContacts = students[i];
            } else if (maxContacts.listOfContacts.length == students[i].listOfContacts.length) {
                if (maxContacts.index < students[i].index) {
                    maxContacts = students[i];
                }
            }
        }
        return maxContacts;
    }

    @Override
    public String toString() {
        return "{" +
                "\"fakultet\":\"" + name + '\"' +
                ", \"studenti\":" + Arrays.toString(students) +
                '}';
    }
}

public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex).getEmailContacts()[posEmail].isNewerThan(faculty.getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}

