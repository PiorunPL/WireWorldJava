package logic;

public final class Direction {
    private Direction(String name) { this.name = name; }

    @Override
    public String toString() { return name; }

    private final String name;
    public static final Direction
            UP = new Direction("u"),
            DOWN = new Direction("d"),
            RIGHT = new Direction("r"),
            LEFT = new Direction("l");

    public static Direction setDirection(String name) {
        return switch (name) {
            case "u" -> UP;
            case "d" -> DOWN;
            case "r" -> RIGHT;
            case "l" -> LEFT;
            default -> null;
        };
    }
}
