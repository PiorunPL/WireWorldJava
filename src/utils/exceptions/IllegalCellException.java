package utils.exceptions;

public class IllegalCellException extends Exception {
    @Override
    public String getMessage() {
        return "Typed illegal type of cell. Legal cells` numbers: 1, 2, 3, 4, 5";
    }
}
