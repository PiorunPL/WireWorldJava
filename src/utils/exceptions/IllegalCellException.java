package utils.exceptions;

/**
 * @author Michał Ziober
 * Wyjątek wyrzucany gdy w pliku wejściowym podano niepoprawny typ komórki
 */
public class IllegalCellException extends Exception {
    @Override
    public String getMessage() {
        ExceptionsDialogs.warningDialog("Warning", "Typed illegal type of cell. Legal cells' numbers: 1, 2, 3, 4, 5");
        return "Typed illegal type of cell. Legal cells' numbers: 1, 2, 3, 4, 5";
    }
}
