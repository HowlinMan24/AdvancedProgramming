package mk.myLabs.No_2.ex2;

public class InsufficientElementsException extends Throwable {

    public String message;

    public InsufficientElementsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
