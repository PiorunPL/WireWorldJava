package utils.exceptions;

public class IllegalStructurePlacement extends Exception{
    @Override
    public String getMessage() {
        return "Tried illegal placement structure in CellMap";
    }
}
