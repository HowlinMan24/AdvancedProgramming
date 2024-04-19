package mk.myLabs.No_2.ex2;

public class InvalidColumnNumberException extends Throwable {

    public String message;

    public InvalidColumnNumberException(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
