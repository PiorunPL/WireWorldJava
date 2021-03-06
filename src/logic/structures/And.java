package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class And extends Structure {
    public And() {
        this.xSize = 8;
        this.ySize = 9;
        this.direction = Direction.UP;
        this.name = "and";
        this.structure = new Cell[][]{
                {new Cell(4), new Cell(4), new Cell(4), new Cell(1), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(4)},
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5), new Cell(1), new Cell(5)},
                {new Cell(1), new Cell(1), new Cell(1), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(5), new Cell(5)},
                {new Cell(4), new Cell(4), new Cell(4), new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(4)},
                {new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(5), new Cell(5), new Cell(5), new Cell(4), new Cell(4)},

        };
    }
    public And(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.xSize = 8;
        this.ySize = 9;
        this.direction = direction;
        this.name = "and";
        this.structure = new Cell[][]{
                {new Cell(4), new Cell(4), new Cell(4), new Cell(1), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(4)},
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(5), new Cell(5), new Cell(5), new Cell(1), new Cell(5)},
                {new Cell(1), new Cell(1), new Cell(1), new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(5), new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(5), new Cell(5)},
                {new Cell(4), new Cell(4), new Cell(4), new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(1), new Cell(4)},
                {new Cell(4), new Cell(4), new Cell(4), new Cell(4), new Cell(5), new Cell(5), new Cell(5), new Cell(4), new Cell(4)},

        };
    }
}
