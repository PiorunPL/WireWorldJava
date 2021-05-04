package logic.structures;

import logic.Direction;

public class Wire extends Structure {
    public Wire(int x, int y, Direction direction, int length) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.length = length;
    }

    private int x, y;
    private Direction direction;
    private int length;

    public int getLength() {
        int length = 0;
        return length;
    }
}
