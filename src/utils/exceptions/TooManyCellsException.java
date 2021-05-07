package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano więcej komórek niż zadeklarowano
 */
public class TooManyCellsException extends Exception {
    @Override
    public String getMessage() {
        return "Typed more cells than declared";
    }
}
