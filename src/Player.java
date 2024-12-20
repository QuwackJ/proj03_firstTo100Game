/**
 * Represents a player in a game with a name and score
 * This class allows creating a player, getting their name and score, and updating their score
 *
 * @author Maya Rao
 * @version 10-22-24
 */

public class Player {
    // instance variables
    /** The name of the player */
    private String playerName;

    /** The player's score */
    private int playerScore;

    // constructor
    /**
     * Constructs a new Player with a given name and an initial score of 0
     *
     * @param name  the name of the player
     */
    public Player(String name) {
        playerName = name;
        playerScore = 0;
    }

    // methods
    /**
     * Get the name of the player
     *
     * @return  the name of the player
     */
    public String getName() {
      return playerName;
    }

    /**
     * Get the player's score
     *
     * @return  the player's score
     */
    public int getScore() {
        return playerScore;
    }

    /**
     * Updating the player's score with the previous round's score
     *
     * @param roundScore    the previous round's score
     * @return              the new score for the player
     */
    public int addRoundTotal(int roundScore) {
        return playerScore += roundScore;
    }
}
