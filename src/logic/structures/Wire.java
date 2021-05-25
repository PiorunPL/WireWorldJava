package logic.structures;

import logic.Direction;
import logic.cells.Cell;

public class Wire extends Structure {
    public Wire() {
        this.xsize = 1;
        this.ysize = 1;
        this.name = "wire";
        this.direction = Direction.UP;
    }
    public Wire(int x, int y) {
        this.xsize = 1;
        this.ysize = 1;
        this.x = x;
        this.y = y;
        this.name = "wire";
        this.direction = Direction.UP;
    }
    public Wire(int x, int y, Direction direction, int length) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.length = length;
        this.name = "wire";
        this.structure = new Cell[length][1];
        for (int i = 0; i < length; i++) {
            this.structure[i][0] = new Cell(1);
        }
    }

    private int length;

    public int getLength() {
        if (this.xsize > 0 && this.ysize == 1) return xsize;
        else return -1;
    }
    public void setLength(int length) { this.length = length; }
}
