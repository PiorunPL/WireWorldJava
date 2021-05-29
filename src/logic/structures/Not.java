package logic.structures;

import logic.Direction;
import logic.cells.Cell;

import static logic.Direction.UP;

public class Not extends Structure {
    public Not() {
        xsize = 6;
        ysize = 6;
        direction = UP;
        name = "not";
        structure = new Cell[][]{
                {new Cell(4), new Cell(4), new Cell(1), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(4), new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(1)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(3), new Cell(5), new Cell(3), new Cell(3), new Cell(5)},
                {new Cell(5), new Cell(2), new Cell(1), new Cell(2), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(4)}
        };
    }

    public Not(int x, int y, Direction direction) {
        this();
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
