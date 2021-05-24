package utils.exceptions;

import javafx.scene.control.Alert;

public class ExceptionsDialogs {
    public static void warningDialog(String title, String text){
        Alert informationAlert = new Alert(Alert.AlertType.WARNING);
        informationAlert.setTitle(title);
        informationAlert.setHeaderText(null);
        informationAlert.setContentText(text);
        informationAlert.showAndWait();
    }
}
