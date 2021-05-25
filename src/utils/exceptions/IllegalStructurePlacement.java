package utils.exceptions;

public class IllegalStructurePlacement extends Exception{
    @Override
    public String getMessage() {
        ExceptionsDialogs.warningDialog("Warning", "Tried illegal placement of structure");
        return "Tried illegal placement structure in CellMap";
    }
}
