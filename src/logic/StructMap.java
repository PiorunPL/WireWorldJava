package logic;

import logic.cells.Cell;
import logic.structures.Map;
import logic.structures.Structure;
import logic.structures.UsersStructuresContainer;
import utils.DBops;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// co to tak na prawdę są te x i y w tym przypadku?
public class StructMap {
    public StructMap(int x, int y) {
        if (x > 0 && y > 0) this.knownDimensions = true;
        else this.knownDimensions = false;
        this.xsize = x;
        this.ysize = y;
        list = new ArrayList<Structure>();
    }

    // container for all structures used in this map
    private List<Structure> list;

    // user's structures used in this map
    public UsersStructuresContainer userStructures;

    // map dimensions
    private int xsize, ysize;

    // are map dimensions known?
    private boolean knownDimensions;


    public Structure getStructure(int i) { return this.list.get(i); }

    public int size() { return list.size(); }

    public void addStruct(String name, int x, int y, Direction direction, int length) {
        Structure struct;
        if ((struct = Structure.isStructure(name, userStructures, x, y, direction, length)) != null) {
            this.list.add(struct);
        }
        else System.out.printf("Structure '%s' doesn't exist in this context.\n", name);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StructMap)) return false;
        StructMap map = (StructMap) o;

        if (this.xsize != map.xsize ||
            this.ysize != map.ysize ||
            this.knownDimensions != map.knownDimensions ||
            this.list.size() != map.list.size()
        ) return false;

        for (int i = 0; i < list.size(); i++) {
            if (!map.getStructure(i).getName().equals(this.list.get(i).getName()) &&
                !map.getStructure(i).getClass().getName().equals(this.list.get(i).getClass().getName()) &&
                map.getStructure(i).getX() != this.list.get(i).getX() &&
                map.getStructure(i).getY() != this.list.get(i).getY() &&
                map.getStructure(i).getDirection() != this.list.get(i).getDirection()
            ) return false;
        }
        return true;
    }
}
