package logic;

import logic.cells.Cell;
import logic.structures.Structure;

import static logic.cells.CellState.*;

public class Simulation {
    private CellMap cellMap;
    private int xsize;
    private int ysize;
    public Simulation(CellMap map) {
        this.cellMap = map;
    }

    public void simulate(int iterations){
        for (int i = 0; i < iterations; i++) {
            simulate();
        }
        save();
    }

    public void simulate() {
        setPreviousStateMap();
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                if (cellMap.getCell(i, j).getPreviousState() == EMPN) ;
                else if (cellMap.getCell(i, j).getPreviousState() == EMPA) ;
                else if (cellMap.getCell(i, j).getPreviousState() == WIRE) {
                    if (isNumberOfHeadsCorrect(i, j))
                        cellMap.getCell(i, j).changeState(ELEH);
                } else if (cellMap.getCell(i, j).getPreviousState() == ELEH)
                    cellMap.getCell(i, j).changeState(ELET);
                else if (cellMap.getCell(i, j).getPreviousState() == ELET)
                    cellMap.getCell(i, j).changeState(WIRE);
            }
        }

    }

    public void save() {

    }

    private void setPreviousStateMap() {
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                cellMap.getCell(i, j).updatePrevious();
            }
        }
    }

    private boolean isNumberOfHeadsCorrect(int x, int y) {
        int numberOfElectronHeads = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && j >= 0 && i < xsize && j < ysize && !(i == x && j == y)) {
                    if (cellMap.getCell(i-1, j-1).getPreviousState() == ELEH)
                        numberOfElectronHeads++;
                }
                if (numberOfElectronHeads > 2)
                    return false;
            }
        }

        if (numberOfElectronHeads != 0)
            return true;
        return false;

    }
}
