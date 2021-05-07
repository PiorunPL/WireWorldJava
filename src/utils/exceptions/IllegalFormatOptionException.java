package utils.exceptions;

public class IllegalFormatOptionException extends Exception {
    @Override
    public String getMessage() {
        return "Typed illegal format option. Legal: map/struct";
    }
}
