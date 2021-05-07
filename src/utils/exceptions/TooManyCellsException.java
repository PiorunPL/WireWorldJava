package utils.exceptions;

public class TooManyCellsException extends Exception {
    @Override
    public String getMessage() {
        return "Typed more cells than declared";
    }
}
