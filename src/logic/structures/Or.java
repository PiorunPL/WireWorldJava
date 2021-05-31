package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class Or extends Structure {
    public Or() {
        this.xSize = 5;
        this.ySize = 5;
        this.direction = Direction.UP;
        this.name = "or";
        this.structure = new Cell[][]{
                {new Cell(5), new Cell(4), new Cell(5), new Cell(4), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(4)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(4), new Cell(5), new Cell(4), new Cell(5)}
        };
        this.direction = Direction.UP;
    }
    public Or(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.xSize = 5;
        this.ySize = 5;
        this.direction = direction;
        this.name = "or";
        this.structure = new Cell[][]{
                {new Cell(5), new Cell(4), new Cell(5), new Cell(4), new Cell(5)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(1), new Cell(1), new Cell(1), new Cell(4)},
                {new Cell(5), new Cell(5), new Cell(1), new Cell(5), new Cell(5)},
                {new Cell(5), new Cell(4), new Cell(5), new Cell(4), new Cell(5)}
        };
    }
}
