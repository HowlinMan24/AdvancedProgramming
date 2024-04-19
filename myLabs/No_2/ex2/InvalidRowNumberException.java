package mk.myLabs.No_2.ex2;

public class InvalidRowNumberException extends Throwable{

    public String message;

    public InvalidRowNumberException(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
