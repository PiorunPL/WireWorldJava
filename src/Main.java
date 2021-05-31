import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    // dimensions of scene
    private final static double sceneSizeX = 970;
    private final static double sceneSizeY = 600;

    // offset between stage and scene coordinates
    private static final double STAGE_OFFSET = 8;
    private static final double STAGE_OFFSET_TOP = 31;

    @Override
    public void start(Stage stage) throws Exception {

        // loading fxml document
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gui/FXMLs/MainScreen.fxml"));
        Pane mainPane = loader.load();

        // creating scene
        Scene scene = new Scene(mainPane, sceneSizeX, sceneSizeY);
        scene.setCamera(new PerspectiveCamera());

        // setting up stage
        stage.setMinWidth(sceneSizeX + 2 * STAGE_OFFSET);
        stage.setMinHeight(sceneSizeY + STAGE_OFFSET + STAGE_OFFSET_TOP);
        stage.setResizable(true);
        stage.setTitle("Wireworld");
        stage.setScene(scene);
        stage.show();
    }
}
