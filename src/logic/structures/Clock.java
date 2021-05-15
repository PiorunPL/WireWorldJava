package logic.structures;

import logic.Direction;

public class Clock extends Structure {
    public Clock() { }
    public Clock(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
