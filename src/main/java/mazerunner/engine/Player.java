package mazerunner.engine;

public class Player {

    public int stamina = 12;
    public int gold = 0;
    int playerPositionX = 0;
    int playerPositionY = 9;


    /**
     * When run, resets the players stats for a new game.
     */
    public void resetPlayer(){
        stamina = 12;
        gold = 0;
        playerPositionX = 0;
        playerPositionY = 9;
    }


    /**
     * Returns the player stamina.
     * @return
     */
    public int getStamina() {
        return stamina;
    }

}