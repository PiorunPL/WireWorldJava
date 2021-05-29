package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class Wire extends Structure {
    public Wire() {
        this.xSize = 1;
        this.ySize = 1;
        this.name = "wire";
        this.direction = Direction.UP;
        this.structure = new Cell[][] {{new Cell(1)}};
        this.x = -1;
        this.y = -1;

    }
    public Wire(int x, int y) {
        this.xSize = 1;
        this.ySize = 1;
        this.x = x;
        this.y = y;
        this.name = "wire";
        this.direction = Direction.UP;
        this.structure = new Cell[][] {{new Cell(1)}};
    }
    public Wire(int x, int y, Direction direction, int length) {
        this.x = x;
        this.y = y;
        this.ySize = 1;
        this.direction = direction;
        this.name = "wire";
        setLength(length);
    }

    public int getLength() {
        if (this.xSize > 0 && this.ySize == 1) return xSize;
        else throw new IllegalStateException();
    }
    public void setLength(int length) {
        if (length != this.xSize) {
            this.xSize = length;
            this.structure = new Cell[xSize][1];
            for (int i = 0; i < xSize; i++) structure[i][0] = new Cell(1);
        }
    }
}
