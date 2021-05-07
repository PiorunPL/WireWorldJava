package utils.exceptions;

public class IllegalSizeException extends Exception {
    @Override
    public String getMessage() {
        return "Typed illegal size. Only positive or [-1 -1] values are allowed";
    }
}
