
package logic.cells;

public class Cell {
    public Cell(int i) { changeState(i); }

    private CellState state;
    private CellState previousState;

    public CellState getState() { return state; }
    public CellState getPreviousState() { return previousState; }

    public void changeState(int i) {
        this.state = CellState.setCellState(i);
    }

    public void changeState(CellState state1) {
        this.state = state1;
    }

    public void updatePrevious()
    {
        this.previousState = this.state;
    }
}