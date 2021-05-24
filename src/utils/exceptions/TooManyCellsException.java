package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy podano więcej komórek niż zadeklarowano
 */
public class TooManyCellsException extends Exception {
    @Override
    public String getMessage() {
        ExceptionsDialogs.warningDialog("Warning", "Typed more cells than declared");
        return "Typed more cells than declared";
    }
}
