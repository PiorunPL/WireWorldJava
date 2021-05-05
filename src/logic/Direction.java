package logic;

public final class Direction {
    private Direction(String name) { this.name = name; }

    @Override
    public String toString() { return name; }

    private String name;
    public static final Direction
            UP = new Direction("u"),
            DOWN = new Direction("d"),
            RIGHT = new Direction("r"),
            LEFT = new Direction("l");

    public static Direction setDirection(String name) {
        if (name.equals("u")) return UP;
        else if (name.equals("d")) return DOWN;
        else if (name.equals("r")) return RIGHT;
        else if (name.equals("l")) return LEFT;
        else return null;
    }

    public void setUp() {

    }

    public void setDown() {

    }

    public void setRight() {

    }

    public void setLeft() {

    }

    public Boolean isUp() {
        boolean b = false;
        return b;
    }

    public Boolean isDown() {
        boolean b = false;
        return b;
    }

    public Boolean isLeft() {
        boolean b = false;
        return b;
    }

    public Boolean isRight() {
        boolean b = false;
        return b;
    }
}
