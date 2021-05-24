package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano niepoprawny format
 */
public class IllegalFormatOptionException extends Exception {
    @Override
    public String getMessage() {
        ExceptionsDialogs.warningDialog("Warning", "Typed illegal format option. Legal: map/struct");
        return "Typed illegal format option. Legal: map/struct";
    }
}
