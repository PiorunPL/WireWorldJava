package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpDialogController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cellsLabel.setStyle("-fx-font-family: monospace; -fx-font-weight: bold; -fx-font-size: 14;");
        cellsLabel.setText(cellsText);
        cellsPane.setContent(cellsLabel);

        structsLabel.setStyle("-fx-font-family: monospace; -fx-font-weight: bold; -fx-font-size: 14;");
        structsLabel.setText(structsText);
        structsPane.setContent(structsLabel);

        controlsLabel.setStyle("-fx-font-family: monospace; -fx-font-weight: bold; -fx-font-size: 14;");
        controlsLabel.setText(controlsText);
        controlsPane.setContent(controlsLabel);

        buttonsLabel.setStyle("-fx-font-family: monospace; -fx-font-weight: bold; -fx-font-size: 14;");
        buttonsLabel.setText(buttonsText);
        buttonsPane.setContent(buttonsLabel);

        filesLabel.setStyle("-fx-font-family: monospace; -fx-font-weight: bold; -fx-font-size: 14;");
        filesLabel.setText(filesText);
        filesPane.setContent(filesLabel);
    }

    @FXML private ScrollPane cellsPane;
    @FXML private ScrollPane structsPane;
    @FXML private ScrollPane controlsPane;
    @FXML private ScrollPane buttonsPane;
    @FXML private ScrollPane filesPane;

    @FXML void buttons() { buttonsPane.toFront(); }
    @FXML void cells() { cellsPane.toFront(); }
    @FXML void controls() { controlsPane.toFront(); }
    @FXML void files() { filesPane.toFront(); }
    @FXML void structs() { structsPane.toFront(); }

    @FXML private Label cellsLabel;
    @FXML private Label structsLabel;
    @FXML private Label controlsLabel;
    @FXML private Label buttonsLabel;
    @FXML private Label filesLabel;

    String cellsText =
            """
                    In this automata five types of cells can be
                    distinguished:
                    - EMPN, empty not appendable (grey) – they often
                      surrounds structures to prevent placing wire
                      in places not intended for this
                    - EMPA, empty appendable (black) – simple empty cells,
                      conductors can be placed on them
                    - WIRE, conductor (silver) – cell that conducts
                      electrons; structures consists of them;
                      simulation happens on conductor cells
                    - ELET, electron tail (orange) – follows electron head,
                      prevents it from spreading in wrong way
                    - ELEH, electron head (red) – represents actual current
                      in the simulation
                    """;

    String structsText =
            """
                    Currently, this automata provides six types of predefined
                    structures:
                    - Wire – string of conductor cells; it can be only placed
                      horizontally or vertically; useful for creating
                      long, straight strings of conductor
                    - Diode – conducts current only one way
                    - Clock – generates electrons with a period of x steps
                    - AND gate – implements logical conjunction; returns
                      electron if there are electrons in both
                      inputs at the same time
                    - NOT gate – implements logical negation; returns
                      electrons with a period of x steps if there
                      is no electron in input
                    - OR gate – implements logical disjunction; returns
                      electron if there is at least one electron
                      in one of two inputs
                    - XOR gate – implements exclusive or; returns electron if
                      there is only one electron in one of two inputs
                    """;

    String controlsText =
            """
                    All actions on board are available if board is editable
                    (Edit button toggled).

                    Placing single cell of conductor
                    Single cell of conductor can be placed with LMB only on
                    EMPA cells. Click LMP on simple cell of conductor to
                    delete it.
                    WARNING! This action does not work on cells that are parts
                    of structures (predefined or user defined). To delete them,
                    delete button has to be used.

                    Placing Electron
                    Electron head can be placed with one LMB click. To place
                    electron tail two LMB clicks are needed. Both actions can
                    be performed on conductor wire only.

                    Placing predefined structures
                    Structure can be placed with LMB after it was chosen by
                    pressing button on right panel. It is possible to place
                    structure if it fits on board and does not cover any
                    not-EMPA cells.
                    """;

    String buttonsText =
            """
                    - New – creates new board with dimensions passed to
                      dialog window; if no dimensions are passed, it
                      creates new board with dimensions displayed on
                      text fields in dialog box
                    - Edit – displays dialog box which asks if board should
                      be editable
                    - Save – saves board into selected file
                    - Save as – saves board into file chosen in file chooser
                      dialog box
                    - Open – displays board from file chosen in file chooser
                      dialog box
                    - Help – displays help dialog box
                    - Next – steps into next iteration of simulation
                    - Play – shows n iterations of simulation with t time step,
                      where:
                        - n – number of iterations; can be set in Iterations
                          text field; default value is displayed on Iterations
                          text field
                        - t – duration of time step (in milliseconds); can be
                         set in Time step text box; default value is displayed
                         on Time step text field
                    - Pause – stops simulation in current position
                    - Delete – after pressing this button delete mode is
                      enabled; if board is editable, it is possible
                      to delete structures from it
                    WARNING! No action can be taken during the simulation.
                    To perform any action simulation needs to be paused first.
                    """;

    String filesText =
            """
                    Example of file format:

                    struct 30 50
                    structures<
                    :structName1:
                    1 4 4 1 1
                    4 1 4 1 4
                    :structName2:
                    1 4 1 4 1
                    1 1 1 4 1
                    >
                    board<
                    or 5 10 u
                    or 5 20
                    wire 0 0 l 4
                    structName1 20 30 r
                    >

                    First line
                    First line of file defines file type {struct, map} and
                    dimensions (not required).

                    User defined structures
                    Structures paragraph contains user defined structures.
                    It is not possible to define this structures in GUI yet,
                    but if board is created in text file, user defined
                    structures are supported.
                    Names of structures need to be surrounded with colons.

                    Board
                    Defines structures coordinates and directions on the
                    board. Syntax:
                    name row column direction (length)
                    - name – name of structure
                    - row – y coordinate of structure on board
                    - column – x coordinate of structure on board
                    - direction – direction of structure on board structures
                      are defined in ‘up’ direction
                        - u – up
                        - r – right
                        - d – down
                        - l – left
                    - length – required only if structure type is wire,
                      defines length of wire
                    """;
}
