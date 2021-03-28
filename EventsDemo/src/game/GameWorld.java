package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameWorld extends World {

    private List<Player> players = new ArrayList<>();
    private int level;
    private long timerStart;
    // private Timer timer; 
    private boolean gameOver;
    private GameView gameView;
    
    private static Clip clipLevel1;
    private static Clip clipLevel2;
    private static Clip clipLevel3;
    private static Clip clipLevel4;
    private static Clip clipLevel5;
        
    /**
     *  load all the audio clips
     */
    static {
    	try {
    		AudioInputStream audioInputStreamLevel1 = AudioSystem.getAudioInputStream(new File("data/audio/GameOneSoundtrack.wav"));
    		clipLevel1 = AudioSystem.getClip();
    		clipLevel1.open(audioInputStreamLevel1);
    	}
    	catch (Exception e) {
    		System.out.println("Cannot open audio file GameOneSoundtrack.wav");
    	}

    	try {
    		AudioInputStream audioInputStreamLevel2 = AudioSystem.getAudioInputStream(new File("data/audio/GameTwoSoundtrack.wav"));
    		clipLevel2 = AudioSystem.getClip();
    		clipLevel2.open(audioInputStreamLevel2);
    	}	
    	catch (Exception e) {
    		System.out.println("Cannot open audio file GameTwoSoundtrack.wav");
    	}
    	
    	try {
    		AudioInputStream audioInputStreamLevel3 = AudioSystem.getAudioInputStream(new File("data/audio/GameThreeSoundtrack.wav"));
    		clipLevel3 = AudioSystem.getClip();
    		clipLevel3.open(audioInputStreamLevel3);
    	}
    	catch (Exception e) {
    		System.out.println("Cannot open audio file GameThreeSoundtrack.wav");
    	}
    	
    	try {
    		AudioInputStream audioInputStreamLevel4 = AudioSystem.getAudioInputStream(new File("data/audio/GameFourSoundtrack.wav"));
    		clipLevel4 = AudioSystem.getClip();
    		clipLevel4.open(audioInputStreamLevel4);
    	}
    	catch (Exception e) {
    		System.out.println("Cannot open audio file GameFourSoundtrack.wav");
    	}
    	
    	try {
    		AudioInputStream audioInputStreamLevel5 = AudioSystem.getAudioInputStream(new File("data/audio/GameFiveSoundtrack.wav"));
    		clipLevel5 = AudioSystem.getClip();
    		clipLevel5.open(audioInputStreamLevel5);
    	}
    	catch (Exception e) {
    		System.out.println("Cannot open audio file GameFiveSoundtrack.wav");
    	}
    	
    	
    }
    
    /**
     * The constructor to initialize the game world class
     */
    public GameWorld() {
        // make the ground
        Shape groundShape = new BoxShape(200, 0.5f);
        StaticBody ground = new StaticBody(this, groundShape);
        ground.setPosition(new Vec2(0f, -12f));

        Shape ceilingShape = new BoxShape(200, 0.2f);
        StaticBody ceiling = new StaticBody(this, ceilingShape);
        ceiling.setPosition(new Vec2(0f, 13f));

        Shape sideShape = new BoxShape(0.2f, 100);
        StaticBody rightSide = new StaticBody(this, sideShape);
        rightSide.setPosition(new Vec2(21f, 0));
        StaticBody leftSide = new StaticBody(this, sideShape);
        leftSide.setPosition(new Vec2(-21f, 0));

        players.add(new Player(this,
                "Bale",
                "data/bale.png",
                new HashSet<Integer>(Arrays.asList(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W)))
        );
        players.get(0).setPosition(new Vec2(-8, -10));
        players.add(new Player(this,
                "Pirlo",
                "data/pirlo.png",
                new HashSet<Integer>(Arrays.asList(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP)))
        );
        players.get(1).setPosition(new Vec2(8, -10));

        RightGoal rightGoal = new RightGoal(this);
        rightGoal.setPosition(new Vec2(15, -10f));
        rightGoal.setName("rightGoal");
        LeftGoal leftGoal = new LeftGoal(this);
        leftGoal.setPosition(new Vec2(-20, -10f));
        leftGoal.setName("leftGoal");

        Ball ball = new Ball(this);
        ball.setPosition(new Vec2(0, 10));
        // BallCollisionListener ballCollisionListener = new BallCollisionListener(ball, players);
        BallCollisionListener ballCollisionListener = new BallCollisionListener(ball,this);
        ball.setLinearVelocity(new Vec2(new int[]{-5, 5}[new Random().nextInt(2)], 5));
        ball.addCollisionListener(ballCollisionListener);
        
        setLevel(1);
    }

    /**
     * Getter for the list of players
     * 
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for the level
     * 
     * @return the current level
     */
	public int getLevel() {
		return level;
	}

	/**
	 * Setter for current level
	 * 
	 * @param level - the current level to be set
	 */
	public void setLevel(int level) {
		this.level = level;

		if( level == 1 ) {
			clipLevel1.start();
		}
		else if( level == 2 ) {
			clipLevel1.stop();
			clipLevel2.start();
		} 
		else if( level == 3 ) {
			clipLevel2.stop();
			clipLevel3.start();
		}
		else if( level == 4 ) {
			clipLevel3.stop();
			clipLevel4.start();
		}
		else {
			clipLevel4.stop();
			clipLevel5.start();
		}
	}
	
	/**
	 * The method to restart the game
	 */
	public void restart() {
		if( clipLevel1.isRunning() )
			clipLevel1.stop();
		if( clipLevel2.isRunning() )
			clipLevel2.stop();
		if( clipLevel3.isRunning() )
			clipLevel3.stop();
		if( clipLevel4.isRunning() )
			clipLevel4.stop();
		if( clipLevel5.isRunning() )
			clipLevel5.stop();
		
		players.get(0).reset();
		players.get(1).reset();
		
		setLevel(1);
	}
	
	/**
	 * Setter for the view of the game
	 * 
	 * @param gameView - the view for this game
	 */
	public void setView(GameView gameView) {
		this.gameView = gameView;
	}
	
	/**
	 * To restart the count down timer
	 */
	public void restartTimer() {
		timerStart = System.currentTimeMillis();
	}
	
	/**
	 * Getter for the timer start value
	 * 
	 * @return the start timer value
	 */
	public long getTimer() {
		return timerStart;
	}
	
	/**
	 * Setter for the game over.
	 * 
	 * Sets the boolean flag to true.
	 * Shows game over on the game view.
	 */
	public void setGameOver() {
		gameOver = true;
		gameView.showGameOver();
	}

	/**
	 * Check if the game is over
	 * 
	 * @return boolean value game over
	 */
	public boolean isGameOver() {
		return gameOver;
	}
}
