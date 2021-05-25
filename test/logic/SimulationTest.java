package logic;


import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static logic.cells.CellState.*;
import static org.junit.jupiter.api.Assertions.*;


public class SimulationTest {

    @Test
    public void isNumberOfHeadsCorrectForCentralCellWithMoreElectronHead() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        CellMap cellMap1 = new CellMap(3, 3);

        cellMap1.getCell(0, 0).changeState(ELEH);
        cellMap1.getCell(0, 2).changeState(ELEH);
        cellMap1.getCell(2, 1).changeState(ELEH);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellMap1.getCell(i, j).updatePrevious();
            }
        }

        Simulation simulation = new Simulation(cellMap1);

        Method method = Simulation.class.getDeclaredMethod("isNumberOfHeadsCorrect", int.class, int.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(simulation, 1, 1));

    }

    @Test
    public void isNumberOfHeadsCorrectForCentralCellWithLessElectronHead() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        CellMap cellMap1 = new CellMap(3, 3);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellMap1.getCell(i, j).updatePrevious();
            }
        }

        Simulation simulation = new Simulation(cellMap1);

        Method method = Simulation.class.getDeclaredMethod("isNumberOfHeadsCorrect", int.class, int.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(simulation, 1, 1));

    }

    @Test
    public void isNumberOfHeadsCorrectForCentralCellWithCorrectNumberOfElectronHeads1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        CellMap cellMap1 = new CellMap(3, 3);

        cellMap1.getCell(0, 0).changeState(ELEH);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellMap1.getCell(i, j).updatePrevious();
            }
        }

        Simulation simulation = new Simulation(cellMap1);

        Method method = Simulation.class.getDeclaredMethod("isNumberOfHeadsCorrect", int.class, int.class);
        method.setAccessible(true);
        assertTrue((Boolean) method.invoke(simulation, 1, 1));

    }

    @Test
    public void isNumberOfHeadsCorrectForCentralCellWithCorrectNumberOfElectronHeads2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        CellMap cellMap1 = new CellMap(3, 3);

        cellMap1.getCell(0, 0).changeState(ELEH);
        cellMap1.getCell(0, 2).changeState(ELEH);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellMap1.getCell(i, j).updatePrevious();
            }
        }

        Simulation simulation = new Simulation(cellMap1);

        Method method = Simulation.class.getDeclaredMethod("isNumberOfHeadsCorrect", int.class, int.class);
        method.setAccessible(true);
        assertTrue((Boolean) method.invoke(simulation, 1, 1));

    }


    @Test
    public void isCellVectorChangedClear() {

        CellMap cellMap1 = new CellMap(3, 3);


        Simulation simulation = new Simulation(cellMap1);
        simulation.getCellVectorChanged().add(cellMap1.getCell(0, 0));
        simulation.getCellVectorChanged().add(cellMap1.getCell(0, 2));

        assertEquals(2, simulation.getCellVectorChanged().size());

        simulation.cleanCellVectorChanged();

        assertEquals(0, simulation.getCellVectorChanged().size());
    }

    @Test
    public void testSimulation1() {
        CellMap cellMap1 = new CellMap(3, 3);

        cellMap1.getCell(0, 0).changeState(ELEH);
        cellMap1.getCell(0, 2).changeState(ELEH);
        cellMap1.getCell(2, 1).changeState(ELEH);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellMap1.getCell(i, j).updatePrevious();
            }
        }

        Simulation simulation = new Simulation(cellMap1);

        simulation.simulate();
        CellMap cellMapAfterSimulation1 = simulation.getCellMap();
        assertSame(cellMapAfterSimulation1.getCell(0, 0).getState(), ELET);
        assertSame(cellMapAfterSimulation1.getCell(0, 2).getState(), ELET);
        assertSame(cellMapAfterSimulation1.getCell(2, 1).getState(), ELET);
        assertSame(cellMapAfterSimulation1.getCell(0, 1).getState(), EMPA);

        simulation.simulate();
        CellMap cellMapAfterSimulation2 = simulation.getCellMap();
        assertSame(cellMapAfterSimulation2.getCell(0, 0).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(0, 2).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(2, 1).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(0, 1).getState(), EMPA);

        assertEquals(3, simulation.getCellVectorChanged().size());
    }

    @Test
    public void testSimulationWithIterations() {
        CellMap cellMap1 = new CellMap(3, 3);

        cellMap1.getCell(0, 0).changeState(ELEH);
        cellMap1.getCell(0, 2).changeState(ELEH);
        cellMap1.getCell(2, 1).changeState(ELEH);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellMap1.getCell(i, j).updatePrevious();
            }
        }

        Simulation simulation = new Simulation(cellMap1);

        simulation.simulate(2);
        CellMap cellMapAfterSimulation2 = simulation.getCellMap();
        assertSame(cellMapAfterSimulation2.getCell(0, 0).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(0, 2).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(2, 1).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(0, 1).getState(), EMPA);

        assertEquals(3, simulation.getCellVectorChanged().size());

        simulation.simulate(2);
        assertSame(cellMapAfterSimulation2.getCell(0, 0).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(0, 2).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(2, 1).getState(), WIRE);
        assertSame(cellMapAfterSimulation2.getCell(0, 1).getState(), EMPA);

        assertEquals(0, simulation.getCellVectorChanged().size());
    }

}
