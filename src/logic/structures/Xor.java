package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class Xor extends Structure {
    public Xor() {
        this.xsize = 5;
        this.ysize = 6;
        this.direction = Direction.UP;
        this.name = "xor";
        this.structure = new Cell[][]{
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(1), new Cell(4)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5)}

        };
    }
    public Xor(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.xsize = 5;
        this.ysize = 6;
        this.direction = direction;
        this.name = "xor";
        this.structure = new Cell[][]{
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(1), new Cell(4)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5)}

        };
    }


}
