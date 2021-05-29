package logic;

import logic.cells.Cell;
import logic.cells.CellState;

import static logic.cells.CellState.*;

public class CellMap {
    private Cell map[][] = null;
    private int xsize, ysize;

    public CellMap(int xsize, int ysize) throws NegativeArraySizeException {
        try {
            this.xsize = xsize;
            this.ysize = ysize;
            map = new Cell[xsize][ysize];
            emptyCellMap();
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }catch(NegativeArraySizeException e){
            throw new NegativeArraySizeException();
        }
    }

    public Cell getCell(int x, int y) {
        Cell cell = null;
        try{
            cell = map[x][y];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
        return cell;
    }

    public void setCell(int x, int y, Cell cell)
    {
        map[x][y] = cell;
    }

    public void setCellState(int type, int x, int y) {
        try {
            map[x][y].changeState(type);
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public int getXSize(){
        return xsize;
    }

    public int getYSize(){
        return ysize;
    }

    /**
     * Zmienia rozmiar mapy o podane wartości
     * @param newX ilość wierszy nowej planszy
     * @param newY ilość kolumn nowej planszy
     */
    public static CellMap changeSize(CellMap map, int newX, int newY){
        CellMap newMap = new CellMap(newX, newY);
        /*for(int i = 0; i < map.getXSize(); i++){
            Cell[] aMap = map[i];
            int aLength = aMap.length;

            System.arraycopy(map[i], 0, newMap[i], 0, map.getYSize());
            for(int j = 0; j < newMap[i].length; j++){
                if( newMap[i][j] == null)
                    newMap[i][j] = new Cell(4);
            }*/
        for(int i = 0; i < map.getXSize(); i++) {
            for (int j = 0; j < map.getYSize(); j++) {
                newMap.setCell(i, j, map.getCell(i, j));
            }
        }

        for(int i = 0; i < newMap.getXSize(); i++){
            for(int j = 0; j< newMap.getYSize(); j++){
                if(newMap.getCell(i,j) == null){
                    newMap.setCell(i,j, new Cell(4));
                }
            }

            /*for(int j = 0; j < map[i].length; j++){
                newMap[i][j] = map[i][j];
            }*/
        }
        return newMap;
    }

    //TUTAJ ZMIENIĆ TYP CELLA JAK ZMIENI SIĘ W CELL STATE!!!
    private void emptyCellMap() {
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                map[i][j] = new Cell(4);
                map[i][j].setxMap(i);
                map[i][j].setyMap(j);
            }
        }
    }
}
