package logic.structures;

import logic.Direction;
import logic.cells.*;

public class UsersStructure extends Structure {
    public UsersStructure(String name, int xsize, int ysize, Cell[][] structure) {
        this.name = name;
        this.xsize = xsize;
        this.ysize = ysize;
        this.structure = structure;
    }

    public UsersStructure(String name, int xsize, int ysize, Cell[][] structure, int x, int y, Direction direction) {
        this.name = name;
        this.xsize = xsize;
        this.ysize = ysize;
        this.structure = structure;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public String getName() { return this.name; }

    public UsersStructure clone(int x, int y, Direction direction) {
        return new UsersStructure(this.name, this.xsize, this.ysize, this.structure, x, y, direction);
    }

    // robocza
    //TODO: usunąć
    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof UsersStructure)) return false;
        UsersStructure struct = (UsersStructure) obj;
        if (!this.name.equals(struct.name)) {
            System.out.println("name");
            System.out.printf("%s, %s\n", struct.name, this.name);
            return false;
        }
        if (this.x != struct.x) {
            System.out.println("x");
            return false;
        }
        if (this.y != struct.y) {
            System.out.println("y");
            return false;
        }
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                if (!this.structure[i][j].getClass().getName().equals(struct.structure[i][j].getClass().getName())) {
                    System.out.printf("[%d, %d]:\n", i, j);
                    System.out.printf("%s, %s\n",
                            this.structure[i][j].getClass().getName(),
                            struct.structure[i][j].getClass().getName());
                    return false;
                }
        return true;
    }

    // robocza
    //TODO: usunąć
    public void print() {
        System.out.printf("%s [%d, %d]\n", name, x, y);
        int liczba;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (structure[i][j].getClass().getName().equals(new EmptyCell(true).getClass().getName())) liczba = 1;
                else if (structure[i][j].getClass().getName().equals(new WireCell().getClass().getName())) liczba = 2;
                else if (structure[i][j].getClass().getName().equals(new ElectronHead().getClass().getName())) liczba = 3;
                else if (structure[i][j].getClass().getName().equals(new ElectronTail().getClass().getName())) liczba = 4;
                else liczba = -1;
                System.out.printf("%d ", liczba);
            }
            System.out.printf("\n");
        }
    }
}
