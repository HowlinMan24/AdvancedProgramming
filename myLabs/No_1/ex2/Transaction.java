//package mk.myLabs.No_1.ex2;
//
//import java.util.Objects;
//
//public class Transaction {
//
//    public long fromId;
//    public long toId;
//
//    public String description;
//
//    public int amount;
//
//    public Transaction(long fromId, long toId, String description, int amount) {
//        this.fromId = fromId;
//        this.toId = toId;
//        this.description = description;
//        this.amount = amount;
//    }
//
//    public long getFromId() {
//        return fromId;
//    }
//
//    public long getToId() {
//        return toId;
//    }
//
//    public String getAmount() {
//        return String.valueOf(amount);
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Transaction that = (Transaction) o;
//        return fromId == that.fromId && toId == that.toId && Objects.equals(description, that.description) && Objects.equals(amount, that.amount);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(fromId, toId, description, amount);
//    }
//}
