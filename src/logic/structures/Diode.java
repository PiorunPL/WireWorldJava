package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class Diode extends Structure {
    public Diode() {
        this.xsize = 5;
        this.ysize = 4;
        this.direction = Direction.UP;
        this.name = "diode";
        this.structure = new Cell[][]{
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(1), new Cell(1), new Cell(5), new Cell(1)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5)}
        };
    }
    public Diode(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.xsize = 5;
        this.ysize = 4;
        this.direction = direction;
        this.name = "diode";
        this.structure = new Cell[][]{
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(1), new Cell(1), new Cell(5), new Cell(1)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5)}
        };
    }
}
