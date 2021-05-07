package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano niewłaściwą liczbę argumentów
 */
public class IncorretNumberOfArgumentsException extends Exception{
    @Override
    public String getMessage() {
        return "Typed incorret number of arguments";
    }
}
