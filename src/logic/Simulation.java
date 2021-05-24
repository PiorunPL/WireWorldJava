package logic;

import logic.cells.Cell;
import logic.structures.Structure;

import java.util.Vector;

import static logic.cells.CellState.*;

public class Simulation {
    private CellMap cellMap;
    private Vector<Cell> cellVectorChanged;

    public CellMap getCellMap() {
        return cellMap;
    }

    public Simulation(CellMap map) {
        cellMap = map;
        cellVectorChanged = new Vector<Cell>();
    }

    public void simulate(int iterations) {
        for (int i = 0; i < iterations; i++) {
            simulate();
        }
        save();
    }

    //TODO Zmiana symulacji z tabeli na jakąś inna strukturę? coś co będzie trzymało tylko electronhead, electrontail i wire (możliwe ze równiez notappendable)
    //DONE Zapisywanie zaktualizowanych (Nowych takich, których wcześniejszy stan był inny) do wektora (lub jakiejs innej struktury)
    public void simulate() {
        setPreviousStateMap();
        cleanCellVectorChanged();
        for (int i = 0; i < cellMap.getXSize(); i++) {
            for (int j = 0; j < cellMap.getYSize(); j++) {
                if (cellMap.getCell(i, j).getPreviousState() == EMPN) ;
                else if (cellMap.getCell(i, j).getPreviousState() == EMPA) ;
                else if (cellMap.getCell(i, j).getPreviousState() == WIRE) {
                    if (isNumberOfHeadsCorrect(i, j)) {
                        cellMap.getCell(i, j).changeState(ELEH);
                        cellVectorChanged.add(cellMap.getCell(i, j));
                    }
                } else if (cellMap.getCell(i, j).getPreviousState() == ELEH) {
                    cellMap.getCell(i, j).changeState(ELET);
                    cellVectorChanged.add(cellMap.getCell(i, j));
                } else if (cellMap.getCell(i, j).getPreviousState() == ELET) {
                    cellMap.getCell(i, j).changeState(WIRE);
                    cellVectorChanged.add(cellMap.getCell(i, j));
                }
            }
        }
    }

    public void save() {

    }

    private void setPreviousStateMap() {
        for (int i = 0; i < cellMap.getXSize(); i++) {
            for (int j = 0; j < cellMap.getYSize(); j++) {
                cellMap.getCell(i, j).updatePrevious();
            }
        }
    }

    private boolean isNumberOfHeadsCorrect(int x, int y) {
        int numberOfElectronHeads = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && j >= 0 && i < cellMap.getXSize() && j < cellMap.getYSize() && !(i == x && j == y)) {
                    if (cellMap.getCell(i, j).getPreviousState() == ELEH)
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

    public Vector<Cell> getCellVectorChanged() {
        return cellVectorChanged;
    }

    public void cleanCellVectorChanged() {
        while (cellVectorChanged.size() != 0) {
            cellVectorChanged.remove(0);
        }
    }
}
