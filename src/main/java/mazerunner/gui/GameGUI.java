package mazerunner.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mazerunner.engine.GameEngine;

public class GameGUI extends Application {


    /**
     * set the GameEngine to the GUI version then launches the GUI.
     *
     * @param args
     */
    public static void main(String[] args) {
        GameEngine.textGame = false;
        launch(args);
    }

    /**
     * Creates the GUI for the maze game.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("game_gui.fxml"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Maze Runner Game");
        primaryStage.show();
    }
}
