package utils;

import logic.CellMap;
import logic.Direction;
import logic.StructMap;
import logic.cells.*;
import logic.structures.Or;
import logic.structures.Structure;
import logic.structures.UsersStructure;
import logic.structures.UsersStructuresContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static logic.cells.CellState.*;
import static org.junit.jupiter.api.Assertions.*;

class DBopsTest {
    public static File testStructFormatFile;

    @BeforeAll
    public static void init() {
        testStructFormatFile = new File("test/testStructFormatFile");
    }

    @Test
    public void getUsersStructuresTest() throws FileNotFoundException {
        UsersStructuresContainer cont = new UsersStructuresContainer();
        Cell[][] cell1 = {
                {new Cell(0), new Cell(1), new Cell(0), new Cell(1), new Cell(1)},
                {new Cell(0), new Cell(1), new Cell(0), new Cell(1), new Cell(0)}
        };
        UsersStructure struct = new UsersStructure(
                "structName1", 2, 5, cell1);
        cont.add(struct);

        Cell[][] cell2 = {
                {new Cell(1), new Cell(0), new Cell(1), new Cell(0), new Cell(1)},
                {new Cell(1), new Cell(1), new Cell(1), new Cell(0), new Cell(1)}
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
        CellMap map = DBops.getMapFromFile(testStructFormatFile);
        //printStructMap(map);
    }

    @Test
    public void isCheckIfStructureFitWhenStructureFitDirectionUP() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(0, 0, Direction.UP);

        assertTrue((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureNotFitDirectionUP1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(1, 0, Direction.UP);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureNotFitDirectionUP2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(0, 1, Direction.UP);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureFitDirectionDOWN() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(4, 4, Direction.DOWN);

        assertTrue((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureNotFitDirectionDOWN1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(3, 4, Direction.DOWN);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureNotFitDirectionDOWN2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(4, 3, Direction.DOWN);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureFitDirectionRIGHT() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(0, 4, Direction.RIGHT);

        assertTrue((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureNotFitDirectionRIGHT1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(1, 4, Direction.RIGHT);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureNotFitDirectionRIGHT2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(0, 3, Direction.RIGHT);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureFitDirectionLEFT() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(4, 0, Direction.LEFT);

        assertTrue((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureNotFitDirectionLEFT1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(3, 0, Direction.LEFT);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCheckIfStructureFitWhenStructureNotFitDirectionLEFT2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("checkIfStructureFit", CellMap.class, Structure.class);
        method.setAccessible(true);

        CellMap cellMap = new CellMap(5, 5);
        Structure struct = new Or(4, 1, Direction.LEFT);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct));
    }

    @Test
    public void isCanBePlacedWhenTrue1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(new DBops(), EMPA, EMPA));
    }

    @Test
    public void isCanBePlacedWhenTrue2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(new DBops(), EMPA, EMPN));
    }

    @Test
    public void isCanBePlacedWhenTrue3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(new DBops(), EMPA, WIRE));
    }

    @Test
    public void isCanBePlacedWhenTrue4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(new DBops(), EMPA, ELEH));
    }

    @Test
    public void isCanBePlacedWhenTrue5() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(new DBops(), EMPA, ELET));
    }

    @Test
    public void isCanBePlacedWhenTrue6() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(new DBops(), WIRE, ELET));
    }

    @Test
    public void isCanBePlacedWhenTrue7() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertTrue((Boolean) method.invoke(new DBops(), WIRE, ELEH));
    }

    @Test
    public void isCanBePlacedWhenFalse1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), EMPN, EMPA));
    }

    @Test
    public void isCanBePlacedWhenFalse2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), EMPN, EMPN));
    }

    @Test
    public void isCanBePlacedWhenFalse3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), EMPN, WIRE));
    }

    @Test
    public void isCanBePlacedWhenFalse4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), EMPN, ELET));
    }

    @Test
    public void isCanBePlacedWhenFalse5() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), EMPN, ELEH));
    }

    @Test
    public void isCanBePlacedWhenFalse6() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELET, ELEH));
    }

    @Test
    public void isCanBePlacedWhenFalse7() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELET, WIRE));
    }

    @Test
    public void isCanBePlacedWhenFalse8() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), WIRE, EMPA));
    }

    @Test
    public void isCanBePlacedWhenFalse9() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), WIRE, WIRE));
    }

    @Test
    public void isCanBePlacedWhenFalse10() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELEH, ELEH));
    }

    @Test
    public void isCanBePlacedWhenFalse11() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELEH, WIRE));
    }

    @Test
    public void isCanBePlacedWhenFalse12() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELEH, EMPA));
    }

    @Test
    public void isCanBePlacedWhenFalse13() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELEH, EMPN));
    }

    @Test
    public void isCanBePlacedWhenFalse14() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELEH, ELET));
    }

    @Test
    public void isCanBePlacedWhenFalse15() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), WIRE, EMPN));
    }

    @Test
    public void isCanBePlacedWhenFalse16() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELET, EMPN));
    }

    @Test
    public void isCanBePlacedWhenFalse17() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELET, EMPA));
    }

    @Test
    public void isCanBePlacedWhenFalse18() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("canBePlaced", CellState.class, CellState.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(new DBops(), ELET, ELET));
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
            for (int j = 0; j < map.getStructure(i).getXSize(); j++) {
                System.out.printf("\t\t");
                for (int k = 0; k < map.getStructure(i).getYSize(); k++) {
                    System.out.printf("%s\t", map.getStructure(i).getCell(j, k).getState());
                }
                System.out.printf("\n");
            }
            System.out.printf("\n");
        }
    }
}