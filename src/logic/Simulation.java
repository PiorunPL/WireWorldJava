package logic;

import logic.cells.Cell;

import static logic.cells.CellState.*;

public class Simulation {
    private Cell[][] cellMap;
    private int xsize;
    private int ysize;

    public Simulation(StructMap map) {
        xsize = map.getXsize();
        ysize = map.getYsize();
        cellMap = new Cell[xsize][ysize];
    }

    public void simulate(int iterations) {
        for (int i = 0; i < iterations; i++) {
            simulate();
        }
        save();
    }

    public void simulate() {
        setPreviousStateMap();
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                if (cellMap[i][j].getPreviousState() == EMPN) ;
                else if (cellMap[i][j].getPreviousState() == EMPA) ;
                else if (cellMap[i][j].getPreviousState() == WIRE) {
                    if (isNumberOfHeadsCorrect(i, j))
                        cellMap[i][j].changeState(3);
                } else if (cellMap[i][j].getPreviousState() == ELEH)
                    cellMap[i][j].changeState(4);
                else if (cellMap[i][j].getPreviousState() == ELET)
                    cellMap[i][j].changeState(2);
            }
        }

    }

    public void save() {

    }

    private void setPreviousStateMap() {
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                cellMap[i][j].updatePrevious();
            }
        }
    }

    private boolean isNumberOfHeadsCorrect(int x, int y) {
        int numberOfElectronHeads = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && j >= 0 && i < xsize && j < ysize && !(i == x && j == y)) {
                    if (cellMap[x - 1][y - 1].getPreviousState() == ELEH)
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
