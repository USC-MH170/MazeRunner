package mazerunner.engine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class GameEngine {
    static Map m = new Map();
    static Player p = new Player();
    private static Scanner input = new Scanner(System.in);
    public static int d = 5;
    public static int gameStatus = 0;
    public static boolean textGame = true;
    public static String action = "Move to start.";
    private final static String fileName = "save.txt";
    PrintWriter writer = null;



    /**
     * Main method which creates an engine object.
     * @param args
     */
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
    }


    /**
     * Run's methods required by the game in correct order depending on if it is through the GUI or System.out.
     *
     * After it prints the
     */
    public GameEngine() {
        if (textGame) {
        setDifficulty();
        }
        m.resetMap();
        generateGame();
        if (textGame) {
        gameControllerText();
        } else {
            gameControllerGUI();
        }
    }
    /**
     * Prompts input for text game difficulty and sets it to d.
     *
     * If input is out of range, another input is prompted.
     *
     */

    public static void setDifficulty() {
        System.out.println("Type Difficulty:");
        d = input.nextInt();
        System.out.println(d);
        if(d > 10) {
            System.out.println("Choose a number between 0-10.");
            setDifficulty();
        }
    }

    /**
     * Generates a game setting the game status and players position.
     *
     * Runs generateMap method passing through the game difficulty. Moreover, the player stats are reset in case of new game.
     *
     */
    public void generateGame() {
        gameStatus = 0;
        m.playerX = 9;
        m.playerY = 0;
        generateMap(d);
        p.resetPlayer();
    }

    /**
     * the generateMap method inputs the d variable to pass into the tile generator. If the game is text based it prints the map to console.
     * @param d
     */

    public void generateMap(int d) {
        m.tileGenerator(d);
        if (textGame) {
            System.out.print(m);
        }
    }
    /**
     *
     * gameControllerText is used for the text based game in the consolelog.
     *
     * The gameControllerText method first checks if the game is still running through a while loop.
     *
     * The instuctions for moving the player are printed to the console.
     *
     * After, required the input to move the player. Based on the input a switch is use to run the corresponding methods.
     *
     * After the map and game information is printed to the console.
     *
     * if the while loop is broken the game status is changed respectively.
     *
     */
    public void gameControllerText() {
        while (p.getStamina() > 0 && gameStatus == 0) {
                System.out.println("\n0 = Up | 1 = Right | 2 = Down | 3 = Left");
                int textController = input.nextInt();
                switch (textController) {
                    case 0:
                        moveUp();
                        break;
                    case 1:
                        moveRight();
                        break;
                    case 2:
                        moveDown();
                        break;
                    case 3:
                        moveLeft();
                        break;
            }
            System.out.print(m);
            System.out.println("Your Stamina is: " + p.stamina);
            System.out.println("You have " + p.gold + " gold. ");
        }
        if (p.getStamina() == 0) {
            endGameStamina();
        } else if (gameStatus == 1) {
            endGameWin();
        }
        else if (gameStatus == 2) {
            endGameTrap();
        }
    }

    public static void gameControllerGUI() {
        while (p.getStamina() > 0 && gameStatus == 0) {
            System.out.println("Your Stamina is: " + p.stamina);
            System.out.println("You have " + p.gold + " gold. ");
            break;
        }
        if (p.getStamina() == 0) {
            endGameStamina();
        } else if (gameStatus == 1) {
            endGameWin();
        }
        else if (gameStatus == 2) {
            endGameTrap();
        }
    }


    public static void updateGame() {
        if (Map.board[m.playerX][m.playerY] == Tile.Empty) {
            p.stamina--;
            Map.board[m.playerX][m.playerY] = Tile.Player;
        } else if (Map.board[m.playerX][m.playerY] == Tile.Gold) {
            p.gold++;
            p.stamina--;
            Map.board[m.playerX][m.playerY] = Tile.Player;
            System.out.println("Nice! You have collected 1 gold.");
        } else if (Map.board[m.playerX][m.playerY] == Tile.Trap) {
            p.gold--;
            p.stamina--;
            System.out.println("Oh no! You are trapped.");
            Map.board[m.playerX][m.playerY] = Tile.PlayerTrap;
            if (p.gold < 0) {
                gameStatus = 2;
            }
        }
        else if (Map.board[m.playerX][m.playerY] == Tile.Apple) {
            p.stamina = p.stamina + 3;
            Map.board[m.playerX][m.playerY] = Tile.Player;
            System.out.println("Yum! You have collected an apple and your stamina has increased.");
        } else if (Map.board[m.playerX][m.playerY] == Tile.Exit) {
            Map.board[m.playerX][m.playerY] = Tile.Player;
            gameStatus = 1;
        }
    }

    public static void moveUp() {
        if (m.playerX > 0) {
            trapChecker();
            m.playerX--;
            updateGame();
            System.out.println("You have moved up.");
            action = "You have moved up.";
        }
        else {
            System.out.println("You can't move there!");
            action = "You can't move there!";
        }
    }

    public static void moveRight() {
        if (m.playerY < 9) {
            trapChecker();
            m.playerY++;
            updateGame();
            System.out.println("You have moved right.");
            action = "You have moved right.";
        }
        else {
            System.out.println("You can't move there!");
            action = "You can't move there!";
        }
    }

    public static void moveDown() {
        if (m.playerX < 9) {
            trapChecker();
            m.playerX++;
            updateGame();
            System.out.println("You have moved down.");
            action = "You have moved down.";
        }
        else {
            System.out.println("You can't move there!");
            action = "You can't move there!";
        }
    }

    public static void moveLeft() {
        if (m.playerY > 0) {
            trapChecker();
            m.playerY--;
            updateGame();
            System.out.println("You have moved left.");
            action = "You have moved left.";
        }
        else {
            System.out.println("You can't move there!");
            action = "You can't move there!";
        }
    }


    public static void trapChecker(){
        if (Map.board[m.playerX][m.playerY] == Tile.PlayerTrap) {
            Map.board[m.playerX][m.playerY] = Tile.Trap;
        } else {
            Map.board[m.playerX][m.playerY] = Tile.Empty;
        }
    }

    public static void endGameWin() {
        System.out.printf("\nYou have won! Your score is: %d", p.gold);
        action = "You have won!";
    }


    public static void endGameStamina() {
        gameStatus = 1;
        System.out.println("\nGAME OVER: YOU HAVE RAN OUT OF STAMINA!");
        action = "Game over: You have no stamina.";
    }

    public static void endGameTrap() {
        System.out.println("\nGAME OVER: YOU STOOD ON A TRAP AND HAD NO GOLD LEFT!");
        action = "Game over: You have no gold left.";
    }

    public String toString(){
        return action + "\nGold: " + p.gold + "\nStamina: " + p.stamina;
    }








    public static void saveGame() {
        try{
            PrintWriter writer = new PrintWriter(fileName);
            writer.println(p.playerPositionX);
            writer.println(p.playerPositionY);
            writer.println(p.stamina);
            writer.println(p.gold);
            writer.println(m.playerX);
            writer.println(m.playerY);
            writer.print(Arrays.deepToString(Map.board));
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }


    public static void loadGame() {
        try{
            gameStatus = 0;
            Scanner loader = new Scanner(new File(fileName));
            p.playerPositionX = loader.nextInt();
            p.playerPositionY = loader.nextInt();
            p.stamina = loader.nextInt();
            p.gold = loader.nextInt();
            m.playerX = loader.nextInt();
            m.playerY = loader.nextInt();
            action = "Saved game loaded.";
            //Map.board = input.nextInt();
            String [][] board = new String[10][10];
            for (int i = 0; i < Map.board.length ; i++) {
                for (int j = 0; j < Map.board[i].length; j++) {
                    board[i][j] = loader.next();
                    board[i][j] = board[i][j].replaceAll("[\\[\\],]", "");
                }
            }
            for (int i = 0; i < Map.board.length ; i++) {
                for (int j = 0; j < Map.board[i].length; j++) {
                    Map.board[i][j] = Tile.valueOf(board[i][j]);
                }
            }
            loader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }










//    public static void saveGame() {
//        WriteData data =  new WriteData();
//        data.playerPositionX = p.playerPositionX;
//        data.playerPositionY = p.playerPositionY;
//        WriteData.board = Map.board;
//        action = "Your game has been saved!";
//        try {
//            ResourceManager.save(data, "save.txt");
//        } catch (Exception e) {
//            System.out.println("Couldn't save: " + e.getMessage());
//        }
//    }



















}