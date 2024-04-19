package mk.myLabs.No_1.ex2;

import java.util.Objects;
import java.util.Random;

public class Account {

    public String name;
    public long identificationNumber;
    public String balance;

    public Account(String name, String balance) {
        this.name = name;
        balance.replace("$","");
        this.balance = balance;
        //TODO generate a random ID
        Random rand = new Random();
        this.identificationNumber = rand.nextLong();
    }

    public String getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return identificationNumber;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Balance: " + balance + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return identificationNumber == account.identificationNumber && Objects.equals(name, account.name) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, identificationNumber, balance);
    }
}
