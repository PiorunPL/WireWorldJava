package logic.cells;

import logic.cells.Cell;

public class EmptyCell implements CellState {
    public EmptyCell(boolean appendable) {
        this.appendable = appendable;
    }

    public boolean appendable;
}
