
package logic.cells;

import controllers.Main;
import controllers.MainPaneController;
import javafx.scene.paint.Color;

import java.awt.*;

import static logic.cells.CellState.*;

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
    private int xMap;
    private int yMap;

    public Color getColor() { return color; }
    public CellState getState() { return state; }
    public CellState getPreviousState() { return previousState; }

    public int getxMap() {
        return xMap;
    }

    public void setxMap(int xMap) {
        this.xMap = xMap;
    }

    public void setyMap(int yMap) {
        this.yMap = yMap;
    }

    public int getyMap() {
        return yMap;
    }

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
        if (state1 == WIRE) color = MainPaneController.COLOR_OF_WIRE;
        else if (state1 == ELET) color = MainPaneController.COLOR_OF_TAIL;
        else if (state1 == ELEH) color = MainPaneController.COLOR_OF_HEAD;
        else if (state1 == EMPA) color = MainPaneController.COLOR_OF_EMPTY;
        else if (state1 == EMPN) color = MainPaneController.COLOR_OF_EMPTYNOTAPPENDABLE;
        else throw new IllegalArgumentException();
    }

    public void updatePrevious()
    {
        this.previousState = this.state;
    }
}

