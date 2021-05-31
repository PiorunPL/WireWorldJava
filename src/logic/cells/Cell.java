
package logic.cells;

import gui.controllers.MainPaneController;
import javafx.scene.paint.Color;
import logic.structures.Structure;
import utils.exceptions.IllegalCellException;

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
    private Structure struct = null;

    public Color getColor() { return color; }

    public Structure getStruct() {
        return struct;
    }

    public void setStruct(Structure struct) {
        this.struct = struct;
    }

    public CellState getState() { return state; }
    public CellState getPreviousState() { return previousState; }

    public int getXMap() {
        return xMap;
    }

    public void setXMap(int xMap) {
        this.xMap = xMap;
    }

    public void setYMap(int yMap) {
        this.yMap = yMap;
    }

    public int getYMap() {
        return yMap;
    }

    public void changeState(int i) {
        if (i == 1) color = MainPaneController.COLOR_OF_WIRE;
        else if (i == 2) color = MainPaneController.COLOR_OF_TAIL;
        else if (i == 3) color = MainPaneController.COLOR_OF_HEAD;
        else if (i == 4) color = MainPaneController.COLOR_OF_EMPA;
        else if (i == 5) color = MainPaneController.COLOR_OF_EMPN;
        else throw new IllegalArgumentException();
        try {
            this.state = CellState.setCellState(i);
        } catch (IllegalCellException e) {
            e.printStackTrace();
        }
    }

    public void changeState(CellState state1) {
        this.state = state1;
        if (state1 == WIRE) color = MainPaneController.COLOR_OF_WIRE;
        else if (state1 == ELET) color = MainPaneController.COLOR_OF_TAIL;
        else if (state1 == ELEH) color = MainPaneController.COLOR_OF_HEAD;
        else if (state1 == EMPA) color = MainPaneController.COLOR_OF_EMPA;
        else if (state1 == EMPN) color = MainPaneController.COLOR_OF_EMPN;
        else throw new IllegalArgumentException();
    }

    public void updatePrevious()
    {
        this.previousState = this.state;
    }
}

