package logic.cells;

public class Cell {
    public Cell(int i) { changeState(i); }

    private  CellState state;

    public void changeState(int i) {
        if (i == CellState.EMPN) state = new EmptyCell(false);
        else if (i == CellState.EMPA) state = new EmptyCell(true);
        else if (i == CellState.WIRE) state = new WireCell();
        else if (i == CellState.ELEH) state = new ElectronHead();
        else if (i == CellState.ELET) state = new ElectronTail();
        else throw new IllegalArgumentException();
    }
}
