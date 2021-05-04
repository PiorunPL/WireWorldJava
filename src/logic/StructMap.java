package logic;

import logic.cells.Cell;

public class StructMap {
    private Cell cellsMap[][] = null;

    public StructMap() {

    }

    public StructMap(int xsize, int ysize) throws NegativeArraySizeException {
        try {
            cellsMap = new Cell[xsize][ysize];
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }catch(NegativeArraySizeException e){
            throw new NegativeArraySizeException();
        }
    }

    public Cell getCell(int x, int y) {
        Cell cell = null;
        try{
            cell = cellsMap[x][y];
        }catch(ArrayIndexOutOfBoundsException e){
            //cell pozostaje nullem
        }
        return cell;
    }

    public void setCell(Cell cell, int x, int y) {
        try {
            cellsMap[x][y] = cell;
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

}
