package logic.structures;

import logic.CellMap;
import logic.Direction;
import logic.cells.Cell;

public abstract class Structure {
    protected String name;
    protected int x, y;
    protected Direction direction;
    protected Cell[][] structure;
    protected int xSize, ySize;

    public void nextDirection() {
        if (direction == Direction.UP) direction = Direction.RIGHT;
        else if (direction == Direction.RIGHT) direction = Direction.DOWN;
        else if (direction == Direction.DOWN) direction = Direction.LEFT;
        else if (direction == Direction.LEFT) direction = Direction.UP;
        else throw new IllegalStateException();
    }

    public void setDirection(Direction direction) { this.direction = direction; }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public Cell getCell(int x, int y) {
        if (y < structure[0].length && x < structure.length) return structure[x][y];
        else return null;

    }

    public static Structure isStructure(String name, UsersStructuresContainer container, int x, int y, Direction direction, int length) {
        if (name.equals("and")) return new And(x, y, direction);
        else if (name.equals("clock")) return new Clock(x, y, direction);
        else if (name.equals("diode")) return new Diode(x, y, direction);
        else if (name.equals("not")) return new Not(x, y, direction);
        else if (name.equals("or")) return new Or(x, y, direction);
        else if (name.equals("wire") && length > 0) return new Wire(x, y, direction, length);
        else if (name.equals("xor")) return new Xor(x, y, direction);
        else {
            if (container == null) return null;
            for (int i = 0; i < container.size(); i++) {
                if (name.equals(container.get(i).getName())) return container.get(i).clone(x, y, direction);
            }
            return null;
        }
    }

    public CellMap structureAfterDirection() {
        CellMap cellMap;

        if (direction == Direction.UP || direction == Direction.DOWN) {
            cellMap = new CellMap(xSize, ySize);
        } else {
            cellMap = new CellMap(ySize, xSize);
        }

        int temp1;
        int temp2;

        if (direction == Direction.UP) {
            for (int j = 0; j < xSize; j++) {
                temp1 = j;
                for (int k = 0; k < ySize; k++) {
                    temp2 = k;

                    cellMap.setCell(temp1, temp2, structure[j][k]);

                }
            }
        } else if (direction == Direction.RIGHT) {
            for (int j = 0; j < xSize; j++) {
                temp2 = xSize - 1 - j;
                for (int k = 0; k < ySize; k++) {
                    temp1 = k;

                    cellMap.setCell(temp1, temp2, structure[j][k]);
                }
            }
        } else if (direction == Direction.DOWN) {
            for (int j = 0; j < xSize; j++) {
                temp1 = xSize - 1 - j;
                for (int k = 0; k < ySize; k++) {
                    temp2 = ySize - 1 - k;

                    cellMap.setCell(temp1, temp2, structure[j][k]);
                }
            }
        } else {
            for (int j = 0; j < xSize; j++) {
                temp2 = j;
                for (int k = 0; k < ySize; k++) {
                    temp1 = ySize - 1 - k;

                    cellMap.setCell(temp1, temp2, structure[j][k]);
                }
            }
        }
        return cellMap;
    }

    public int getXAfterRotation() {
        if (direction == Direction.UP) {
            return x;
        } else if (direction == Direction.RIGHT) {
            return x;
        } else if (direction == Direction.DOWN) {
            return x - (xSize - 1);
        } else {
            return x - (ySize - 1);
        }
    }

    public int getYAfterRotation() {
        if (direction == Direction.UP) {
            return y;
        } else if (direction == Direction.RIGHT) {
            return y - (xSize - 1);
        } else if (direction == Direction.DOWN) {
            return y - (ySize - 1);
        } else {
            return y;
        }
    }

    public int getXSizeAfterRotation() {
        if (direction == Direction.UP || direction == Direction.DOWN)
            return xSize;
        else
            return ySize;
    }

    public int getYSizeAfterRotation() {
        if (direction == Direction.UP || direction == Direction.DOWN)
            return ySize;
        else
            return xSize;
    }


}


