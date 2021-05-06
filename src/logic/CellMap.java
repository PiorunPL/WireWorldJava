package logic;

import logic.cells.Cell;

public class CellMap {
    private Cell map[][] = null;

    public CellMap(int xsize, int ysize) throws NegativeArraySizeException {
        try {
            map = new Cell[xsize][ysize];
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
            //cell pozostaje nullem
        }
        return cell;
    }

    public void setCell(int type, int x, int y) {
        try {
            map[x][y] = new Cell(type);
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    /**
     * Zmienai rozmiar mapy o podane wartości
     * @param x ilość wierszy do dodania
     * @param y ilość kolumn do dodania
     */
    public void changeSize(int x, int y){

    }
}
