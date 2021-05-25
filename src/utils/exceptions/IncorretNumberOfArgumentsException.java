package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano niewłaściwą liczbę argumentów
 */
public class IncorretNumberOfArgumentsException extends Exception{
    @Override
    public String getMessage() {
        ExceptionsDialogs.warningDialog("Warning", "Typed incorret number of arguments");
        return "Typed incorret number of arguments";
    }
}
