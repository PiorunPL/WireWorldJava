
package logic.cells;

import controllers.Main;
import controllers.MainPaneController;
import javafx.scene.paint.Color;

import java.awt.*;

public class Cell {
    public Cell(int i) {
        try {
            changeState(i);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private CellState state;
    private CellState previousState;
    private Color color;

    public Color getColor() { return color; }
    public CellState getState() { return state; }
    public CellState getPreviousState() { return previousState; }

    public void changeState(int i) {
        if (i == 1) color = MainPaneController.COLOR_OF_WIRE;
        else if (i == 2) color = MainPaneController.COLOR_OF_TAIL;
        else if (i == 3) color = MainPaneController.COLOR_OF_HEAD;
        else if (i == 4) color = MainPaneController.COLOR_OF_EMPTY;
        else if (i == 5) color = MainPaneController.COLOR_OF_EMPTYNOTAPPENDABLE;
        else throw new IllegalArgumentException();
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