package inventorymgr.InventoryManagementSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Starting position for the entire program and handles any changes to the stage/scene.
 */
public class Main extends Application {

    /**
     * Static Stage Variable used to display the various scenes.
     */
    private static Stage stage;


    /**
     * Creates the initial Stage and loads the Main Page Scene
     *
     * @param stage Stage - Used to initialize static stage variable.
     * @exception IOException required to handle FXMLLoader.load()
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("inventory_mainpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Changes the scene currently on the stage.
     *
     * @param fxml String - fxml file name or path
     * @exception IOException required to handle FXMLLoader.load()
     */
    public void changeScene(String fxml) throws IOException
    {
        Parent pane = FXMLLoader.load(Main.class.getResource(fxml));
        stage.getScene().setRoot(pane);
        stage.sizeToScene();
    }

    public void closeProgram()
    {
        stage.close();
    }

    /**
     * main function.
     *
     * Javadoc files can be found at "InventoryManagementSystem\Javadoc"
     *
     * @param args - Starting Arguments
     */
    public static void main(String[] args) {
        launch();
    }


}