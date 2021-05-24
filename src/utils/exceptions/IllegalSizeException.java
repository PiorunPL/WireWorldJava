package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano niepoprawny rozmiar mapy
 */
public class IllegalSizeException extends Exception {
    @Override
    public String getMessage() {
        ExceptionsDialogs.warningDialog("Warning", "Typed illegal size. Only positive or [-1 -1] values are allowed");
        return "Typed illegal size. Only positive or [-1 -1] values are allowed";
    }
}
