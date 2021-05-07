package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano niepoprawny format
 */
public class IllegalFormatOptionException extends Exception {
    @Override
    public String getMessage() {
        return "Typed illegal format option. Legal: map/struct";
    }
}
