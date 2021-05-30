package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class Clock extends Structure {
    public Clock() {
        xsize = 5;
        ysize = 7;
        direction = Direction.UP;
        name = "clock";
        structure = new Cell[][]{
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(1), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(1)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(5)}
        };
    }
    public Clock(int x, int y, Direction direction) {
        this();
        this.x = x;
        this.y = y;
        this.direction = direction;

    }
}
