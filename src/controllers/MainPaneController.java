package controllers;

import javafx.event.ActionEvent;
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
import javafx.stage.Stage;
import logic.CellMap;
import logic.StructMap;
import logic.cells.Cell;
import logic.cells.CellState;
import logic.structures.*;
import org.w3c.dom.css.Rect;
import utils.DBops;
import utils.Dialogs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPaneController implements Initializable {

    private void dragged(MouseEvent e) {
        if (e.getButton().equals(MouseButton.MIDDLE) && e.getTarget() instanceof Rectangle) {
            Rectangle rec = (Rectangle) e.getTarget();
            double posY = (SIZE + BREAK) * GridPane.getRowIndex(rec) - BREAK + 0.5 * SIZE;
            double posX = (SIZE + BREAK) * GridPane.getColumnIndex(rec) -  BREAK + 0.5 * SIZE;
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
                if (e.getTarget() instanceof Rectangle) {
                    Rectangle rec = (Rectangle) e.getTarget();
                }
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    Structure s = getClickedStructure();
                    if (editable && e.getTarget() instanceof Rectangle) {
                        Rectangle rec = (Rectangle) e.getTarget();
                        if (s != null) {
                            if (s instanceof Wire && wireStartPoint == null) {
                                wireStartPoint = new Wire(GridPane.getRowIndex(rec), GridPane.getColumnIndex(rec));
                            } else { setStuctureOnMap(s, e); }
                        } else {
                            if (rec.getFill().equals(COLOR_OF_EMPTY)) rec.setFill(COLOR_OF_WIRE);
                            else if (rec.getFill().equals(COLOR_OF_WIRE)) rec.setFill(COLOR_OF_EMPTY);
                        }
                    }
                } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                    if (editable && e.getTarget() instanceof Rectangle) {
                        Rectangle rec = (Rectangle) e.getTarget();
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
                Structure s = getClickedStructure();
                if (s != null && editable) showStructure(s, e);
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
                        setAllClicksToFalse();
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

        //setGridPos(Main.getStage(), "x");
        //setGridPos(Main.getStage(), "y");


        // tracking current scene size
        //primaryStage.widthProperty().addListener(e -> { setGridPos(grid, primaryStage, "x"); });
        //primaryStage.heightProperty().addListener(e -> { setGridPos(grid, primaryStage, "y"); });
    }

    // TODO: poprawić działanie tego
    private void setGridPos(Stage stage, String dimension) {
        double ratio;
        double board;
        if (dimension.equals("x")) {
            board = Main.getSceneSizeX() - 2 * OFFSET_X;
            ratio = grid.getTranslateX() / board;
            Main.setSceneSizeX(stage.getWidth());
            grid.translateXProperty().set(grid.getTranslateX() + Main.getSceneSizeX() * ratio);
        } else if (dimension.equals("y")) {
            board = Main.getSceneSizeY() - OFFSET_Y;
            ratio = grid.getTranslateY() / board;
            Main.setSceneSizeY(stage.getHeight());
            grid.translateYProperty().set(grid.getTranslateY() + Main.getSceneSizeY() * ratio);
        }
    }

    // offset between BorderPane.center coordinates and scene coordinates
    private final double OFFSET_X = 142;
    private final double OFFSET_Y = 40;

    // size and gap in grid pane
    private final double SIZE = 50;
    private final double BREAK = 3;

    // number of rows and columns in grid pane
    private int xsize = 100;
    private int ysize = 100;

    private boolean wireClicked = false;
    private boolean diodeClicked = false;
    private boolean clockClicked = false;
    private boolean andClicked = false;
    private boolean notClicked = false;
    private boolean orClicked = false;
    private boolean xorClicked = false;

    private Wire wireStartPoint;
    private StructMap map;

    private boolean editable = false;
    private File saveFile;
    private Backup backup;

    public static final Color COLOR_OF_WIRE = Color.SILVER;
    public static final Color COLOR_OF_EMPTY = Color.BLACK;
    public static final Color COLOR_OF_HEAD = Color.ORANGE;
    public static final Color COLOR_OF_TAIL = Color.RED;

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
    void edit(ActionEvent event) {
        try {
            editable = Dialogs.editDialog();
        } catch (IllegalStateException e) {
            editable = false;
            e.printStackTrace();
        }
    }

    @FXML
    void help(ActionEvent event) {
    }

    @FXML
    void newBoard(ActionEvent event) {
        int[] result = Dialogs.newBoardDialog();
        xsize = result[0];
        ysize = result[1];
        setAllClicksToFalse();
        editable = false;

        drawGrid();
    }

    @FXML
    void next(ActionEvent event) {
    }

    @FXML
    void open(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File dir = new File("C:\\Users\\sebas\\Documents\\Studia\\Informatka Stosowana\\2_semestr\\Języki i Metody Programowania 2\\Materiały\\Projekt_2\\WireWorldJava\\test");
        fc.setInitialDirectory(dir);
        File selected = fc.showOpenDialog(null);

        if (selected != null) {
            CellMap map = DBops.getMapFromFile(selected);
            displayMap(map);
        } else {
            // TODO: handle, maybe dialog window
        }
    }

    @FXML
    void play(ActionEvent event) {
    }

    @FXML
    void previous(ActionEvent event) {
    }

    @FXML
    void save(ActionEvent event) {
        if (saveFile == null) {
            FileChooser fc = new FileChooser();
            saveFile = fc.showSaveDialog(null);
        }
        try {
            DBops.saveMapToFile(new StructMap(xsize, ysize), null, saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveAs(ActionEvent event) {
        FileChooser fc = new FileChooser();
        saveFile = fc.showSaveDialog(null);

        if (saveFile != null) {
            try {
                DBops.saveMapToFile(gridToMap(), null, saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // handle
        }
    }

    // structure buttons

    @FXML
    void and(ActionEvent event) {
        setAllClicksToFalse();
        andClicked = true;
    }

    @FXML
    void clock(ActionEvent event) {
        setAllClicksToFalse();
        clockClicked = true;
    }

    @FXML
    void diode(ActionEvent event) {
        setAllClicksToFalse();
        diodeClicked = true;
    }

    @FXML
    void not(ActionEvent event) {
        setAllClicksToFalse();
        notClicked = true;
    }

    @FXML
    void or(ActionEvent event) {
        setAllClicksToFalse();
        orClicked = true;
    }

    @FXML
    void wire(ActionEvent event) {
        setAllClicksToFalse();
        wireClicked = true;
    }

    @FXML
    void xor(ActionEvent event) {
        setAllClicksToFalse();
        xorClicked = true;
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
                if (cell.getState().equals(CellState.EMPA) ||
                    cell.getState().equals(CellState.EMPN)) rec.setFill(COLOR_OF_EMPTY);
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
        for (int i = 0; i < xsize; i++)
            for (int j = 0; j < ysize; j++) {
                grid.add(rec = new Rectangle(SIZE, SIZE), j, i);
                rec.setFill(COLOR_OF_EMPTY);
            }
        map = new StructMap(xsize, ysize);
    }

    public StructMap gridToMap() {
        return new StructMap(xsize, ysize);
    }

    public void setAllClicksToFalse() {
        wireClicked = false;
        diodeClicked = false;
        clockClicked = false;
        andClicked = false;
        notClicked = false;
        orClicked = false;
        xorClicked = false;
    }

    public Structure getClickedStructure() {
        if (wireClicked) return new Wire();
        else if (diodeClicked) return new Diode();
        else if (clockClicked) return new Clock();
        else if (andClicked) return new And();
        else if (notClicked) return new Not();
        else if (orClicked) return new Or();
        else if (xorClicked) return new Xor();
        else return null;
    }

    public void showStructure(Structure s, MouseEvent e) {
        if (e.getTarget() instanceof Rectangle) {
            Rectangle rec = (Rectangle) e.getTarget();
            int x0 = GridPane.getRowIndex(rec);
            int y0 = GridPane.getColumnIndex(rec);
            int xSize = (grid.getRowCount() - s.getXSize() - x0) > 0 ? s.getXSize() : grid.getRowCount() - x0;
            int ySize = (grid.getColumnCount() - s.getYSize() - y0) > 0 ? s.getYSize() : grid.getColumnCount() - y0;

            for (int i = 0; i < (backup != null && backup.tab != null ? backup.tab.length : 0); i++) {
                for (int j = 0; j < (backup.tab[0] != null ? backup.tab[0].length : 0); j++) {
                    rec = (Rectangle) grid.getChildren(). get((backup.x + i) * ysize + backup.y + j);
                    rec.setFill(backup.tab[i][j]);
                }
            }

            Color[][] tab = new Color[xSize][ySize];

            if (s instanceof Wire) {
                tab[0][0] = (Color) rec.getFill();
                rec = (Rectangle) grid.getChildren().get(x0 * ysize + y0);
                if (wireStartPoint != null && wireStartPoint.getLength() > 0){
                    int range = 0, i = 0;
                    if (x0 == wireStartPoint.getX()) {
                        range = y0 - wireStartPoint.getY() > 0 ? 0 : wireStartPoint.getY() - y0;
                        i = y0 - wireStartPoint.getY() > 0 ? wireStartPoint.getY() - y0 : 0;
                    } else if (y0 == wireStartPoint.getY()) {
                        range = x0 - wireStartPoint.getX() > 0 ? 0 : wireStartPoint.getX() - x0;
                        i = x0 - wireStartPoint.getX() > 0 ? wireStartPoint.getX() - x0 : 0;
                    }

                    System.out.printf("anchor: (%d, %d), range: %d, i: %d\n",wireStartPoint.getX(), wireStartPoint.getY(), range, i);

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
                        Cell cell = s.getCell(i, j);
                        rec = (Rectangle) grid.getChildren().get((x0 + i) * ysize + y0 + j);
                        tab[i][j] = (Color) rec.getFill();

                        rec.setFill(cell.getColor());
                    }
                }
            }
            backup = new Backup(x0, y0, tab);
        }
    }

    private void setStuctureOnMap(Structure s, MouseEvent e) {
        if (e.getTarget() instanceof Rectangle) {
            Rectangle rec = (Rectangle) e.getTarget();
            int x0 = GridPane.getRowIndex(rec);
            int y0 = GridPane.getColumnIndex(rec);
            int xSize = (grid.getRowCount() - s.getXSize() - x0) > 0 ? s.getXSize() : grid.getRowCount() - x0;
            int ySize = (grid.getColumnCount() - s.getXSize() - y0) > 0 ? s.getYSize() : grid.getColumnCount() - y0;

            Color[][] tab = new Color[xSize][ySize];

            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    Cell cell = s.getCell(i, j);
                    rec = (Rectangle) grid.getChildren().get((x0 + i) * ysize + y0 + j);

                    rec.setFill(cell.getColor());
                    tab[i][j] = (Color) rec.getFill();
                }
            }
            backup = new Backup(x0, y0, tab);
            if (map != null) map.addStruct(s.getName(), x0, y0, s.getDirection(), s.getXSize());
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