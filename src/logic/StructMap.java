package logic;

import logic.structures.Structure;
import logic.structures.UsersStructuresContainer;

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
    private UsersStructuresContainer userStructures;

    // map dimensions
    private int xsize, ysize;

    // are map dimensions known?
    private boolean knownDimensions;

    public UsersStructuresContainer getUserStructures() { return this.userStructures; }
    public Structure getStructure(int i) { return this.list.get(i); }
    public int getXsize() { return this.xsize; }
    public int getYsize() { return this.ysize; }
    public boolean getKnownDimensions() { return knownDimensions; }

    public int size() { return list.size(); }

    public void addUserStructures(UsersStructuresContainer container) { this.userStructures = container; }
    public void addStruct(String name, int x, int y, Direction direction, int length) {
        Structure struct;
        if ((struct = Structure.isStructure(name, this.userStructures, x, y, direction, length)) != null) {
            this.list.add(struct);
        }
        else System.out.printf("Structure '%s' doesn't exist in this context.\n", name);
    }
}
