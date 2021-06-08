package logic;

import logic.structures.Structure;
import logic.structures.UsersStructuresContainer;
import logic.structures.Wire;

import java.util.List;
import java.util.Vector;

public class StructMap {
    public StructMap(int x, int y) {
        this.knownDimensions = x > 0 && y > 0;
        this.xSize = x;
        this.ySize = y;
        vector = new Vector<Structure>();
    }

    // container for all structures used in this map
    private Vector<Structure> vector;

    public void removeStructure(Structure structure){

        vector.remove(structure);
    }

    // user's structures used in this map
    private UsersStructuresContainer userStructures;

    // map dimensions
    private final int xSize;
    private final int ySize;

    // are map dimensions known?
    private final boolean knownDimensions;

    public UsersStructuresContainer getUserStructures() { return this.userStructures; }
    public Structure getStructure(int i) { return this.vector.get(i); }
    public int getXSize() { return this.xSize; }
    public int getYSize() { return this.ySize; }
    public boolean getKnownDimensions() { return knownDimensions; }

    public int size() { return vector.size(); }

    public void addUserStructures(UsersStructuresContainer container) { this.userStructures = container; }
    public void addStruct(String name, int x, int y, Direction direction, int length) {
        Structure struct;
        if ((struct = Structure.isStructure(name, this.userStructures, x, y, direction, length)) != null) {
            this.vector.add(struct);
        }
        else System.out.printf("Structure '%s' doesn't exist in this context.\n", name);
    }
    public void add(Structure structure)    {        this.vector.add(structure);    }

    public static StructMap backupMap(StructMap toBackup){
        StructMap backupMap;
        backupMap = new StructMap(toBackup.getXSize(), toBackup.getYSize());
        backupMap.addUserStructures(toBackup.getUserStructures());
        Structure str;
        int length = -1;
        for(int i = 0; i<toBackup.size(); i++){
            str = toBackup.getStructure(i);
            if (str.getName().equals("wire")) {
                length = ((Wire) str).getLength();
            }
            backupMap.addStruct(str.getName(), str.getX(), str.getY(), str.getDirection(), length);
            length = -1;
        }
        return backupMap;
    }
}
