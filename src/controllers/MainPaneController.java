package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.CellMap;
import logic.StructMap;
import logic.cells.Cell;
import logic.cells.CellState;
import logic.structures.*;
import utils.DBops;
import utils.Dialogs;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPaneController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fxmlRoot = (BorderPane) Main.getMainPane();
        grid = new GridPane();

        grid.toBack();
        grid.setViewOrder(1);

        // mouse click event
        EventHandler<MouseEvent> mouseClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.printf("Mouse entered %d, %s\n", (int) e.getSceneX(), e.getTarget());
                if (editable && e.getTarget() instanceof Rectangle) {
                    Rectangle rec = (Rectangle) e.getTarget();
                    if (rec.getFill().equals(COLOR_OF_EMPTY)) rec.setFill(COLOR_OF_WIRE);
                    else if (rec.getFill().equals(COLOR_OF_WIRE)) rec.setFill(COLOR_OF_HEAD);
                    else if (rec.getFill().equals(COLOR_OF_HEAD)) rec.setFill(COLOR_OF_TAIL);
                    else if (rec.getFill().equals(COLOR_OF_TAIL)) rec.setFill(COLOR_OF_EMPTY);
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

        // mouse entered and exited event
        EventHandler<MouseEvent> mouseEnteredGrid = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Structure s = getClickedStructure();
                if (s != null && editable) showStructure(s, e, "show");
                if (e.getTarget() instanceof Rectangle) {
                    Rectangle rec = (Rectangle) e.getTarget();
                    //System.out.printf("%d %d\n", GridPane.getRowIndex(rec), GridPane.getColumnIndex(rec));
                }
            }
        };
        fxmlRoot.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEnteredGrid);

        // dragging event
        EventHandler<MouseEvent> mouseDrag = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

            }
        };
        fxmlRoot.addEventFilter(MouseEvent.DRAG_DETECTED, mouseDrag);

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
                        break;
                }
                System.out.println(grid.getTranslateX() + ", " + grid.getTranslateY());
            }
        };
        fxmlRoot.addEventFilter(KeyEvent.KEY_PRESSED, click);

        // initialization of grid pane
        drawGrid();

        // setup of grid pane
        fxmlRoot.setCenter(grid);
        BorderPane.setMargin(grid, new Insets(BREAK, BREAK, BREAK, BREAK));

        // setting position of grid pane
        grid.translateXProperty().set(gridPos(grid, "x"));
        grid.translateYProperty().set(gridPos(grid, "y"));

        // tracking current scene size
        //primaryStage.widthProperty().addListener(e -> { setGridPos(grid, primaryStage, "x"); });
        //primaryStage.heightProperty().addListener(e -> { setGridPos(grid, primaryStage, "y"); });
    }

    private double gridPos(GridPane grid, String dimesion) throws IllegalArgumentException {
        if (dimesion.equals("x")) {
            return grid.getTranslateX() - OFFSET_X + Main.getSceneSizeX() / 2 - (x * SIZE + (x + 1) * BREAK) / 2;
        } else if (dimesion.equals("y")) {
            return grid.getTranslateY() - OFFSET_Y + Main.getSceneSizeY() / 2 - (y * SIZE + (y + 1) * BREAK) / 2 + 5;
        } else throw new IllegalArgumentException("Illegal 'dimension' argument.");
    }

    // TODO: poprawić działanie tego
    private void setGridPos(GridPane grid, Stage stage, String dimension) {
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
        System.out.printf("expected: %f, %f\n", stage.getWidth() / 2, stage.getHeight() / 2);
        System.out.printf("actual: %f, %f\n", grid.getTranslateX(), grid.getTranslateY());
    }


    // offset between BorderPane.center coordinates and scene coordinates
    private final double OFFSET_X = 142;
    private final double OFFSET_Y = 40;

    // size and gap in grid pane
    private final double SIZE = 50;
    private final double BREAK = 3;

    // number of rows and columns in grid pane
    private int x = 100;
    private int y = 100;

    private boolean wireClicked = false;
    private boolean diodeClicked = false;
    private boolean clockClicked = false;
    private boolean andClicked = false;
    private boolean notClicked = false;
    private boolean orClicked = false;
    private boolean xorClicked = false;

    private boolean editable = false;
    private File saveFile;
    private Backup backup;

    public static final Color COLOR_OF_WIRE = Color.SILVER;
    public static final Color COLOR_OF_EMPTY = Color.BLACK;
    public static final Color COLOR_OF_HEAD = Color.ORANGE;
    public static final Color COLOR_OF_TAIL = Color.RED;

    @FXML
    GridPane grid;
    @FXML
    private BorderPane fxmlRoot;
    @FXML
    private Button previousButton;
    @FXML
    private Button playButton;
    @FXML
    private Button nextButton;
    @FXML
    private TextField iterationTextField;
    @FXML
    private TextField timeStepTextField;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button saveAsButton;
    @FXML
    private Button openButton;
    @FXML
    private Button heplButton;
    @FXML
    private Button wireButton;
    @FXML
    private Button diodeButton;
    @FXML
    private Button clockButton;
    @FXML
    private Button andButton;
    @FXML
    private Button notButton;
    @FXML
    private Button orButton;
    @FXML
    private Button xorButton;

    // action buttons

    @FXML
    void edit(ActionEvent event) {
        try {
            editable = Dialogs.editDialog();
            System.out.println(editable);
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
        x = result[0];
        y = result[1];
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
    void save(ActionEvent event) {
        if (saveFile == null) {
            FileChooser fc = new FileChooser();
            saveFile = fc.showOpenDialog(null);
        }
        DBops.saveMapToFile(new StructMap(x, y), saveFile);
    }

    @FXML
    void saveAs(ActionEvent event) {
        FileChooser fc = new FileChooser();
        saveFile = fc.showOpenDialog(null);

        if (saveFile != null) {
            DBops.saveMapToFile(gridToMap(), saveFile);
        } else {
            // handle
        }
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
        x = map.getXSize();
        y = map.getYSize();

        drawGrid();

        Rectangle rec;
        Cell cell;
        int c = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                rec = (Rectangle) grid.getChildren().get(i * y + j);
                cell = map.getCell(i, j);
                rec.setFill(cell.getColor());
                System.out.printf("%s\t", cell.getState());
            }
            System.out.println();
        }
    }

    void drawGrid() {
        if (grid.getChildren() != null) {
            grid.getChildren().clear();
        }

        grid.setHgap(BREAK);
        grid.setVgap(BREAK);
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++) {
                grid.add(new Rectangle(SIZE, SIZE), i, j);
            }
    }

    public StructMap gridToMap() {
        return new StructMap(x, y);
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

    public void showStructure(Structure s, MouseEvent e, String type) {
        if (e.getTarget() instanceof Rectangle) {
            Rectangle rec = (Rectangle) e.getTarget();
            int x0 = GridPane.getRowIndex(rec);
            int y0 = GridPane.getColumnIndex(rec);
            int xSize = (grid.getRowCount() - s.getXSize() - x0) > 0 ? s.getXSize() : grid.getRowCount() - x0;
            int ySize = (grid.getColumnCount() - s.getYSize() - y0) > 0 ? s.getYSize() : grid.getColumnCount() - y0;

            System.out.printf(
                    "gridRowCount:\t%d,\tsXsixe:\t%d,\tx0:\t%d\t->\txSize:\t%d\n" +
                    "gridColCount:\t%d,\tsYsixe:\t%d,\ty0:\t%d\t->\tySize:\t%d\n\n",
                    grid.getRowCount(), s.getXSize(), x0, xSize,
                    grid.getColumnCount(), s.getYSize(), y0, ySize);

            int offsetX = backup != null ? x0 - backup.x: 0;
            int offsetY = backup != null ? y0 - backup.y : 0;

            for (int i = 0; i < (backup != null && backup.tab != null ? backup.tab.length : 0); i++) {
                for (int j = 0; j < (backup.tab[0] != null ? backup.tab[0].length : 0); j++) {
                    rec = (Rectangle) grid.getChildren(). get((backup.y + j) * x + backup.x + i);
                    rec.setFill(backup.tab[i][j]);
                }
            }

            Color[][] tab = new Color[xSize][ySize];

            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    CellState state = s.getCell(i, j).getState();
                    rec = (Rectangle) grid.getChildren().get((y0 + j) * x + x0 + i);
                    tab[i][j] = (Color) rec.getFill();

                    if (state.equals(CellState.EMPA) || state.equals(CellState.EMPN)) {
                        rec.setFill(COLOR_OF_EMPTY);
                    } else if (state.equals(CellState.WIRE)) {
                        rec.setFill(COLOR_OF_WIRE);
                    } else if (state.equals(CellState.ELEH)) {
                        rec.setFill(COLOR_OF_HEAD);
                    } else if (state.equals(CellState.ELET)) {
                        rec.setFill(COLOR_OF_TAIL);
                    }
                }
            }
            backup = new Backup(x0, y0, tab);
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