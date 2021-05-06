package controllers;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class MainPaneController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXMLs/MainScreen.fxml"));
        Region main = loader.load();
        Scene scene = new Scene(main, 800, 600);
        Camera camera = new PerspectiveCamera();

        GridPane grid = new GridPane();

        Circle circle = new Circle(10);

        //System.out.println(loader1.getController().getClass().getName().toString());
        //for (int i = 0; i < 10; i++)
          //  for (int j = 0; j < 9; j++)
            //    grid.add(circle, i, j);

        System.out.println(grid.getRowCount());
        //fxmlRoot.setCenter(grid);

        scene.setCamera(camera);

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Logowanie");
        primaryStage.show();

        initMouseControll(main, scene, primaryStage);
    }

    public void initMouseControll(Parent group, Scene scene, Stage stage) {
        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });

    }

    @FXML
    private Button previousButton;
    @FXML
    private Button playButton;
    @FXML
    private Button nextButton;
    @FXML
    private TextField iterationTextField;
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
    @FXML
    private BorderPane fxmlRoot;

    @FXML
    void and(ActionEvent event) {

    }

    @FXML
    void clock(ActionEvent event) {

    }

    @FXML
    void diode(ActionEvent event) {

    }

    @FXML
    void edit(ActionEvent event) {

    }

    @FXML
    void help(ActionEvent event) {

    }

    @FXML
    void newBoard(ActionEvent event) {

    }

    @FXML
    void next(ActionEvent event) {

    }

    @FXML
    void not(ActionEvent event) {

    }

    @FXML
    void open(ActionEvent event) {

    }

    @FXML
    void or(ActionEvent event) {

    }

    @FXML
    void play(ActionEvent event) {

    }

    @FXML
    void previous(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void saveAs(ActionEvent event) {

    }

    @FXML
    void wire(ActionEvent event) {

    }

    @FXML
    void xor(ActionEvent event) {

    }

}




