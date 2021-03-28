package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

import java.io.File;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class BallCollisionListener implements CollisionListener {

    private Ball ball;
    private List<Player> players;
    private GameWorld gameWorld;

    /**
     * Constructor
     * 
     * @param ball - the ball for this listener
     * @param gameWorld - the game world
     */
    public BallCollisionListener(Ball ball, GameWorld gameWorld) {
        this.ball = ball;
        this.gameWorld = gameWorld;
        this.players = gameWorld.getPlayers();
    }
    
    private void updateLevelScore() {
    	// switch to level 2 when level 1 score is reached
    	if( gameWorld.getLevel() == 1 ) {
    		
    		// update the number of rounds won based on the current scores of the players
    		if( players.get(0).getScore() == 3 )
        	{
    			players.get(0).addRoundWin();
    			gameWorld.setLevel(gameWorld.getLevel() + 1);
        	}
        	else if( players.get(1).getScore() == 3 )
        	{
        		players.get(1).addRoundWin();
        		gameWorld.setLevel(gameWorld.getLevel() + 1);
        	}
    	}
    	// switch to level 3 when level 2 score is reached
    	else if( gameWorld.getLevel() == 2 ) {
    		
    		// update the number of rounds won based on the current scores of the players
    		if( players.get(0).getScore() == 5 )
        	{
    			players.get(0).addRoundWin();
    			gameWorld.setLevel(gameWorld.getLevel() + 1);
        	}
        	else if( players.get(1).getScore() == 5 )
        	{
        		players.get(1).addRoundWin();
        		gameWorld.setLevel(gameWorld.getLevel() + 1);
        	}
    	}
    	// switch to level 4 when level 3 score is reached
    	else if( gameWorld.getLevel() == 3 ) {
    		
    		// update the number of rounds won based on the current scores of the players
    		if( players.get(0).getScore() == 7 )
        	{
    			players.get(0).addRoundWin();
    			gameWorld.setLevel(gameWorld.getLevel() + 1);
        	}
        	else if( players.get(1).getScore() == 7 )
        	{
        		players.get(1).addRoundWin();
        		gameWorld.setLevel(gameWorld.getLevel() + 1);
        	}
    		gameWorld.restartTimer();
    	}
    	// switch to level 5 when level 4 score is reached
    	else if( gameWorld.getLevel() == 4 ) {
    		
    		// check if the timer runs out in level 4
    		long currTime = System.currentTimeMillis();
    		long interval = currTime - gameWorld.getTimer();
    		
    		long seconds = interval/1000;
    		long minutes = seconds/60;
    		seconds = seconds%60;
    		
    		// switch to next level if the timer runs out
    		if( minutes == 1 && seconds == 0 ) {
    			if( players.get(0).getScore() > players.get(1).getScore() )
    				players.get(0).addRoundWin();
    			else
    				players.get(1).addRoundWin();
    			gameWorld.setLevel(gameWorld.getLevel() + 1);
    			gameWorld.restartTimer();
    			return;
    		}
    		
    		// update the number of rounds won based on the current scores of the players
    		if( players.get(0).getScore() == 9 )
        	{
    			players.get(0).addRoundWin();
    			gameWorld.setLevel(gameWorld.getLevel() + 1);
        	}
        	else if( players.get(1).getScore() == 9 )
        	{
        		players.get(1).addRoundWin();
        		gameWorld.setLevel(gameWorld.getLevel() + 1);
        	}
    		
    		gameWorld.restartTimer();
    	}
    	// for the last level, update score and set game over
    	else if( gameWorld.getLevel() == 5 ) {
    		
    		// update the number of rounds won based on the current scores of the players
    		if( players.get(0).getScore() == 11 )
        	{
    			players.get(0).addRoundWin();
        	}
        	else if( players.get(1).getScore() == 11 )
        	{
        		players.get(1).addRoundWin();
        	}
    		
    		// set game over
    		gameWorld.setGameOver();
    	}
    }

    @Override
    public void collide(CollisionEvent e) {
        float impulseFactor = 3f;
        if (this.ball.getPosition().y <= -2 &&
                ("leftGoal".equals(e.getOtherBody().getName()) || "rightGoal".equals(e.getOtherBody().getName()))) {
            // ball is on the same height as the goal posts and collides with one of the goals
            System.out.println("GOAL!");
            if ("leftGoal".equals(e.getOtherBody().getName())) {
                // is in left goal, so increment the score of player 2
                System.out.println("Player 2 (" + players.get(1).getName() + ") scores!");
                players.get(1).incrementScore();
            } else {
                // is in right goal, so increment the score of player 1
                System.out.println("Player 1 (" + players.get(0).getName() + ") scores!");
                players.get(0).incrementScore();
            }
            System.out.println("Score: " + players.get(0).getName() + " " + players.get(0).getScore() + " - " + players.get(1).getScore() + " " + players.get(1).getName());
            this.ball.setPosition(new Vec2(0, 10));
            this.ball.setLinearVelocity(new Vec2(new int[]{-5, 5}[new Random().nextInt(2)], 5));
            
            // update the level scores
            updateLevelScore();
        }

        // check if player has kicked the ball
        if (e.getOtherBody() instanceof Player) {
            if (((Player) e.getOtherBody()).isKicking()) {
                // if yes, apply a stronger impulse factor
                impulseFactor = 4f;                
            }
            // EDIT 2: adding sound when the player is heading the ball
            else {
            	
            	try {
        	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data/audio/HeaderAndKickingSound.wav"));
        	        Clip clip = AudioSystem.getClip();
        	        clip.open(audioInputStream);
        	        clip.start();
        	        // System.out.println("Played");
        	    } catch(Exception ex) {
        	        System.out.println("Error with playing sound.");
        	        ex.printStackTrace();
        	    }
            }
        }
        this.ball.applyImpulse(e.getVelocity().mul(impulseFactor));
    }
}