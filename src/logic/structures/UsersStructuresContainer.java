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

    // robocza
    //TODO: usunąć
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UsersStructuresContainer)) return false;
        UsersStructuresContainer container = (UsersStructuresContainer) obj;
        if (container.size() != this.size()) {
            System.out.println("size");
            return false;
        }
        for (int i = 0; i < container.size(); i++)
            if (!this.get(i).equals(container.get(i))) {
                System.out.println("struct" + i);
                return false;
            }
        return true;
    }
}
