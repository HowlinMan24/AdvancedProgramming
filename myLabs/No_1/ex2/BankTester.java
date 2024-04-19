package mk.myLabs.No_1.ex2;

import java.util.*;
import java.util.stream.Collectors;

class Account {

    String name;
    long identificationNumber;
    String balance;

    public Account(String name, String balance) {
        this.name = name;
        this.balance = balance.replace("$","");
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
        this.balance = balance.replace("$","");
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Balance: " + balance + "$\n";
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

class Transaction {

    public long fromId;
    public long toId;

    public String description;

    public String amount;

    public Transaction(long fromId, long toId, String description, String amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.description = description;
        this.amount = amount.replace("$","");
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    public String getAmount() {
        return String.valueOf(amount);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return fromId == that.fromId && toId == that.toId && amount == that.amount && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, description, amount);
    }
}

class FlatAmountProvisionTransaction extends Transaction {
    public final long fromId;
    public final long toId;

    public final String flatProvision;

    public final String amount;

    public FlatAmountProvisionTransaction(long fromId, long toId, String amount , String flatProvision) {
        super(fromId, toId, "FlatAmount", amount);
        this.fromId = fromId;
        this.toId = toId;
        this.flatProvision = flatProvision.replace("$","");
        this.amount = amount.replace("$","");
    }

    public String getAmount() {
        return String.valueOf(Float.parseFloat(amount)-Float.parseFloat(flatProvision));
    }
    public String getFlatAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return fromId == that.fromId && toId == that.toId && Objects.equals(description, that.description) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, description, amount);
    }
}

class FlatPercentProvisionTransaction extends Transaction {

    public final long fromId;
    public final long toId;

    public final int centsPerDollar;

    public final String amount;

    public FlatPercentProvisionTransaction(long fromId, long toId, String amount, int centsPerDollar) {
        super(fromId, toId, "FlatPercent", String.valueOf(amount));
        this.fromId = fromId;
        this.toId = toId;
        this.centsPerDollar = centsPerDollar;
        this.amount = amount.replace("$","");
    }

    int getPercent() {
        return centsPerDollar;
    }

    public String getAmount() {
        return String.valueOf(Double.parseDouble(amount)*centsPerDollar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return fromId == that.fromId && toId == that.toId && Objects.equals(description, that.description) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fromId, toId, description, amount);
    }
}

class Bank {

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
        boolean flag1 = false,flag2 =false;
        int indexLocationFrom=-1,indexLocationTo=-1;
        for (int i = 0; i < accountsOfPeople.length; i++) {
            if (accountsOfPeople[i].getId() == t.fromId) {
                flag1 = true;
                indexLocationFrom=i;
            }else if(accountsOfPeople[i].getId() == t.toId) {
                flag2 = true;
                indexLocationTo=i;
            }
        }
        if(flag1 && flag2) {
            if(Float.parseFloat(accountsOfPeople[indexLocationFrom].balance) - Float.parseFloat(t.amount) < 0) {
                return false;
            }else {
                float changedBalance=Float.parseFloat(accountsOfPeople[indexLocationFrom].balance) - Float.parseFloat(t.amount);
                accountsOfPeople[indexLocationFrom].setBalance(String.valueOf(changedBalance));
                return true;
            }
        }
        return false;
    }

    public String totalProvision() {
        return String.valueOf(totalPaidProvision);
    }

    public String totalTransfers() {
        int sum = 0;
        for (Account i : accountsOfPeople) {
            sum += Integer.parseInt(i.balance);
        }
        return String.valueOf(sum);
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

    public Account[] getAccounts() {
        return accountsOfPeople;
    }
}


public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "typical_usage":
                testTypicalUsage(jin);
                break;
            case "equals":
                testEquals();
                break;
        }
        jin.close();
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", "20.00$");
        Account a2 = new Account("Andrej", "20.00$");
        Account a3 = new Account("Andrej", "30.00$");
        Account a4 = new Account("Gajduk", "20.00$");
        List<Account> all = Arrays.asList(a1, a2, a3, a4);
        if (!(a1.equals(a1) && !a1.equals(a2) && !a2.equals(a1) && !a3.equals(a1)
                && !a4.equals(a1)
                && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        Set<Long> ids = all.stream().map(Account::getId).collect(Collectors.toSet());
        if (ids.size() != all.size()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, "50.00$", "50.00$");
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, "20.00$", "10.00$");
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, "20.00$", 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, "50.00$", 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, "20.00$", 10);
        if (fa1.equals(fa1) &&
                !fa2.equals(null) &&
                fa2.equals(fa1) &&
                fa1.equals(fa2) &&
                fa1.equals(fa3) &&
                !fa1.equals(fa4) &&
                !fa1.equals(fa5) &&
                !fa1.equals(fp1) &&
                fp1.equals(fp1) &&
                !fp2.equals(null) &&
                fp2.equals(fp1) &&
                fp1.equals(fp2) &&
                fp1.equals(fp3) &&
                !fp1.equals(fp4) &&
                !fp1.equals(fp5) &&
                !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account accounts[] = new Account[]{a1, a2, a3, a4};
        Account accounts1[] = new Account[]{a2, a1, a3, a4};
        Account accounts2[] = new Account[]{a1, a2, a3};
        Account accounts3[] = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);

        if (!(b1.equals(b1) &&
                !b1.equals(null) &&
                !b1.equals(b2) &&
                !b2.equals(b1) &&
                !b1.equals(b3) &&
                !b3.equals(b1) &&
                !b1.equals(b4) &&
                b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        //accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        long from_id = a2.getId();
        long to_id = a3.getId();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, "3.00$", "3.00$");
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account accounts[] = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i)
            accounts[i] = new Account(jin.nextLine(), jin.nextLine());
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    String amount = jin.nextLine();
                    String parameter = jin.nextLine();
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount: " + t.getAmount());
                    System.out.println("Transaction description: " + t.getDescription());
                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank.toString());
                    System.out.println("Total provisions: " + bank.totalProvision());
                    System.out.println("Total transfers: " + bank.totalTransfers());
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, String amount, String o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, Integer.parseInt(o));
        }
        return null;
    }


}
