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
    public void getDimensionsTest() throws FileNotFoundException {
        int[] dimensions = DBops.getDimensions(testStructFormatFile);
        assertEquals(100, dimensions[0]);
        assertEquals(200, dimensions[1]);
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

    @Test
    public void getMapFromFileTest() throws FileNotFoundException {
        StructMap map = new StructMap(100, 200);
        map.userStructures = DBops.getUsersStructures(testStructFormatFile);
        map.addStruct("diode", 11, 20, Direction.setDirection("u"), -1);
        //map.addStruct("structName1", 20, 30, Direction.setDirection("r"), -1);
        assertEquals(DBops.getMapFromFile(testStructFormatFile), map);
    }
}