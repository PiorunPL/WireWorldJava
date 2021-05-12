package logic.structures;

import logic.Direction;
import logic.cells.Cell;

import java.util.Scanner;

public abstract class Structure {
    protected String name;
    protected int x, y;
    protected Direction direction;
    protected Cell[][] structure;
    protected int xsize, ysize;

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Direction getDirection() { return direction; }
    public int getXSize() { return xsize; }
    public int getYSize() { return ysize; }
    public Cell getCell(int x, int y) {
        if (x < structure.length && y < structure[0].length) return structure[x][y];
        else return null;
    }

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
