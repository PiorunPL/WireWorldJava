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
     * Zmienai rozmiar mapy o podane wartości
     * @param x ilość wierszy do dodania
     * @param y ilość kolumn do dodania
     */
    public void changeSize(int x, int y){

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
