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
                        if (clickedStructure != null) {
                            if (clickedStructure instanceof Wire && wireStartPoint == null) {
                                wireStartPoint = new Wire(GridPane.getRowIndex(rec), GridPane.getColumnIndex(rec));
                            } else {
                                setStructureOnMap(e);
                            }
                        } else {
                            if (rec.getFill().equals(COLOR_OF_EMPTY)) rec.setFill(COLOR_OF_WIRE);
                            else if (rec.getFill().equals(COLOR_OF_WIRE)) rec.setFill(COLOR_OF_EMPTY);
                        }
                    }
                } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                    if (editable && e.getTarget() instanceof Rectangle) {
                        Rectangle rec = (Rectangle) e.getTarget();
                        if (clickedStructure != null) {
                            clickedStructure.nextDirection();
                            showStructure(e);
                        }
                        if (rec.getFill().equals(COLOR_OF_WIRE)) rec.setFill(COLOR_OF_HEAD);
                        else if (rec.getFill().equals(COLOR_OF_HEAD)) rec.setFill(COLOR_OF_TAIL);
                        else if (rec.getFill().equals(COLOR_OF_TAIL)) rec.setFill(COLOR_OF_WIRE);
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
    public static final Color COLOR_OF_HEAD = Color.ORANGE;
    public static final Color COLOR_OF_TAIL = Color.RED;
    public static final Color COLOR_OF_EMPTYNOTAPPENDABLE = Color.rgb(100,100,100);

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

        drawGrid();
    }

    @FXML
    void next() {
    }

    @FXML
    void open() {
        clickedStructure = null;
        editable = false;
        saveFile = null;
        FileChooser fc = new FileChooser();
        File dir = new File("C:\\Users\\sebas\\Documents\\Studia\\Informatka Stosowana\\2_semestr\\Języki i Metody Programowania 2\\Materiały\\Projekt_2\\WireWorldJava\\test");
        fc.setInitialDirectory(dir);
        File selected = fc.showOpenDialog(null);

        if (selected != null) {
            CellMap map = DBops.getMapFromFile(selected);
            displayMap(map);
        }
    }

    @FXML
    void play() {
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
                if (cell.getState().equals(CellState.EMPA)) rec.setFill(COLOR_OF_EMPTY);
                else if (cell.getState().equals(CellState.EMPN)) rec.setFill(COLOR_OF_EMPTYNOTAPPENDABLE);
                else if (cell.getState().equals(CellState.WIRE)) rec.setFill(COLOR_OF_WIRE);
                else if (cell.getState().equals(CellState.ELEH)) rec.setFill(COLOR_OF_HEAD);
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
        cellMap = new CellMap(xsize, ysize);
    }

    public void showStructure(MouseEvent e) {
        if (e.getTarget() instanceof Rectangle) {
            CellMap cellMap1 = clickedStructure.structureAfterDirection();
            Rectangle rec = (Rectangle) e.getTarget();
            int x0 = GridPane.getRowIndex(rec);
            int y0 = GridPane.getColumnIndex(rec);
            int xSize = (grid.getRowCount() - clickedStructure.getXSizeAfterRotation() - x0) > 0
                    ? clickedStructure.getXSizeAfterRotation() : grid.getRowCount() - x0;
            int ySize = (grid.getColumnCount() - clickedStructure.getYSizeAfterRotation() - y0) > 0
                    ? clickedStructure.getYSizeAfterRotation() : grid.getColumnCount() - y0;

            for (int i = 0; i < (backup != null && backup.tab != null ? backup.tab.length : 0); i++) {
                for (int j = 0; j < (backup.tab[0] != null ? backup.tab[0].length : 0); j++) {
                    rec = (Rectangle) grid.getChildren().get((backup.x + i) * ysize + backup.y + j);
                    rec.setFill(backup.tab[i][j]);
                }
            }

            Color[][] tab = new Color[xSize][ySize];

            if (clickedStructure instanceof Wire) {
                tab[0][0] = (Color) rec.getFill();
                rec = (Rectangle) grid.getChildren().get(x0 * ysize + y0);
                if (wireStartPoint != null && wireStartPoint.getLength() > 0) {
                    int range = 0, i = 0;
                    if (x0 == wireStartPoint.getX()) {
                        range = y0 - wireStartPoint.getY() > 0 ? 0 : wireStartPoint.getY() - y0;
                        i = y0 - wireStartPoint.getY() > 0 ? wireStartPoint.getY() - y0 : 0;
                    } else if (y0 == wireStartPoint.getY()) {
                        range = x0 - wireStartPoint.getX() > 0 ? 0 : wireStartPoint.getX() - x0;
                        i = x0 - wireStartPoint.getX() > 0 ? wireStartPoint.getX() - x0 : 0;
                    }

                    for (; i <= range; i++) {
                        if (x0 == wireStartPoint.getX()) {
                            rec = (Rectangle) grid.getChildren().get(x0 * ysize + y0 + i);
                        } else if (y0 == wireStartPoint.getY()) {
                            rec = (Rectangle) grid.getChildren().get((x0 + i) * ysize + y0);
                        }
                        rec.setFill(COLOR_OF_WIRE);
                    }
                } else {
                    rec.setFill(COLOR_OF_WIRE);
                }
            } else {
                for (int i = 0; i < xSize; i++) {
                    for (int j = 0; j < ySize; j++) {
                        Cell cell = cellMap1.getCell(i, j);
                        rec = (Rectangle) grid.getChildren().get((x0 + i) * ysize + y0 + j);
                        tab[i][j] = (Color) rec.getFill();

                        rec.setFill(cell.getColor());
                    }
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
            int x0 = GridPane.getRowIndex(rec);
            int y0 = GridPane.getColumnIndex(rec);
            int xSize = (grid.getRowCount() - clickedStructure.getXSizeAfterRotation() - x0) > 0
                    ? clickedStructure.getXSizeAfterRotation() : grid.getRowCount() - x0;
            int ySize = (grid.getColumnCount() - clickedStructure.getYSizeAfterRotation() - y0) > 0
                    ? clickedStructure.getYSizeAfterRotation() : grid.getColumnCount() - y0;

            Color[][] tab = new Color[xSize][ySize];

            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    Cell cell = cellMap1.getCell(i, j);
                    Rectangle rec1;
                    rec1 = (Rectangle) grid.getChildren().get((x0 + i) * ysize + y0 + j);
                    tab[i][j] = (Color) rec1.getFill();

                    rec1.setFill(cell.getColor());
                }
            }
            //backup = new Backup(x0, y0, tab);



            boolean error = false;
            try {
                StructMap structMap = new StructMap(xsize, ysize);
                clickedStructure.setX(x0);
                clickedStructure.setY(y0);
                structMap.addStruct(clickedStructure.getName(), x0, y0, clickedStructure.getDirection(), clickedStructure.getXSize());
                if (firstAdded)
                    DBops.getMapStructFormat(clickedStructure, cellMap);
                else {
                    cellMap = DBops.getMapStructFormat(structMap);
                    firstAdded = true;
                }

            } catch (IllegalStructurePlacement illegalStructurePlacement) {
                illegalStructurePlacement.printStackTrace();
                error = true;
            }
            if (error)
            {
                for (int i = 0; i < (backup != null && backup.tab != null ? backup.tab.length : 0); i++) {
                    for (int j = 0; j < (backup.tab[0] != null ? backup.tab[0].length : 0); j++) {
                        Rectangle rec1;
                        rec1 = (Rectangle) grid.getChildren().get((backup.x + i) * ysize + backup.y + j);
                        rec1.setFill(backup.tab[i][j]);
                    }
                }
            }

            if (map != null && !error) {
                map.addStruct(clickedStructure.getName(), x0, y0, clickedStructure.getDirection(), clickedStructure.getXSize());
                backup = null;
            }
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

