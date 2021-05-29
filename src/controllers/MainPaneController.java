package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
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

import javafx.stage.Stage;
import utils.exceptions.IllegalStructurePlacement;

import java.util.Vector;


import logic.Direction;
import logic.Simulation;
import utils.DBops;
import utils.Dialogs;
import logic.CellMap;
import logic.StructMap;
import logic.cells.Cell;
import logic.structures.*;

import static logic.cells.CellState.*;

public class MainPaneController implements Initializable {

    // size and gap in grid pane
    private final double SIZE = 50;
    private final double BREAK = 3;

    // number of rows and columns in grid pane
    private int xSize = 30;
    private int ySize = 30;

    // structures and maps
    private Structure clickedStructure;
    private Wire wireStartPoint;
    private StructMap map;
    private CellMap cellMap = null;
    private Backup backup;
    private Vector<Cell> electronHeadCellVector = null;
    private Vector<Cell> electronTailCellVector = null;

    // logical switches
    private boolean editable = false;
    private boolean deleteStructureSwitch = false;
    public static boolean firstAdded = false;

    // save file
    private File saveFile;

    // colors
    public static final Color COLOR_OF_WIRE = Color.SILVER;
    public static final Color COLOR_OF_EMPA = Color.BLACK;
    public static final Color COLOR_OF_HEAD = Color.RED;
    public static final Color COLOR_OF_TAIL = Color.ORANGE;
    public static final Color COLOR_OF_EMPN = Color.rgb(100, 100, 100);

    // fields related to simulation
    Simulation simulation = null;
    Vector<Cell> cellVector;
    SimThread simThread;
    private int iterations = 100;
    private int timeStep = 1000;

    // fxml objects
    @FXML
    private GridPane grid;
    @FXML
    private BorderPane fxmlRoot;
    @FXML
    private TextField iterationTextField;
    @FXML
    private TextField timeStepTextField;

