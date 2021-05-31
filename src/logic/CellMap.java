package logic;

import logic.cells.Cell;
import logic.cells.CellState;

import java.util.HashMap;


public class CellMap {
    private HashMap<Integer, HashMap<Integer, Cell>> hashMap;
    private int xsize, ysize;

    public CellMap(int xsize, int ysize) throws NegativeArraySizeException {
        this.xsize = xsize;
        this.ysize = ysize;
        hashMap = new HashMap<>();
    }

    public Cell getCell(int x, int y) {
        Cell cell;
        if (!hashMap.containsKey(x)) {
            cell = new Cell(4);
        } else if (!hashMap.get(x).containsKey(y)) {
            cell = new Cell(4);
        } else {
            cell = hashMap.get(x).get(y);
        }

        return cell;
    }

    public void setCell(int x, int y, Cell cell) {
        if (!hashMap.containsKey(x)) {
            hashMap.put(x, new HashMap<>());
            hashMap.get(x).put(y, cell);
        } else if (!hashMap.get(x).containsKey(y)) {
            hashMap.get(x).put(y, cell);
        } else {
            hashMap.get(x).remove(y);
            hashMap.get(x).put(y, cell);
        }
    }

    public void removeCell(int x, int y) {
        if (!hashMap.containsKey(x)) {
        } else if (!hashMap.get(x).containsKey(y)) {
        } else {
            hashMap.get(x).remove(y);
        }
    }

    public void setCellState(int type, int x, int y) {
        if (!hashMap.containsKey(x)) {
            hashMap.put(x, new HashMap<>());
            hashMap.get(x).put(y, new Cell(type));
        } else if (!hashMap.get(x).containsKey(y)) {
            hashMap.get(x).put(y, new Cell(type));
        } else {
            hashMap.get(x).get(y).changeState(type);
        }
    }

    public int getXSize() {
        return xsize;
    }

    public int getYSize() {
        return ysize;
    }
}


