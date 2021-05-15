package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class Xor extends Structure {
    public Xor() {
        this.xsize = 7;
        this.ysize = 6;
        this.direction = direction;
        this.name = "xor";
        this.structure = new Cell[][]{
                {new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(4)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(1), new Cell(4)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(4)},

        };
    }
    public Xor(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.xsize = 7;
        this.ysize = 6;
        this.direction = direction;
        this.name = "xor";
        this.structure = new Cell[][]{
                {new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(4)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(1), new Cell(4)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5)},
                {new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(4)},

        };
    }


}