    // initialize

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
                        int x = GridPane.getRowIndex(rec);
                        int y0 = GridPane.getColumnIndex(rec);
                        if (clickedStructure != null) {
                            if (clickedStructure instanceof Wire && wireStartPoint == null) {
                                wireStartPoint = new Wire(x, y0, Direction.UP, 1);
                            } else {
                                setStructureOnMap(e);
                            }
                        } else if (deleteStructureSwitch) {
                            deleteStructure(e);
                        } else {
                            if (cellMap.getCell(x, y0).getState().equals(EMPA)) {
                                clickedStructure = new Wire(1, 1);
                                setStructureOnMap(e);
                                clickedStructure = null;
                            } else if (cellMap.getCell(x, y0).getState().equals(WIRE)) {
                                Structure structure = cellMap.getCell(x, y0).getStruct();
                                if (structure instanceof Wire && ((Wire) structure).getLength() == 1)
                                    deleteStructure(e);
                            }
                        }
                    }
                } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                    if (editable && e.getTarget() instanceof Rectangle) {
                        Rectangle rec = (Rectangle) e.getTarget();
                        int x = GridPane.getRowIndex(rec);
                        int y = GridPane.getColumnIndex(rec);
                        if (clickedStructure != null && !(clickedStructure instanceof Wire)) {
                            clickedStructure.nextDirection();
                            showStructure(e);
                        }
                        if (cellMap.getCell(x, y).getState().equals(WIRE)) {
                            cellMap.getCell(x, y).changeState(ELEH);
                        } else if (cellMap.getCell(x, y).getState().equals(ELEH)) {
                            cellMap.getCell(x, y).changeState(ELET);
                        } else if (cellMap.getCell(x, y).getState().equals(ELET)) {
                            cellMap.getCell(x, y).changeState(WIRE);
                        }
                        rec.setFill(cellMap.getCell(x, y).getColor());
                    }
                }
            }
        };
        fxmlRoot.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClick);

        // scrolling event
        EventHandler<ScrollEvent> mouseScroll = new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent e) {
                // todo ograniczyć
                grid.translateZProperty().set(grid.getTranslateZ() - e.getDeltaY());
            }
        };
        fxmlRoot.addEventFilter(ScrollEvent.SCROLL, mouseScroll);

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
                        deleteStructureSwitch = false;
                        wireStartPoint = null;
                        if (backup != null) backup.display(grid, ySize);
                        break;
                }
            }
        };
        fxmlRoot.addEventFilter(KeyEvent.KEY_PRESSED, click);

        // map dragged event
        grid.setOnMouseDragged(event -> dragged(event));

        // initialization of grid pane
        cellMap = new CellMap(xSize, ySize);
        map = new StructMap(xSize, ySize);
        drawGrid();

        // setup of grid pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #F9AA33");
        pane.getChildren().add(grid);
        fxmlRoot.setCenter(pane);
        pane.setViewOrder(1);
        BorderPane.setMargin(grid, new Insets(BREAK, BREAK, BREAK, BREAK));
    }

    // action buttons

    @FXML
    void delete() {
        clickedStructure = null;
        deleteStructureSwitch = true;

    }

    @FXML
    void edit() {
        if (simThread == null || (simThread != null && !simThread.isAlive())) {
            try {
                editable = Dialogs.editDialog();
            } catch (IllegalStateException e) {
                editable = false;
            }
        }
    }

    @FXML
    void help() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXMLs/HelpDialog.fxml"));
        try {
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(pane);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newBoard() {
        if (simThread == null || (simThread != null && !simThread.isAlive())) {
            int[] result = Dialogs.newBoardDialog();

            if (result != null) { xSize = result[0]; ySize = result[1]; }
            if (xSize < 0 || ySize < 0) { xSize = 30; ySize = 30; }

            clickedStructure = null;
            editable = false;
            firstAdded = false;
            saveFile = null;
            cellMap = new CellMap(xSize, ySize);
            map = new StructMap(xSize, ySize);
            drawGrid();
        }
    }

    @FXML
    public void next() {
        editable = false;

        if (simulation == null) simulation = new Simulation(cellMap);

        simulation.simulate();
        cellVector = simulation.getCellVectorChanged();

        while (cellVector.size() != 0) {
            Rectangle rec;
            rec = (Rectangle) grid.getChildren().get(cellVector.get(0).getxMap() * ySize + cellVector.get(0).getyMap());
            rec.setFill(cellVector.get(0).getColor());
            cellVector.remove(0);
        }

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Rectangle rec;
                rec = (Rectangle) grid.getChildren().get(i * ySize + j);
                Cell cell = cellMap.getCell(i, j);
                rec.setFill(cell.getColor());
            }
        }
    }

    @FXML
    // todo czy obsłużyć wyjątek
    void open() throws IllegalStructurePlacement {
        if (simThread == null || (simThread != null && !simThread.isAlive())) {
            FileChooser fc = new FileChooser();
            File selected = fc.showOpenDialog(null);

            if (selected != null) {
                map = DBops.getMapFromFile(selected);
                cellMap = DBops.getMapStructFormat(map);
                displayMap(cellMap);
                firstAdded = true;
                clickedStructure = null;
                editable = false;
                saveFile = null;
            }
        }
    }

    @FXML
    void play() {
        if ((iterationTextField.getText()) != null && !iterationTextField.getText().trim().isEmpty()) {
            try {
                iterations = Integer.parseInt(iterationTextField.getText());
            } catch (NumberFormatException e) {
                // inserted value is not a number
            }
            iterationTextField.clear();
        }

        if ((timeStepTextField.getText()) != null && !timeStepTextField.getText().trim().isEmpty()) {
            try {
                timeStep = Integer.parseInt(timeStepTextField.getText());
            } catch (NumberFormatException e) {
                // inserted value is not a number
            }
            timeStepTextField.clear();
        }

        if (simThread == null || (simThread != null && !simThread.isAlive()))
            simThread = new SimThread(this, iterations, timeStep);
    }

    @FXML
    void pause() {
        if (simThread != null && simThread.isAlive()) simThread.interrupt();
    }

    @FXML
    void save() {
        if (simThread == null || (simThread != null && !simThread.isAlive())) {
            if (saveFile == null) {
                FileChooser fc = new FileChooser();
                saveFile = fc.showSaveDialog(null);
                findAllElectrons();
            }
            try {
                if (saveFile != null)
                    DBops.saveMapToFile(map, saveFile, electronHeadCellVector, electronTailCellVector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveAs() {
        if (simThread == null || (simThread != null && !simThread.isAlive())) {
            FileChooser fc = new FileChooser();
            saveFile = fc.showSaveDialog(null);
            findAllElectrons();

            if (saveFile != null) {
                try {
                    DBops.saveMapToFile(map, saveFile, electronHeadCellVector, electronTailCellVector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    // other methods

    void displayMap(CellMap map) {
        grid.getChildren().clear();
        xSize = map.getXSize();
        ySize = map.getYSize();

        drawGrid();

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                ((Rectangle) grid.getChildren().get(i * ySize + j)).setFill(map.getCell(i, j).getColor());
            }
        }
    }

    void drawGrid() {
        if (grid.getChildren() != null) {
            grid.getChildren().clear();
        }

        iterations = 100;
        timeStep = 1000;

        grid.setHgap(BREAK);
        grid.setVgap(BREAK);

        Rectangle rec;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                grid.add(rec = new Rectangle(SIZE, SIZE), j, i);
                rec.setFill(COLOR_OF_EMPA);
            }
        }
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
                    clickedStructure.setDirection(yMouse > clickedStructure.getY() ? Direction.RIGHT : Direction.LEFT);
                    x0 = clickedStructure.getX();
                    y0 = clickedStructure.getDirection().equals(Direction.RIGHT) ? clickedStructure.getY() : yMouse;
                } else if (yMouse == clickedStructure.getY()) {
                    ((Wire) clickedStructure).setLength(Math.abs(xMouse - clickedStructure.getX()) + 1);
                    clickedStructure.setDirection(xMouse > clickedStructure.getX() ? Direction.DOWN : Direction.UP);
                    x0 = clickedStructure.getDirection().equals(Direction.UP) ? xMouse : clickedStructure.getX();
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

            CellMap displayedStructure = clickedStructure.structureAfterDirection();

            if (backup != null) backup.display(grid, this.ySize);

            Color[][] tab = new Color[xSize - xStart][ySize - yStart];
            backup = new Backup(x0, y0, tab);

            for (int i = xStart; i < xSize; i++) {
                for (int j = yStart; j < ySize; j++) {
                    rec = (Rectangle) grid.getChildren().get((x0 - xStart + i) * this.ySize + y0 + j - yStart);
                    backup.collect(i - xStart, j - yStart, (Color) rec.getFill());
                    rec.setFill(displayedStructure.getCell(i, j).getColor());
                }
            }
        }
    }

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
                    rec = (Rectangle) grid.getChildren().get((x0 - xStart + i) * this.ySize + y0 + j - yStart);
                    rec.setFill(cell.getColor());
                }
            }

            boolean error = false;
            try {
                StructMap structMap = new StructMap(this.xSize, this.ySize);
                clickedStructure.setX(xMouse);
                clickedStructure.setY(yMouse);
                DBops.getMapStructFormat(clickedStructure, cellMap);
            } catch (IllegalStructurePlacement illegalStructurePlacement) {
                illegalStructurePlacement.printStackTrace();
                error = true;
            }

            if (error)
                if (backup != null) backup.display(grid, this.ySize);

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
        if (name.equals("or")) clickedStructure = new Or();
        else if (name.equals("and")) clickedStructure = new And();
        else if (name.equals("clock")) clickedStructure = new Clock();
        else if (name.equals("diode")) clickedStructure = new Diode();
        else if (name.equals("not")) clickedStructure = new Not();
        else if (name.equals("wire")) clickedStructure = new Wire();
        else if (name.equals("xor")) clickedStructure = new Xor();
    }

    private void findAllElectrons() {
        electronHeadCellVector = new Vector<Cell>();
        electronTailCellVector = new Vector<Cell>();
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Cell cell = cellMap.getCell(i, j);
                if (cell.getState() == ELEH) {
                    electronHeadCellVector.add(cell);
                } else if (cell.getState() == ELET) {
                    electronTailCellVector.add(cell);
                }
            }
        }
    }

    private void dragged(MouseEvent e) {
        if (e.getButton().equals(MouseButton.MIDDLE) && e.getTarget() instanceof Rectangle) {
            Rectangle rec = (Rectangle) e.getTarget();
            double posY = (SIZE + BREAK) * GridPane.getRowIndex(rec) - BREAK + 0.5 * SIZE;
            double posX = (SIZE + BREAK) * GridPane.getColumnIndex(rec) - BREAK + 0.5 * SIZE;
            grid.translateXProperty().set(grid.getTranslateX() + e.getX() - posX);
            grid.translateYProperty().set(grid.getTranslateY() + e.getY() - posY);
        }
    }

    private void deleteStructure(MouseEvent e) {
        Rectangle rec = (Rectangle) e.getTarget();
        int xMouse = GridPane.getRowIndex(rec), yMouse = GridPane.getColumnIndex(rec);
        Structure struct = cellMap.getCell(xMouse, yMouse).getStruct();

        if (struct == null) return;

        int x = struct.getXSizeAfterRotation();
        int y = struct.getYSizeAfterRotation();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cellMap.setCell(struct.getXAfterRotation() + i, struct.getYAfterRotation() + j, new Cell(4));
            }
        }

        map.removeStructure(struct);
        displayMap(cellMap);
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

    public void display(GridPane grid, int ysize) {
        for (int i = 0; i < (tab != null ? tab.length : 0); i++) {
            for (int j = 0; j < (tab[0] != null ? tab[0].length : 0); j++) {
                Rectangle rec1;
                rec1 = (Rectangle) grid.getChildren().get((x + i) * ysize + y + j);
                rec1.setFill(tab[i][j]);
            }
        }
    }

    public void collect(int x, int y, Color color) {
        tab[x][y] = color;
    }
}

class SimThread extends Thread {
    public SimThread(MainPaneController controller, int iterations, int timeStep) {
        this.controller = controller;
        this.iterations = iterations;
        this.timeStep = timeStep;
        start();
    }

    private final MainPaneController controller;
    private final int iterations;
    private final int timeStep;

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            controller.next();
            try {
                sleep(timeStep);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

