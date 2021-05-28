package utils;

import logic.CellMap;
import logic.Direction;
import logic.StructMap;
import logic.cells.Cell;
import logic.cells.CellState;
import logic.structures.Or;
import logic.structures.Structure;
import logic.structures.UsersStructure;
import logic.structures.UsersStructuresContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.exceptions.IllegalStructurePlacement;

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

    @Test
    public void getMapStructFormatThrowIllegalStructurePlacement1() throws NoSuchMethodException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("getMapStructFormat", StructMap.class);
        method.setAccessible(true);

        StructMap structMap = new StructMap(10, 10);
        structMap.addStruct("or", 0, 0, Direction.UP, -1);
        structMap.addStruct("or", 8, 7, Direction.UP, -1);

        try {
            method.invoke(new DBops(), structMap);
            fail("should have thrown an exception");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStructurePlacement);
        }
    }

    @Test
    public void getMapStructFormatThrowIllegalStructurePlacement2() throws NoSuchMethodException, IllegalAccessException {
        Method method = DBops.class.getDeclaredMethod("getMapStructFormat", StructMap.class);
        method.setAccessible(true);

        StructMap structMap = new StructMap(10, 10);
        structMap.addStruct("or", 0, 0, Direction.UP, -1);
        structMap.addStruct("or", 2, 2, Direction.UP, -1);

        try {
            method.invoke(new DBops(), structMap);
            fail("should have thrown an exception");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStructurePlacement);
        }
    }

    @Test
    public void getMapStructFormatNotThrowException() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = DBops.class.getDeclaredMethod("getMapStructFormat", StructMap.class);
        method.setAccessible(true);

        StructMap structMap = new StructMap(10, 10);
        structMap.addStruct("or", 0, 0, Direction.UP, -1);

        assertDoesNotThrow(() -> method.invoke(new DBops(), structMap));
    }

    @Test
    public void testPutStructToCellMapUp() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        CellMap cellMap = new CellMap(5, 5);

        Structure structure = new Or(0, 0, Direction.UP);

        Method method = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method.setAccessible(true);
        method.invoke(new DBops(), cellMap, structure);

        assertEquals(EMPN, cellMap.getCell(0, 0).getState());
        assertEquals(EMPN, cellMap.getCell(1, 1).getState());
        assertEquals(WIRE, cellMap.getCell(1, 2).getState());
        assertEquals(WIRE, cellMap.getCell(2, 1).getState());
        assertEquals(EMPN, cellMap.getCell(4, 2).getState());
        assertEquals(EMPA, cellMap.getCell(4, 3).getState());
    }

    @Test
    public void testPutStructToCellMapRight() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(5, 5);

        Structure structure = new Or(0, 4, Direction.RIGHT);

        Method method = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method.setAccessible(true);
        method.invoke(new DBops(), cellMap, structure);

        assertEquals(EMPN, cellMap.getCell(0, 4).getState());
        assertEquals(EMPN, cellMap.getCell(1, 3).getState());
        assertEquals(WIRE, cellMap.getCell(2, 3).getState());
        assertEquals(WIRE, cellMap.getCell(1, 2).getState());
        assertEquals(EMPN, cellMap.getCell(2, 0).getState());
        assertEquals(EMPA, cellMap.getCell(3, 0).getState());
    }


    @Test
    public void testPutStructToCellMapDown() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(5, 5);

        Structure structure = new Or(4, 4, Direction.DOWN);

        Method method = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method.setAccessible(true);
        method.invoke(new DBops(), cellMap, structure);

        assertEquals(EMPN, cellMap.getCell(4, 4).getState());
        assertEquals(EMPN, cellMap.getCell(3, 3).getState());
        assertEquals(WIRE, cellMap.getCell(3, 2).getState());
        assertEquals(WIRE, cellMap.getCell(2, 3).getState());
        assertEquals(EMPN, cellMap.getCell(0, 2).getState());
        assertEquals(EMPA, cellMap.getCell(0, 1).getState());
    }

    @Test
    public void testPutStructToCellMapLeft() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(5, 5);

        Structure structure = new Or(4, 0, Direction.LEFT);

        Method method = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method.setAccessible(true);
        method.invoke(new DBops(), cellMap, structure);

        assertEquals(EMPN, cellMap.getCell(4, 0).getState());
        assertEquals(EMPN, cellMap.getCell(3, 1).getState());
        assertEquals(WIRE, cellMap.getCell(3, 2).getState());
        assertEquals(WIRE, cellMap.getCell(2, 1).getState());
        assertEquals(EMPN, cellMap.getCell(2, 4).getState());
        assertEquals(EMPA, cellMap.getCell(0, 2).getState());
    }

    @Test
    public void testCheckIfSpaceForStructureIsClearFailUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(10, 10);

        Structure struct1 = new Or(0, 0, Direction.UP);
        Structure struct2 = new Or(4, 4, Direction.UP);

        Method method = DBops.class.getDeclaredMethod("checkIfSpaceForStructureIsClear", CellMap.class, Structure.class);
        method.setAccessible(true);

        Method method1 = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method1.setAccessible(true);
        method1.invoke(new DBops(), cellMap, struct1);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct2));
    }

    @Test
    public void testCheckIfSpaceForStructureIsClearFailDown() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(10, 10);

        Structure struct1 = new Or(0, 0, Direction.UP);
        Structure struct2 = new Or(8, 8, Direction.DOWN);

        Method method = DBops.class.getDeclaredMethod("checkIfSpaceForStructureIsClear", CellMap.class, Structure.class);
        method.setAccessible(true);

        Method method1 = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method1.setAccessible(true);
        method1.invoke(new DBops(), cellMap, struct1);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct2));
    }

    @Test
    public void testCheckIfSpaceForStructureIsClearFailLeft() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(10, 10);

        Structure struct1 = new Or(0, 0, Direction.UP);
        Structure struct2 = new Or(7, 2, Direction.LEFT);

        Method method = DBops.class.getDeclaredMethod("checkIfSpaceForStructureIsClear", CellMap.class, Structure.class);
        method.setAccessible(true);

        Method method1 = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method1.setAccessible(true);
        method1.invoke(new DBops(), cellMap, struct1);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct2));
    }

    @Test
    public void testCheckIfSpaceForStructureIsClearFailRight() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(10, 10);

        Structure struct1 = new Or(0, 0, Direction.UP);
        Structure struct2 = new Or(2, 7, Direction.RIGHT);

        Method method = DBops.class.getDeclaredMethod("checkIfSpaceForStructureIsClear", CellMap.class, Structure.class);
        method.setAccessible(true);

        Method method1 = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method1.setAccessible(true);
        method1.invoke(new DBops(), cellMap, struct1);

        assertFalse((Boolean) method.invoke(new DBops(), cellMap, struct2));
    }

    @Test
    public void testCheckIfSpaceForStructureIsClearUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(10, 10);

        Structure struct1 = new Or(0, 0, Direction.UP);
        Structure struct2 = new Or(5, 5, Direction.UP);

        Method method = DBops.class.getDeclaredMethod("checkIfSpaceForStructureIsClear", CellMap.class, Structure.class);
        method.setAccessible(true);

        Method method1 = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method1.setAccessible(true);
        method1.invoke(new DBops(), cellMap, struct1);

        assertTrue((Boolean) method.invoke(new DBops(), cellMap, struct2));
    }

    @Test
    public void testCheckIfSpaceForStructureIsClearRight() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(10, 10);

        Structure struct1 = new Or(0, 0, Direction.UP);
        Structure struct2 = new Or(2, 9, Direction.RIGHT);

        Method method = DBops.class.getDeclaredMethod("checkIfSpaceForStructureIsClear", CellMap.class, Structure.class);
        method.setAccessible(true);

        Method method1 = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method1.setAccessible(true);
        method1.invoke(new DBops(), cellMap, struct1);

        assertTrue((Boolean) method.invoke(new DBops(), cellMap, struct2));
    }

    @Test
    public void testCheckIfSpaceForStructureIsClearDown() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(10, 10);

        Structure struct1 = new Or(0, 0, Direction.UP);
        Structure struct2 = new Or(6, 9, Direction.DOWN);

        Method method = DBops.class.getDeclaredMethod("checkIfSpaceForStructureIsClear", CellMap.class, Structure.class);
        method.setAccessible(true);

        Method method1 = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method1.setAccessible(true);
        method1.invoke(new DBops(), cellMap, struct1);

        assertTrue((Boolean) method.invoke(new DBops(), cellMap, struct2));
    }

    @Test
    public void testCheckIfSpaceForStructureIsClearLeft() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CellMap cellMap = new CellMap(10, 10);

        Structure struct1 = new Or(0, 0, Direction.UP);
        Structure struct2 = new Or(9, 4, Direction.LEFT);

        Method method = DBops.class.getDeclaredMethod("checkIfSpaceForStructureIsClear", CellMap.class, Structure.class);
        method.setAccessible(true);

        Method method1 = DBops.class.getDeclaredMethod("putStructToCellMap", CellMap.class, Structure.class);
        method1.setAccessible(true);
        method1.invoke(new DBops(), cellMap, struct1);

        assertTrue((Boolean) method.invoke(new DBops(), cellMap, struct2));
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