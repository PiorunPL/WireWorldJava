package logic;

import logic.cells.Cell;

public class CellMap {
    private Cell[][] map = null;
    private int xSize, ySize;

    public CellMap(int xSize, int ySize) throws NegativeArraySizeException {
        try {
            this.xSize = xSize;
            this.ySize = ySize;
            map = new Cell[xSize][ySize];
            emptyCellMap();
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }catch(NegativeArraySizeException e){
            throw new NegativeArraySizeException();
        }
    }

    public Cell getCell(int x, int y) {
        Cell cell;
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

    public int getXSize(){
        return xSize;
    }

    public int getYSize(){
        return ySize;
    }

    private void emptyCellMap() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                map[i][j] = new Cell(4);
                map[i][j].setXMap(i);
                map[i][j].setYMap(j);
            }
        }
    }
}
