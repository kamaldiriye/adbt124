package game;

import city.cs.engine.*;

import java.util.HashSet;

public class Player extends Walker {
    private static final Shape playerShape = new PolygonShape(
            -0.11f, 2.8f,
            0.87f, 1.48f,
            0.99f, 0.29f,
            0.24f, -2.32f,
            -1.12f, -2.27f,
            -1.24f, 1.21f
    );
    private int score = 0;
    private int roundsWon = 0;
    private boolean isKicking = false;
    private final String name;
    private final HashSet<Integer> keyCodes;

    /**
     * Constructor to create a player
     * 
     * @param world - the world for the game
     * @param name - the name for this player
     * @param imagePath - the image path
     * @param keyCodes - the key codes
     */
    public Player(World world, String name, String imagePath, HashSet<Integer> keyCodes) {
        super(world, playerShape);
        this.name = name;
        this.keyCodes = keyCodes;
        BodyImage image = new BodyImage(imagePath, 5f);
        addImage(image);
    }

    /**
     * Getter for the score
     * 
     * @return - the score of this player
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for the name
     * 
     * @return - the name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the score
     * 
     * @param score - the score to set for the current player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * To increment the current score of the player by 1
     */
    public void incrementScore() {
        this.score++;
        System.out.println("Player " + this.name + " score: " + this.score);
    }

    /**
     * The getter for the key codes
     * 
     * @return - the key codes
     */
    public HashSet<Integer> getActionKeys() {
        return keyCodes;
    }

    /**
     * The getter for isKicking
     * 
     * @return - the boolean flag to check if the player is kicking
     */
    public boolean isKicking() {
        return isKicking;
    }

    /**
     * The setter for isKicking
     * 
     * @param kicking - the value to set to isKicking
     */
    public void setKicking(boolean kicking) {
        isKicking = kicking;
    }

    /**
     * Getter for the number of rounds won
     * 
     * @return - the number of rounds won by this player
     */
	public int getRoundsWon() {
		return roundsWon;
	}
	
	/**
	 * To increment the number of rounds won by 1
	 */
	public void addRoundWin() {
		roundsWon++;
	}
	
	/*
	 * TRo reset the scores and the number of rounds won to 0
	 */
	public void reset() {
		roundsWon = 0;
		score = 0;
	}
}
