package logic.structures;

import logic.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrTest {

    @Test
    public void initializationOfOrClass() {
        Or or = new Or(1, 1, Direction.setDirection("r"));
        System.out.println(or.map[0][0].getClass().getName());
    }

}