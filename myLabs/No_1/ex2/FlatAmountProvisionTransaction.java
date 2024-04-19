//package mk.myLabs.No_1.ex2;
//
//import java.util.Objects;
//
//public class FlatAmountProvisionTransaction extends Transaction {
//    public  final long fromId;
//    public final long toId;
//
//    public final String description;
//
//    public final int amount;
//
//    public FlatAmountProvisionTransaction(long fromId, long toId, String description, int amount) {
//        super(fromId,toId,description,amount);
//        this.fromId = fromId;
//        this.toId = toId;
//        this.description = "FlatAmount";
//        this.amount = amount;
//    }
//
//    String getFlatAmount() {
//        return String.valueOf(amount);
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
//        return String.valueOf(getFlatAmount());
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
//        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
//        return fromId == that.fromId && toId == that.toId && Objects.equals(description, that.description) && Objects.equals(amount, that.amount);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(fromId, toId, description, amount);
//    }
//}
