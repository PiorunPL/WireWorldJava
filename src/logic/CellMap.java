package logic;

import logic.cells.Cell;
import java.util.HashMap;


public class CellMap {
    private final HashMap<Integer, HashMap<Integer, Cell>> hashMap;
    private final int xsize;
    private final int ysize;

    public CellMap(int xsize, int ysize) {
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

    /**
     * Zmienia rozmiar mapy o podane wartości
     *
     * @param newX ilość wierszy nowej planszy
     * @param newY ilość kolumn nowej planszy
     */
    public static CellMap changeSize(CellMap map, int newX, int newY) {
        CellMap newMap = new CellMap(newX, newY);
        /*for(int i = 0; i < map.getXSize(); i++){
            Cell[] aMap = map[i];
            int aLength = aMap.length;

            System.arraycopy(map[i], 0, newMap[i], 0, map.getYSize());
            for(int j = 0; j < newMap[i].length; j++){
                if( newMap[i][j] == null)
                    newMap[i][j] = new Cell(4);
            }*/
        for (int i = 0; i < map.getXSize(); i++) {
            for (int j = 0; j < map.getYSize(); j++) {
                newMap.setCell(i, j, map.getCell(i, j));
            }
        }

        for (int i = 0; i < newMap.getXSize(); i++) {
            for (int j = 0; j < newMap.getYSize(); j++) {
                if (newMap.getCell(i, j) == null) {
                    newMap.setCell(i, j, new Cell(4));
                }
            }

            /*for(int j = 0; j < map[i].length; j++){
                newMap[i][j] = map[i][j];
            }*/
        }
        return newMap;
    }
}

