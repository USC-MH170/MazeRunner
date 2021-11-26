package mazerunner.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mazerunner.engine.GameEngine;
import mazerunner.engine.Map;
import mazerunner.engine.Tile;
import java.net.URL;
import java.util.ResourceBundle;

    public class Controller implements Initializable {

        @FXML
        public GridPane mapGrid;

        @FXML
        Button start;

        @FXML
        TextField levelInput;

        @FXML
        TextArea outputToGUI;

        @FXML
        Button up;

        @FXML
        Button down;

        @FXML
        Button right;

        @FXML
        Button left;

        @FXML
        Button help;

        @FXML
        Button save;

        @FXML
        Button load;

        GameEngine g = new GameEngine();
        Image Empty = new Image("File:img/Empty.jpg");
        Image Player = new Image("file:img/Player.jpg");
        Image Gold = new Image("File:img/Gold.jpg");
        Image Trap = new Image("File:img/Trap.jpg");
        Image PlayerTrap = new Image("File:img/PlayerTrap.jpg");
        Image Apple = new Image("File:img/Apple.jpg");
        Image Exit = new Image("File:img/Exit.jpg");





        /**
         * Initializes startNewGame and updateGUI methods.
         * @param url
         * @param resourceBundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            startNewGame();
            updateGUI();
        }
        /**
         * Listeners for GUI buttons
         */
        @FXML
        public void startNewGame() {
            start.setOnAction(e -> isInt(levelInput, levelInput.getText()));
            up.setOnAction(e -> moveUp());
            down.setOnAction(e -> moveDown());
            right.setOnAction(e -> moveRight());
            left.setOnAction(e -> moveLeft());
            help.setOnAction(e -> help());
            save.setOnAction(e -> GameEngine.saveGame());
            load.setOnAction(e -> loadGame());
        }

        public void loadGame() {
            GameEngine.loadGame();
            //GUIStringDisplay();
            updateGUI();
        }


        /**
         *
         * Inputs the difficulty then prints to console.
         * creates a new instance of GameEngine for the new difficulty.
         * If input is not a number, prints a request to input a number.
         *
         * @param levelInput
         * @param text
         * @return
         */
        private boolean isInt(TextField levelInput, String text) {
            if (Integer.parseInt(levelInput.getText()) <= 10 && Integer.parseInt(levelInput.getText()) > 0) {
                try {
                    GameEngine.d = Integer.parseInt(levelInput.getText());
                    new GameEngine();
                    updateGUI();
                    return true;
                } catch (NumberFormatException e) {
                    outputToGUI.setText("Please enter a number.");
                }
                return false;
            }
            outputToGUI.setText("Choose a number between 0-10.");
            return false;
        }
        /**
         * Checks if game is in progress.
         *
         * Runs the corresponding methods to move up in the engine to update the game and GUI
         */
        @FXML
        public void moveUp() {
            if (GameEngine.gameStatus == 0) {
                GameEngine.moveUp();
                GameEngine.gameControllerGUI();
                updateGUI();
            }
        }
        /**
         * Checks if game is in progress.
         *
         * Runs the corresponding methods to move down in the engine to update the game and GUI
         */
        @FXML
        public void moveDown() {
            if (GameEngine.gameStatus == 0) {
                GameEngine.moveDown();
                GameEngine.gameControllerGUI();
                updateGUI();
            }
        }
        /**
         * Checks if game is in progress.
         *
         * Runs the corresponding methods to move right in the engine to update the game and GUI
         */
        @FXML
        public void moveRight() {
            if (GameEngine.gameStatus == 0) {
                GameEngine.moveRight();
                GameEngine.gameControllerGUI();
                updateGUI();
            }
        }
        /**
         * Checks if game is in progress.
         *
         * Runs the corresponding methods to move left in the engine to update the game and GUI
         */
        @FXML
        public void moveLeft() {
            if (GameEngine.gameStatus == 0) {
                GameEngine.moveLeft();
                GameEngine.gameControllerGUI();
                updateGUI();

            }
        }
        /**
         * Outputs the string value of GameEngine to the GUI. This includes the action and amount of gold and stamina.
         */
        public void GUIStringDisplay() {
            outputToGUI.setText(String.valueOf(g));
        }

        /**
         * Updates the tiles in the GUI GridPane through checking the positioning of the board 2d array.
         */
        public void updateGUI() {
            GUIStringDisplay();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (Map.board[i][j] == Tile.Empty) {
                        mapGrid.add(new ImageView(Empty), j, i);
                    } else if (Map.board[i][j] == Tile.Player) {
                        mapGrid.add(new ImageView(Player), j, i);
                    } else if (Map.board[i][j] == Tile.Gold) {
                        mapGrid.add(new ImageView(Gold), j, i);
                    } else if (Map.board[i][j] == Tile.Trap) {
                        mapGrid.add(new ImageView(Trap), j, i);
                    } else if (Map.board[i][j] == Tile.PlayerTrap) {
                        mapGrid.add(new ImageView(PlayerTrap), j, i);
                    } else if (Map.board[i][j] == Tile.Apple) {
                        mapGrid.add(new ImageView(Apple), j, i);
                    } else if (Map.board[i][j] == Tile.Exit) {
                        mapGrid.add(new ImageView(Exit), j, i);
                    }
                }
            }
        }


        public void help() {
            Stage helpStage = new Stage();

            helpStage.initModality(Modality.APPLICATION_MODAL);
            helpStage.setTitle("Maze Runner - Help");
            helpStage.setMinWidth(300);
            helpStage.setMinHeight(300);

            Label label = new Label();
            label.setText("How to play: \n\nThe aim of the game is to collect as many coins a possible \nbefore getting to the exit. \n\nEach movement costs 1 stamina and standing on traps will \nreduce your gold by one. \n\nTo start a game, enter a difficulty between 0-10 and press start." );
            label.setPadding(new Insets(0, 30,   30,  30));
            Button playGame = new Button("Play Game");
            playGame.setOnAction(e -> helpStage.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, playGame);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            helpStage.setScene(scene);
            helpStage.show();
        }







    }








