package logic.cells;

public class CellState {
    public CellState(String name) {
        this.name = name;
    }

    public static CellState setCellState(int i) {
        if (i == 1) return WIRE;
        else if (i == 2) return ELET;
        else if (i == 3) return ELEH;
        else if (i == 4) return EMPA;
        else if (i == 5) return EMPN;
        else return null;
    }
    @Override
    public String toString() { return name; }

    private String name;
    public static final CellState
            WIRE = new CellState("WIRE"), // wire cell
            ELET = new CellState("ELET"), // electron tail
            ELEH = new CellState("ELEH"), // electron head
            EMPA = new CellState("...."), // empty cell, appendable
            EMPN = new CellState("EMPN"); // empty cell, not appendable
}