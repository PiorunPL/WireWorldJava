package logic.structures;

import logic.Direction;
import logic.cells.Cell;
import logic.cells.CellState;

public class Or extends Structure {
    public Or(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.xsize = 5;
        this.ysize = 5;
        this.direction = direction;
        this.name = "or";
        this.structure = new Cell[][]{
                {new Cell(0), new Cell(1), new Cell(0), new Cell(1), new Cell(0)},
                {new Cell(0), new Cell(0), new Cell(2), new Cell(0), new Cell(0)},
                {new Cell(0), new Cell(2), new Cell(2), new Cell(2), new Cell(1)},
                {new Cell(0), new Cell(0), new Cell(2), new Cell(0), new Cell(0)},
                {new Cell(0), new Cell(1), new Cell(0), new Cell(1), new Cell(0)}
        };
    }
}
