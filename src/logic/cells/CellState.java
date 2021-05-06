package logic.cells;

public class CellState {
    public CellState(String name) {
        this.name = name;
    }

    public static CellState setCellState(int i) {
        if (i == 0) return EMPN;
        else if (i == 1) return EMPA;
        else if (i == 2) return WIRE;
        else if (i == 3) return ELEH;
        else if (i == 4) return ELET;
        else return null;
    }
    @Override
    public String toString() { return name; }

    private String name;
    public static final CellState
            EMPN = new CellState("EMPN"), // empty cell, not appendable
            EMPA = new CellState("EMPA"), // empty cell, appendable
            WIRE = new CellState("WIRE"), // wire cell
            ELEH = new CellState("ELEH"), // electron head
            ELET = new CellState("ELET"); // electron tail
}