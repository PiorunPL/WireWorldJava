package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano mniej komórek niż zadeklarowano
 */
public class TooLessCellsException extends Exception{
    @Override
    public String getMessage() {
        return "Typed less cells than declared";
    }
}
