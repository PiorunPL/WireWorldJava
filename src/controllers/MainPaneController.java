package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import logic.Direction;
import logic.Simulation;
import utils.DBops;
import utils.Dialogs;
import logic.CellMap;
import logic.StructMap;
import logic.cells.Cell;
import logic.cells.CellState;
import logic.structures.*;
import org.w3c.dom.css.Rect;
import utils.DBops;
import utils.Dialogs;
import utils.exceptions.IllegalStructurePlacement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import static java.lang.Thread.sleep;
import static logic.cells.CellState.*;

public class MainPaneController implements Initializable {


    private void dragged(MouseEvent e) {
        if (e.getButton().equals(MouseButton.MIDDLE) && e.getTarget() instanceof Rectangle) {
            Rectangle rec = (Rectangle) e.getTarget();
            double posY = (SIZE + BREAK) * GridPane.getRowIndex(rec) - BREAK + 0.5 * SIZE;
            double posX = (SIZE + BREAK) * GridPane.getColumnIndex(rec) - BREAK + 0.5 * SIZE;
            GridPane.getColumnIndex(rec);
            grid.translateXProperty().set(grid.getTranslateX() + e.getX() - posX);
            grid.translateYProperty().set(grid.getTranslateY() + e.getY() - posY);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fxmlRoot = (BorderPane) Main.getMainPane();
        grid = new GridPane();

        // mouse click event
        EventHandler<MouseEvent> mouseClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    if (editable && e.getTarget() instanceof Rectangle) {
                        Rectangle rec = (Rectangle) e.getTarget();
                        int x0 = GridPane.getRowIndex(rec);
                        int y0 = GridPane.getColumnIndex(rec);
                        if (clickedStructure != null) {
                            System.out.println(wireStartPoint);
                            if (clickedStructure instanceof Wire && wireStartPoint == null) {

                                //todo możliwe że coś z tym wire
                                wireStartPoint = new Wire(
                                        GridPane.getRowIndex(rec),
                                        GridPane.getColumnIndex(rec),
                                        Direction.UP,
                                        1
                                );
                            } else {
                                setStructureOnMap(e);
                            }
                        } else {
                            if (rec.getFill().equals(COLOR_OF_EMPTY)) {
                                rec.setFill(COLOR_OF_WIRE);
                                cellMap.getCell(x0, y0).changeState(WIRE);
                            } else if (rec.getFill().equals(COLOR_OF_WIRE)) {
                                rec.setFill(COLOR_OF_EMPTY);
                                cellMap.getCell(x0, y0).changeState(EMPA);
                            }
                        }
                    }
                } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                    if (editable && e.getTarget() instanceof Rectangle) {
                        Rectangle rec = (Rectangle) e.getTarget();
                        int x0 = GridPane.getRowIndex(rec);
                        int y0 = GridPane.getColumnIndex(rec);
                        if (clickedStructure != null && !(clickedStructure instanceof Wire)) {
                            clickedStructure.nextDirection();
                            showStructure(e);
                        }
                        if (rec.getFill().equals(COLOR_OF_WIRE)) {
                            rec.setFill(COLOR_OF_HEAD);
                            cellMap.getCell(x0, y0).changeState(ELEH);
                        } else if (rec.getFill().equals(COLOR_OF_HEAD)) {
                            rec.setFill(COLOR_OF_TAIL);
                            cellMap.getCell(x0, y0).changeState(ELET);
                        } else if (rec.getFill().equals(COLOR_OF_TAIL)) {
                            rec.setFill(COLOR_OF_WIRE);
                            cellMap.getCell(x0, y0).changeState(WIRE);
                        }
                    }
                }
            }
        };
        fxmlRoot.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClick);

        // scrolling event
        EventHandler<ScrollEvent> mouseScroll = new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent e) {
                grid.translateZProperty().set(grid.getTranslateZ() - e.getDeltaY());
            }
        };
        fxmlRoot.addEventFilter(ScrollEvent.SCROLL, mouseScroll);

        // map dragged event
        grid.setOnMouseDragged(event -> dragged(event));

        // mouse entered event
        EventHandler<MouseEvent> mouseEnteredGrid = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (clickedStructure != null && editable) showStructure(e);
            }
        };
        fxmlRoot.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEnteredGrid);

        // key clicked event
        EventHandler<KeyEvent> click = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                switch (e.getCode()) {
                    case W:
                        grid.translateYProperty().set(grid.getTranslateY() + 10);
                        break;
                    case S:
                        grid.translateYProperty().set(grid.getTranslateY() - 10);
                        break;
                    case D:
                        grid.translateXProperty().set(grid.getTranslateX() - 10);
                        break;
                    case A:
                        grid.translateXProperty().set(grid.getTranslateX() + 10);
                        break;
                    case ESCAPE:
                        clickedStructure = null;
                        Rectangle rec;
                        wireStartPoint = null;
                        for (int i = 0; i < (backup != null && backup.tab != null ? backup.tab.length : 0); i++) {
                            for (int j = 0; j < (backup.tab[0] != null ? backup.tab[0].length : 0); j++) {
                                rec = (Rectangle) grid.getChildren().get((backup.x + i) * ysize + backup.y + j);
                                rec.setFill(backup.tab[i][j]);
                            }
                        }
                        break;
                }
            }
        };
        fxmlRoot.addEventFilter(KeyEvent.KEY_PRESSED, click);

        // initialization of grid pane
        drawGrid();

        // setup of grid pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #F9AA33");
        pane.getChildren().add(grid);
        fxmlRoot.setCenter(pane);
        pane.setViewOrder(1);

        grid.toBack();
        grid.setViewOrder(1);
        BorderPane.setMargin(grid, new Insets(BREAK, BREAK, BREAK, BREAK));
    }

    // size and gap in grid pane
    private final double SIZE = 50;
    private final double BREAK = 3;

    // number of rows and columns in grid pane
    private int xsize = 30;
    private int ysize = 30;
    private Structure clickedStructure;

    private Wire wireStartPoint;
    private StructMap map;
    private CellMap cellMap = null;

    private boolean editable = false;
    private File saveFile;
    private Backup backup;

    public static final Color COLOR_OF_WIRE = Color.SILVER;
    public static final Color COLOR_OF_EMPTY = Color.BLACK;
    public static final Color COLOR_OF_HEAD = Color.RED;
    public static final Color COLOR_OF_TAIL = Color.ORANGE;
    public static final Color COLOR_OF_EMPTYNOTAPPENDABLE = Color.rgb(100, 100, 100);

    @FXML
    private GridPane grid;
    @FXML
    private BorderPane fxmlRoot;
    @FXML
    private TextField iterationTextField;
    @FXML
    private TextField timeStepTextField;

    // action buttons

    @FXML
    void edit() {
        try {
            editable = Dialogs.editDialog();

        } catch (IllegalStateException e) {
            editable = false;
            e.printStackTrace();
        }

        if (editable == true)
            simulation = null;
    }

    @FXML
    void help() {
    }

    @FXML
    void newBoard() {
        int[] result = Dialogs.newBoardDialog();
        xsize = result[0];
        ysize = result[1];
        clickedStructure = null;
        editable = false;
        firstAdded = false;
        saveFile = null;
        cellMap = new CellMap(xsize, ysize);
        drawGrid();
    }

    @FXML
    void next() {
        if (editable == true)
            editable = false;

        if (simulation == null) ;
        simulation = new Simulation(cellMap);

        simulation.simulate();
        cellVector = simulation.getCellVectorChanged();

        while (cellVector.size() != 0) {
            Rectangle rec;
            rec = (Rectangle) grid.getChildren().get(cellVector.get(0).getxMap() * ysize + cellVector.get(0).getyMap());
            rec.setFill(cellVector.get(0).getColor());
            cellVector.remove(0);
        }



        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                Rectangle rec;
                rec = (Rectangle) grid.getChildren().get(i * ysize + j);
                Cell cell = cellMap.getCell(i, j);
                rec.setFill(cell.getColor());
            }
        }

    }

    @FXML
    void open() {
        clickedStructure = null;
        editable = false;
        saveFile = null;
        FileChooser fc = new FileChooser();
        //File dir = new File();
        //fc.setInitialDirectory();
        File selected = fc.showOpenDialog(null);

        if (selected != null) {
            cellMap = DBops.getMapFromFile(selected);
            displayMap(cellMap);
        }
    }

    Simulation simulation = null;
    Vector<Cell> cellVector;

    @FXML
    void play() {
        for (int i = 0; i < Integer.parseInt(iterationTextField.getText()); i++) {
            next();
        }
    }

    @FXML
    void previous() {
    }

    @FXML
    void save() {
        if (saveFile == null) {
            FileChooser fc = new FileChooser();
            saveFile = fc.showSaveDialog(null);
        }
        try {
            if (saveFile != null) DBops.saveMapToFile(map, saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveAs() {
        FileChooser fc = new FileChooser();
        saveFile = fc.showSaveDialog(null);

        if (saveFile != null) {
            try {
                DBops.saveMapToFile(map, saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // structure buttons
    @FXML
    void and() {
        clickedStructure = new And();
    }

    @FXML
    void clock() {
        clickedStructure = new Clock();
    }

    @FXML
    void diode() {
        clickedStructure = new Diode();
    }

    @FXML
    void not() {
        clickedStructure = new Not();
    }

    @FXML
    void or() {
        clickedStructure = new Or();
    }

    @FXML
    void wire() {
        clickedStructure = new Wire();
    }

    @FXML
    void xor() {
        clickedStructure = new Xor();
    }

    void displayMap(CellMap map) {
        grid.getChildren().clear();
        xsize = map.getXSize();
        ysize = map.getYSize();

        drawGrid();

        Rectangle rec;
        Cell cell;
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                rec = (Rectangle) grid.getChildren().get(i * ysize + j);
                cell = map.getCell(i, j);
                if (cell.getState().equals(EMPA)) rec.setFill(COLOR_OF_EMPTY);
                else if (cell.getState().equals(CellState.EMPN)) rec.setFill(COLOR_OF_EMPTYNOTAPPENDABLE);
                else if (cell.getState().equals(WIRE)) rec.setFill(COLOR_OF_WIRE);
                else if (cell.getState().equals(ELEH)) rec.setFill(COLOR_OF_HEAD);
                else if (cell.getState().equals(CellState.ELET)) rec.setFill(COLOR_OF_TAIL);

            }
        }
    }

    void drawGrid() {
        if (grid.getChildren() != null) {
            grid.getChildren().clear();
        }

        Rectangle rec;
        grid.setHgap(BREAK);
        grid.setVgap(BREAK);
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                grid.add(rec = new Rectangle(SIZE, SIZE), j, i);
                rec.setFill(COLOR_OF_EMPTY);
            }
        }
        map = new StructMap(xsize, ysize);

    }

    public void showStructure(MouseEvent e) {
        if (e.getTarget() instanceof Rectangle) {
            Rectangle rec = (Rectangle) e.getTarget();

            // coordinates of mouse on the grid
            int xMouse = GridPane.getRowIndex(rec), yMouse = GridPane.getColumnIndex(rec);

            // indexes of structure cells from which drawing structure on grid begins
            int xStart = 0, yStart = 0;

            // size of drawing on the grid
            int xSize, ySize;

            // top left corner of drawn structure
            int x0 = xMouse, y0 = yMouse;

            if (clickedStructure instanceof Wire) {
                if (wireStartPoint != null) clickedStructure = wireStartPoint;

                if (xMouse == clickedStructure.getX()) {
                    ((Wire) clickedStructure).setLength(Math.abs(yMouse - clickedStructure.getY()) + 1);
                    if (yMouse > clickedStructure.getY()) clickedStructure.setDirection(Direction.RIGHT);
                    else clickedStructure.setDirection(Direction.LEFT);
                    xSize = clickedStructure.getXSizeAfterRotation();
                    ySize = clickedStructure.getYSizeAfterRotation();
                    x0 = clickedStructure.getX();
                    y0 = clickedStructure.getDirection().equals(Direction.RIGHT) ?
                            clickedStructure.getY() : yMouse;
                } else if (yMouse == clickedStructure.getY()) {
                    ((Wire) clickedStructure).setLength(Math.abs(xMouse - clickedStructure.getX()) + 1);
                    if (xMouse > clickedStructure.getX()) clickedStructure.setDirection(Direction.DOWN);
                    else clickedStructure.setDirection(Direction.UP);
                    xSize = clickedStructure.getXSizeAfterRotation();
                    ySize = clickedStructure.getYSizeAfterRotation();
                    x0 = clickedStructure.getDirection().equals(Direction.UP) ?
                            xMouse: clickedStructure.getX();
                    y0 = clickedStructure.getY();
                } else {
                    ((Wire) clickedStructure).setLength(1);
                }
            } else {
                x0 = clickedStructure.getDirection().equals(Direction.UP) ||
                        clickedStructure.getDirection().equals(Direction.RIGHT) ?
                        xMouse : xMouse - clickedStructure.getXSizeAfterRotation() + 1;
                y0 = clickedStructure.getDirection().equals(Direction.UP) ||
                        clickedStructure.getDirection().equals(Direction.LEFT) ?
                        yMouse : yMouse - clickedStructure.getYSizeAfterRotation() + 1;
            }

            if ((grid.getRowCount() - clickedStructure.getXSizeAfterRotation() - x0) >= 0) {
                if (x0 < 0) {
                    xStart = Math.abs(x0);
                    x0 = 0;
                }
                xSize = clickedStructure.getXSizeAfterRotation();
            } else xSize = grid.getRowCount() - xMouse;

            if ((grid.getColumnCount() - clickedStructure.getYSizeAfterRotation() - y0) >= 0) {
                if (y0 < 0) {
                    yStart = Math.abs(y0);
                    y0 = 0;
                }
                ySize = clickedStructure.getYSizeAfterRotation();
            } else ySize = grid.getColumnCount() - yMouse;

            System.out.printf("direction: %s\nx0: %d, y0: %d\nxMouse: %d, yMouse: %d\nxStartPoint: %d, yStartPoint: %d\nxStart: %d, yStart: %d\nxSize: %d, ySize: %d\n\n",
                    clickedStructure.getDirection(), x0, y0, xMouse, yMouse, clickedStructure.getX(), clickedStructure.getY(), xStart, yStart, xSize,ySize);

            CellMap cellMap1 = clickedStructure.structureAfterDirection();

            for (int i = 0; i < (backup != null && backup.tab != null ? backup.tab.length : 0); i++) {
                for (int j = 0; j < (backup.tab[0] != null ? backup.tab[0].length : 0); j++) {
                    rec = (Rectangle) grid.getChildren().get((backup.x + i) * ysize + backup.y + j);
                    rec.setFill(backup.tab[i][j]);
                }
            }

            Color[][] tab = new Color[xSize - xStart][ySize - yStart];

            for (int i = xStart; i < xSize; i++) {
                for (int j = yStart; j < ySize; j++) {
                    //System.out.printf("xStart: %d, i: %d, xSize: %d\nyStart: %d, j: %d, ySize: %d\n\n",
                      //      xStart, i, xSize, yStart, j, ySize);
                    Cell cell = cellMap1.getCell(i, j);
                    rec = (Rectangle) grid.getChildren().get((x0 - xStart + i) * ysize + y0 + j - yStart);
                    tab[i - xStart][j - yStart] = (Color) rec.getFill();
                    rec.setFill(cell.getColor());
                }
            }
            backup = new Backup(x0, y0, tab);
        }
    }

    public static boolean firstAdded = false;

    private void setStructureOnMap(MouseEvent e) {
        if (e.getTarget() instanceof Rectangle) {
            CellMap cellMap1 = clickedStructure.structureAfterDirection();
            Rectangle rec = (Rectangle) e.getTarget();

            int xMouse = GridPane.getRowIndex(rec), yMouse = GridPane.getColumnIndex(rec);
            int xStart = 0, yStart = 0;
            int xSize, ySize;

            int x0 = clickedStructure.getDirection().equals(Direction.UP) ||
                    clickedStructure.getDirection().equals(Direction.RIGHT) ?
                    xMouse : xMouse - clickedStructure.getXSizeAfterRotation() + 1;
            int y0 = clickedStructure.getDirection().equals(Direction.UP) ||
                    clickedStructure.getDirection().equals(Direction.LEFT) ?
                    yMouse : yMouse - clickedStructure.getYSizeAfterRotation() + 1;

            if ((grid.getRowCount() - clickedStructure.getXSizeAfterRotation() - x0) >= 0) {
                if (x0 < 0) {
                    xStart = Math.abs(x0);
                    x0 = 0;
                }
                xSize = clickedStructure.getXSizeAfterRotation();
            } else xSize = grid.getRowCount() - xMouse;

            if ((grid.getColumnCount() - clickedStructure.getYSizeAfterRotation() - y0) >= 0) {
                if (y0 < 0) {
                    yStart = Math.abs(y0);
                    y0 = 0;
                }
                ySize = clickedStructure.getYSizeAfterRotation();
            } else ySize = grid.getColumnCount() - yMouse;

            for (int i = xStart; i < xSize; i++) {
                for (int j = yStart; j < ySize; j++) {
                    Cell cell = cellMap1.getCell(i, j);
                    rec = (Rectangle) grid.getChildren().get((x0 - xStart + i) * ysize + y0 + j - yStart);
                    rec.setFill(cell.getColor());
                }
            }

            boolean error = false;
            try {
                StructMap structMap = new StructMap(xsize, ysize);
                clickedStructure.setX(xMouse);
                clickedStructure.setY(yMouse);
                structMap.addStruct(clickedStructure.getName(), xMouse, yMouse, clickedStructure.getDirection(), clickedStructure.getXSize());
                if (firstAdded) {
                    DBops.getMapStructFormat(clickedStructure, cellMap);

                } else {
                    cellMap = DBops.getMapStructFormat(structMap);
                    firstAdded = true;
                }

            } catch (IllegalStructurePlacement illegalStructurePlacement) {
                illegalStructurePlacement.printStackTrace();
                error = true;
            }
            if (error) {
                for (int i = 0; i < (backup != null && backup.tab != null ? backup.tab.length : 0); i++) {
                    for (int j = 0; j < (backup.tab[0] != null ? backup.tab[0].length : 0); j++) {
                        Rectangle rec1;
                        rec1 = (Rectangle) grid.getChildren().get((backup.x + i) * ysize + backup.y + j);
                        rec1.setFill(backup.tab[i][j]);
                    }
                }
            }

            if (map != null && !error) {
                map.addStruct(clickedStructure.getName(), xMouse, yMouse, clickedStructure.getDirection(), clickedStructure.getXSize());
                backup = null;
                createNewStructure(clickedStructure.getName());
            }

            if (clickedStructure instanceof Wire) {
                wireStartPoint = null;
                clickedStructure = new Wire();
            }
        }
    }

    private void createNewStructure(String name) {
        if (name == "or") clickedStructure = new Or();
        else if (name == "and") clickedStructure = new And();
        else if (name == "clock") clickedStructure = new Clock();
        else if (name == "diode") clickedStructure = new Diode();
        else if (name == "not") clickedStructure = new Not();
        else if (name == "wire") clickedStructure = new Wire();
        else if (name == "xor") clickedStructure = new Xor();
        else {

        }

    }
}


class Backup {
    public Backup(int x, int y, Color[][] tab) {
        this.x = x;
        this.y = y;
        this.tab = tab;
    }

    int x;
    int y;
    Color[][] tab;
}

