package logic.structures;

import logic.Direction;
import logic.cells.Cell;
import logic.cells.CellState;
import logic.cells.EmptyCell;
import logic.cells.WireCell;

public class Or extends Structure {
    public Or(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    private int x, y;
    private Direction direction;
    public final Cell[][] map = {
            {new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPN)},
            {new Cell(CellState.EMPN), new Cell(CellState.EMPN), new Cell(CellState.WIRE), new Cell(CellState.EMPN), new Cell(CellState.EMPN)},
            {new Cell(CellState.EMPN), new Cell(CellState.WIRE), new Cell(CellState.WIRE), new Cell(CellState.WIRE), new Cell(CellState.EMPA)},
            {new Cell(CellState.EMPN), new Cell(CellState.EMPN), new Cell(CellState.WIRE), new Cell(CellState.EMPN), new Cell(CellState.EMPN)},
            {new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPN)}
    };
}
