package logic.cells;

import utils.exceptions.IllegalCellException;

public class CellState {
    public CellState(String name) {
        this.name = name;
    }

    public static CellState setCellState(int i) throws IllegalCellException {
        if (i == 1) return WIRE;
        else if (i == 2) return ELET;
        else if (i == 3) return ELEH;
        else if (i == 4) return EMPA;
        else if (i == 5) return EMPN;
        else throw new IllegalCellException();
    }
    @Override
    public String toString() { return name; }

    private final String name;

    public static final CellState
            WIRE = new CellState("WIRE"), // wire cell
            ELET = new CellState("ELET"), // electron tail
            ELEH = new CellState("ELEH"), // electron head
            EMPA = new CellState("...."), // empty cell, appendable
            EMPN = new CellState("EMPN"); // empty cell, not appendable
}