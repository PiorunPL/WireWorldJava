package logic.structures;

import java.util.ArrayList;
import java.util.List;

public class UsersStructuresContainer {
    public UsersStructuresContainer() {
        list = new ArrayList<UsersStructure>();
    }

    private List<UsersStructure> list;

    public void add(UsersStructure structure) {
        list.add(structure);
    }

    public int size() {
        return list.size();
    }

    public UsersStructure get(int i) {
        return list.get(i);
    }
}
