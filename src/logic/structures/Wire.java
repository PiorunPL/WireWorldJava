package logic.structures;

import logic.Direction;

public class Wire extends Structure {
    public Wire() { }
    public Wire(int x, int y, Direction direction, int length) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.length = length;
    }

    private int length;

    public int getLength() { return this.length; }
    public void setLength(int length) { this.length = length; }
}
