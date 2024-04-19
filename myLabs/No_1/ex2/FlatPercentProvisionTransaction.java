//package mk.myLabs.No_1.ex2;
//
//import java.util.Objects;
//
//public class FlatPercentProvisionTransaction extends Transaction{
//
//    public  final long fromId;
//    public final long toId;
//
//    public final String description;
//
//    public final int amount;
//
//    public FlatPercentProvisionTransaction(long fromId, long toId, String description, int amount) {
//        super(fromId,toId,description,amount);
//        this.fromId = fromId;
//        this.toId = toId;
//        this.description = "FlatPercent";
//        this.amount = amount;
//    }
//
//    int getPercent() {
//        return (5/100);
//    }
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
//        if (!super.equals(o)) return false;
//        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
//        return fromId == that.fromId && toId == that.toId && Objects.equals(description, that.description) && Objects.equals(amount, that.amount);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), fromId, toId, description, amount);
//    }
//}
