package logic.structures;

import logic.Direction;

public class Clock extends Structure {
    public Clock(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    private int x, y;
    private Direction direction;
}
