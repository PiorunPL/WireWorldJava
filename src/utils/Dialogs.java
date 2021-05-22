package utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

import java.util.Optional;

public class Dialogs {

    public static int[] newBoardDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New Board");
        dialog.setHeaderText("Please, insert dimensions of the new board.");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        HBox pane = new HBox();
        pane.setSpacing(10);
        pane.setPadding(new Insets(20, 150, 10, 10));
        pane.setAlignment(Pos.CENTER);

        Label rowsLabel = new Label("Rows:");
        TextField rows = new TextField();
        rows.setPromptText("100");
        Label colsLabel = new Label("Cols:");
        TextField cols = new TextField();
        cols.setPromptText("100");

        pane.getChildren().addAll(rowsLabel, rows, colsLabel, cols);

        dialog.getDialogPane().setContent(pane);

        dialog.showAndWait();

        int[] result;
        try {
            result = new int[] {Integer.parseInt(rows.getText()), Integer.parseInt(cols.getText())};
        } catch (NumberFormatException e) {
            result = new int[] {100, 100};
        }
        return result;
    }

    public static boolean editDialog() throws IllegalStateException {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Edit Board");
        dialog.setHeaderText("Do you want to edit this board?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        dialog.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == yesButton) return true;
        else if (result.get() == noButton) return false;
        else throw new IllegalStateException();
    }
}
