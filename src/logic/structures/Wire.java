package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class Wire extends Structure {
    public Wire() {
        this.xsize = 1;
        this.ysize = 1;
        this.name = "wire";
        this.direction = Direction.UP;
        this.structure = new Cell[][] {{new Cell(1)}};
        this.x = -1;
        this.y = -1;

    }
    public Wire(int x, int y) {
        this.xsize = 1;
        this.ysize = 1;
        this.x = x;
        this.y = y;
        this.name = "wire";
        this.direction = Direction.UP;
        this.structure = new Cell[][] {{new Cell(1)}};
    }
    public Wire(int x, int y, Direction direction, int length) {
        this.x = x;
        this.y = y;
        this.ysize = 1;
        this.direction = direction;
        this.name = "wire";
        setLength(length);
    }

    public int getLength() {
        if (this.xsize > 0 && this.ysize == 1) return xsize;
        else return -1;
    }
    public void setLength(int length) {
        if (length != this.xsize) {
            this.xsize = length;
            this.structure = new Cell[xsize][1];
            for (int i = 0; i < xsize; i++) structure[i][0] = new Cell(1);
        }
    }
}
