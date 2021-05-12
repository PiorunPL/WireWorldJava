package logic;

import logic.cells.Cell;
import logic.structures.Structure;

import static logic.cells.CellState.*;

public class Simulation {
    private Cell[][] cellMap;
    private int xsize;
    private int ysize;

    public Simulation(StructMap map) {
        xsize = map.getXsize();
        ysize = map.getYsize();
        cellMap = new Cell[xsize][ysize];
        EmptyCellMap();
        for (int i = 0; i < map.size(); i++) {
            Structure struct = map.getStructure(i);
            int temp1 = struct.getX();
            int temp2 = struct.getY();

            for (int j = 0; j < struct.getXsize(); j++) {
                if (struct.getDirection() == Direction.UP || struct.getDirection() == Direction.DOWN)
                    temp1 = temp1Actualization1(struct, j);
                if (struct.getDirection() == Direction.RIGHT || struct.getDirection() == Direction.LEFT)
                    temp2 = temp2Actualization1(struct, j);
                for (int k = 0; k < struct.getYsize(); k++) {
                    if (struct.getDirection() == Direction.RIGHT || struct.getDirection() == Direction.LEFT)
                        temp1 = temp1Actualization2(struct, j);
                    if (struct.getDirection() == Direction.UP || struct.getDirection() == Direction.DOWN)
                        temp2 = temp2Actualization2(struct, j);

                    if (cellMap[temp1][temp2].getState() == EMPA)
                        cellMap[temp1][temp2] = struct.getCell(j, k);
                    else if (struct.getCell(j, k).getState() == ELEH) {
                        cellMap[temp1][temp2].changeState(ELEH);
                    } else if (struct.getCell(j, k).getState() == ELET) {
                        cellMap[temp1][temp2].changeState(ELET);
                    } else {

                    }
                }
            }


        }
    }

    private int temp1Actualization1(Structure struct, int iteration) {
        int temp1;

        if (struct.getDirection() == Direction.UP) {
            temp1 = struct.getX() + iteration;
        } else if (struct.getDirection() == Direction.RIGHT) {
            temp1 = struct.getX();
        } else if (struct.getDirection() == Direction.DOWN) {
            temp1 = struct.getX() - iteration;
        } else {
            temp1 = struct.getX();
        }

        return temp1;
    }

    private int temp2Actualization1(Structure struct, int iteration) {
        int temp2;

        if (struct.getDirection() == Direction.UP) {
            temp2 = struct.getY();
        } else if (struct.getDirection() == Direction.RIGHT) {
            temp2 = struct.getY() - iteration;
        } else if (struct.getDirection() == Direction.DOWN) {
            temp2 = struct.getY();
        } else {
            temp2 = struct.getY() + iteration;
        }

        return temp2;
    }

    private int temp1Actualization2(Structure struct, int iteration) {
        int temp1;

        if (struct.getDirection() == Direction.UP) {
            temp1 = struct.getX();
        } else if (struct.getDirection() == Direction.RIGHT) {
            temp1 = struct.getX() - iteration;
        } else if (struct.getDirection() == Direction.DOWN) {
            temp1 = struct.getX();
        } else {
            temp1 = struct.getX() + iteration;
        }

        return temp1;
    }

    private int temp2Actualization2(Structure struct, int iteration) {
        int temp2;

        if (struct.getDirection() == Direction.UP) {
            temp2 = struct.getY() + iteration;
        } else if (struct.getDirection() == Direction.RIGHT) {
            temp2 = struct.getY();
        } else if (struct.getDirection() == Direction.DOWN) {
            temp2 = struct.getY() - iteration;
        } else {
            temp2 = struct.getY();
        }

        return temp2;
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
                if (cellMap[i][j].getPreviousState() == EMPN) ;
                else if (cellMap[i][j].getPreviousState() == EMPA) ;
                else if (cellMap[i][j].getPreviousState() == WIRE) {
                    if (isNumberOfHeadsCorrect(i, j))
                        cellMap[i][j].changeState(ELEH);
                } else if (cellMap[i][j].getPreviousState() == ELEH)
                    cellMap[i][j].changeState(ELET);
                else if (cellMap[i][j].getPreviousState() == ELET)
                    cellMap[i][j].changeState(WIRE);
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

    private void EmptyCellMap() {
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                cellMap[i][j] = new Cell(1);
            }
        }
    }
}
