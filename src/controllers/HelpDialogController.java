package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    @FXML private Pane orangePane;
    @FXML private Pane helpPane;

    String cellsText =
            "In this automata five types of cells can be\ndistinguished:\n" +
            "- EMPN, empty not appendable (grey) – they often\n" +
                    "  surrounds structures to prevent placing wire\n" +
                    "  in places not intended for this\n" +
            "- EMPA, empty appendable (black) – simple empty cells,\n" +
                    "  conductors can be placed on them\n" +
            "- WIRE, conductor (silver) – cell that conducts\n" +
                    "  electrons; structures consists of them;\n" +
                    "  simulation happens on conductor cells\n" +
            "- ELET, electron tail (orange) – follows electron head,\n" +
                    "  prevents it from spreading in wrong way\n" +
            "- ELEH, electron head (red) – represents actual current\n" +
                    "  in the simulation\n";

    String structsText =
            "Currently, this automata provides six types of predefined\n" +
                    "structures:\n" +
            "- Wire – string of conductor cells; it can be only placed\n" +
                    "  horizontally or vertically; useful for creating\n" +
                    "  long, straight strings of conductor\n" +
            "- Diode – conducts current only one way\n" +
            "- Clock – generates electrons with a period of x steps\n" +
            "- AND gate – implements logical conjunction; returns\n" +
                    "  electron if there are electrons in both\n" +
                    "  inputs at the same time\n" +
            "- NOT gate – implements logical negation; returns\n" +
                    "  electrons with a period of x steps if there\n" +
                    "  is no electron in input\n" +
            "- OR gate – implements logical disjunction; returns\n" +
                    "  electron if there is at least one electron\n" +
                    "  in one of two inputs\n" +
            "- XOR gate – implements exclusive or; returns electron if\n" +
                    "  there is only one electron in one of two inputs\n";

    String controlsText =
            "All actions on board are available if board is editable\n" +
                    "  (Edit button toggled).\n\n" +
            "Placing single cell of conductor\n" +
            "Single cell of conductor can be placed with LMB only on\n" +
                    "EMPA cells. Click LMP on simple cell of conductor to\n" +
                    "delete it.\n" +
            "WARNING! This action does not work on cells that are parts\n" +
                    "of structures (predefined or user defined). To delete them,\n" +
                    " delete button has to be used.\n\n" +
            "Placing Electron\n" +
            "Electron head can be placed with one LMB click. To place\n" +
                    "electron tail two LMB clicks are needed. Both actions can\n" +
                    "be performed on conductor wire only.\n\n" +
            "Placing predefined structures\n" +
            "Structure can be placed with LMB after it was chosen by\n" +
                    "pressing button on right panel. It is possible to place\n" +
                    "structure if it fits on board and does not cover any\n" +
                    "not-EMPA cells.\n";

    String buttonsText =
            "- New – creates new board with dimensions passed to\n" +
                    "  dialog window; if no dimensions are passed, it\n" +
                    "  creates new board with dimensions displayed on\n" +
                    "  text fields in dialog box\n" +
            "- Edit – displays dialog box which asks if board should\n" +
                    "  be editable\n" +
            "- Save – saves board into selected file\n" +
            "- Save as – saves board into file chosen in file chooser\n" +
                    "  dialog box\n" +
            "- Open – displays board from file chosen in file chooser\n" +
                    "  dialog box\n" +
            "- Help – displays help dialog box\n" +
            "- Next – steps into next iteration of simulation\n" +
            "- Play – shows n iterations of simulation with t time step,\n" +
                    "  where:\n" +
            "    - n – number of iterations; can be set in Iterations\n" +
                    "      text field; default value is displayed on Iterations\n" +
                    "      text field\n" +
            "    - t – duration of time step (in milliseconds); can be\n" +
                    "     set in Time step text box; default value is displayed\n" +
                    "     on Time step text field\n" +
            "- Pause – stops simulation in current position\n" +
            "- Delete – after pressing this button delete mode is\n" +
                    "  enabled; if board is editable, it is possible\n" +
                    "  to delete structures from it\n" +
            "WARNING! No action can be taken during the simulation.\n" +
                    "To perform any action simulation needs to be paused first.\n";

    String filesText =
            "Example of file format:\n\n" +
            "struct 30 50\n" +
            "structures<\n" +
            ":structName1:\n" +
            "1 4 4 1 1\n" +
            "4 1 4 1 4\n" +
            ":structName2:\n" +
            "1 4 1 4 1\n" +
            "1 1 1 4 1\n" +
            ">\n" +
            "board<\n" +
            "or 5 10 u\n" +
            "or 5 20\n" +
            "wire 0 0 l 4\n" +
            "structName1 20 30 r\n" +
            ">\n" +
            "\n" +
            "First line\n" +
            "First line of file defines file type {struct, map} and\n" +
                    "dimensions (not required).\n" +
            "\n" +
            "User defined structures\n" +
            "Structures paragraph contains user defined structures.\n" +
                    "It is not possible to define this structures in GUI yet,\n" +
                    "but if board is created in text file, user defined\n" +
                    "structures are supported.\n" +
            "Names of structures need to be surrounded with colons.\n" +
            "\n" +
            "Board\n" +
            "Defines structures coordinates and directions on the\nboard. Syntax:\n" +
            "name row column direction (length)\n" +
            "- name – name of structure\n" +
            "- row – y coordinate of structure on board\n" +
            "- column – x coordinate of structure on board\n" +
            "- direction – direction of structure on board structures\n" +
                    "  are defined in ‘up’ direction\n" +
            "    - u – up\n" +
            "    - r – right\n" +
            "    - d – down\n" +
            "    - l – left\n" +
            "- length – required only if structure type is wire,\n" +
                    "  defines length of wire\n";
}
