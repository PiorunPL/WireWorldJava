package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano niewłaściwą liczbę argumentów
 */
public class IncorrectNumberOfArgumentsException extends Exception{
    @Override
    public String getMessage() {
        ExceptionsDialogs.warningDialog("Warning", "Typed incorrect number of arguments");
        return "Typed incorrect number of arguments";
    }
}
