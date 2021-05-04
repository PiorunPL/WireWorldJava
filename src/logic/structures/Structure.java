package logic.structures;

import logic.Direction;

import java.util.Scanner;

public abstract class Structure {
    private String name;
    private int x, y;
    private Direction direction;

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Direction getDirection() { return direction; }


    public static Structure isStructure(String name, UsersStructuresContainer container, int x, int y, Direction direction, int length) {
        if (name.equals("and")) return new And(x, y, direction);
        else if (name.equals("clock")) return new Clock(x, y, direction);
        else if (name.equals("diode")) return new Diode(x, y, direction);
        else if (name.equals("not")) return new Not(x, y, direction);
        else if (name.equals("or")) return new Or(x, y, direction);
        else if (name.equals("wire") && length > 0) return new Wire(x, y, direction, length);
        else if (name.equals("xor")) return new Xor(x, y, direction);
        else {
            if (container == null) return null;
            for (int i = 0; i < container.size(); i++) {
                if (name.equals(container.get(i).getName())) return container.get(i).clone(x, y, direction);
            }
            return null;
        }
    }
}
