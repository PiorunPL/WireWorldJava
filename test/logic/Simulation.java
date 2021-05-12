package logic;

import logic.cells.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static logic.cells.CellState.EMPA;


public class Simulation {

    @Test
    public void isNumberOfHeadsCorrectForCentralCell()
    {
        Cell[][] cellMap = new Cell[3][3];
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                cellMap[i][j] = new Cell(0);
                cellMap[i][j].changeState(0);
            }
        }

        cellMap[0][0].changeState(3);
        cellMap[0][2].changeState(3);
        cellMap[1][0].changeState(3);


    }
}
