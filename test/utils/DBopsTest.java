package utils;

import logic.Direction;
import logic.StructMap;
import logic.cells.*;
import logic.structures.UsersStructure;
import logic.structures.UsersStructuresContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DBopsTest {
    public static File testStructFormatFile;

    @BeforeAll
    public static void init() {
        testStructFormatFile = new File("C:\\Users\\sebas\\Documents\\Studia\\Informatka Stosowana\\2_semestr\\Języki i Metody Programowania 2\\Materiały\\Projekt_2\\WireWorldJava\\test\\testStructFormatFile");
    }

    @Test
    public void getTypeTest() throws FileNotFoundException {
        assertEquals("struct", DBops.getType(testStructFormatFile));
    }

    @Test
    public void getUsersStructuresTest() throws FileNotFoundException {
        UsersStructuresContainer cont = new UsersStructuresContainer();
        Cell[][] cell1 = {
                {new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPA)},
                {new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPN)}
        };
        UsersStructure struct = new UsersStructure(
                "structName1", 2, 5, cell1);
        cont.add(struct);

        Cell[][] cell2 = {
                {new Cell(CellState.EMPA), new Cell(CellState.EMPN), new Cell(CellState.EMPA), new Cell(CellState.EMPN), new Cell(CellState.EMPA)},
                {new Cell(CellState.EMPA), new Cell(CellState.EMPA), new Cell(CellState.EMPA), new Cell(CellState.EMPN), new Cell(CellState.EMPA)}
        };
        struct = new UsersStructure(
                "structName2", 2, 5, cell2);
        cont.add(struct);

        //DBops.getUsersStructures(testStructFormatFile).get(0).print();
        //DBops.getUsersStructures(testStructFormatFile).get(1).print();
        //cont.get(0).print();
        //cont.get(1).print();
        assertTrue(cont.equals(DBops.getUsersStructures(testStructFormatFile)));
    }

    // temporary test, does not test this method, only prints StructMap
    @Test
    public void getMapFromFileTest() throws FileNotFoundException {
        StructMap map = DBops.getMapFromFile(testStructFormatFile);
        printStructMap(map);
    }

    public void printStructMap(StructMap map) {
        System.out.printf("StructMap\n\tlength: %d\n", map.size());
        System.out.printf("\tsize: [%d, %d], %s\n",
                map.getXsize(),
                map.getYsize(),
                map.getKnownDimensions() ? "known" : "not known"
        );
        for (int i = 0; i < map.size(); i++) {
            System.out.printf("\tname: %s, x: %d, y: %d, direction: %s, class: %s\n\tstructure:\n",
                    map.getStructure(i).getName(),
                    map.getStructure(i).getX(),
                    map.getStructure(i).getY(),
                    map.getStructure(i).getDirection().toString(),
                    map.getStructure(i).getClass().getName());
            for (int j = 0; j < map.getStructure(i).getXsize(); j++) {
                System.out.printf("\t\t");
                for (int k = 0; k < map.getStructure(i).getYsize(); k++) {
                    System.out.printf("%s\t", map.getStructure(i).getCell(j, k).getState().getClass().getName());
                }
                System.out.printf("\n");
            }
            System.out.printf("\n");
        }
    }
}