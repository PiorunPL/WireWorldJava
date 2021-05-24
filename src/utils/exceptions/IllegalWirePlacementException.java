package utils.exceptions;

public class IllegalWirePlacementException extends Exception{
    @Override
    public String getMessage() {
        return "Tried illegal placement of Electron (Electrons needs to be placed on the Wire)";
    }
}
