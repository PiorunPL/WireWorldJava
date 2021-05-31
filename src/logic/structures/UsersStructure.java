package logic.structures;

import logic.Direction;
import logic.cells.*;

public class UsersStructure extends Structure {
    public UsersStructure(String name, int xSize, int ySize, Cell[][] structure) {
        this.name = name;
        this.xSize = xSize;
        this.ySize = ySize;
        this.structure = structure;
    }

    public UsersStructure(String name, int xSize, int ySize, Cell[][] structure, int x, int y, Direction direction) {
        this.name = name;
        this.xSize = xSize;
        this.ySize = ySize;
        this.structure = structure;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public String getName() { return this.name; }

    public UsersStructure clone(int x, int y, Direction direction) {
        return new UsersStructure(this.name, this.xSize, this.ySize, this.structure, x, y, direction);
    }
}
