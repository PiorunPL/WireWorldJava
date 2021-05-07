package utils.exceptions;

public class TooLessCellsException extends Exception{
    @Override
    public String getMessage() {
        return "Typed less cells than declared";
    }
}
