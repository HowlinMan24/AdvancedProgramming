package mk.myLabs.No_1.ex2;

import java.util.Arrays;
import java.util.Objects;

public class Bank {

    public Account[] accountsOfPeople;

    public String bankName;

    public int totalSumOfTransfers;

    public int totalPaidProvision;

    public Bank(String name, Account[] accounts) {
        this.bankName=name;
        this.accountsOfPeople = new Account[accounts.length];
        for (int i = 0; i < accounts.length; i++)
            this.accountsOfPeople[i] = accounts[i];
        this.totalPaidProvision = 0;
        this.totalSumOfTransfers = 0;
    }

    public boolean makeTransaction(Transaction t) {


        if(t instanceof FlatAmountProvisionTransaction) {
            totalSumOfTransfers+=t.amount;
            return true;
        }
        else if(t instanceof FlatPercentProvisionTransaction) {
            totalSumOfTransfers+=t.amount;
            totalPaidProvision+=((FlatPercentProvisionTransaction)t).getPercent();
            return true;
        }

    }

    public String totalTransfers() {
        return String.valueOf(totalSumOfTransfers);
    }

    public String totalProvision() {
        return String.valueOf(totalPaidProvision);
    }

    public Account[] getAccounts() {
        return accountsOfPeople;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return totalSumOfTransfers == bank.totalSumOfTransfers && totalPaidProvision == bank.totalPaidProvision && Arrays.equals(accountsOfPeople, bank.accountsOfPeople) && Objects.equals(bankName, bank.bankName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(bankName, totalSumOfTransfers, totalPaidProvision);
        result = 31 * result + Arrays.hashCode(accountsOfPeople);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder printString=new StringBuilder();

        printString.append("Name: ").append(bankName).append('\n').append('\n');

        for(Account strI:accountsOfPeople)
            printString.append(strI.toString());

        return printString.toString();
    }
}
