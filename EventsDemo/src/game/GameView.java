package game;

import city.cs.engine.UserView;
import city.cs.engine.World;
import city.cs.engine.SoundClip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameView extends UserView {
	
	// the constants for the different levels
	private static final String LEVEL_1_BACKGROUND_IMAGE = "data/BackgroundGameOne.png";
	private static final String LEVEL_2_BACKGROUND_IMAGE = "data/BackgroundGameTwo.png";
	private static final String LEVEL_3_BACKGROUND_IMAGE = "data/BackgroundGameThree.png";
	private static final String LEVEL_4_BACKGROUND_IMAGE = "data/BackgroundGameFour.png";
	private static final String LEVEL_5_BACKGROUND_IMAGE = "data/BackgroundGameFive.png";
	
    private Image background;
    // private JLabel labelLevel;
    private int level;
    private GameWorld gameWorld;
    private long startTime;
    private JButton restartButton;
 
    private static String getBackgroundImage(int level) {
    	// return the background image for the given level - from 1 to 5
    	if( level == 1 )
    		return LEVEL_1_BACKGROUND_IMAGE;
    	else if( level == 2 )
    		return LEVEL_2_BACKGROUND_IMAGE;
    	else if( level == 3 )
    		return LEVEL_3_BACKGROUND_IMAGE;
    	else if( level == 4 )
    		return LEVEL_4_BACKGROUND_IMAGE;
    	else
    		return LEVEL_5_BACKGROUND_IMAGE;
    }
    
    public GameView(World w, int width, int height) {
        super(w, width, height);
        gameWorld = (GameWorld)w;
        setLevel(gameWorld.getLevel());
        startTime = System.currentTimeMillis();
        
        restartButton = new JButton("Restart");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);
        add(buttonPanel);
        restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameWorld.restart();
			}
		});
    }
    
    public void showGameOver() {
    	// to display the game over message
    	
    	// find and store the winner in the string winner
    	String winner = "";
    	if( gameWorld.getPlayers().get(0).getRoundsWon() == gameWorld.getPlayers().get(1).getRoundsWon() ) {
    		winner = "Its a tie!";
    	}
    	else {
    		if( gameWorld.getPlayers().get(0).getRoundsWon() > gameWorld.getPlayers().get(1).getRoundsWon() ) {
    			winner = gameWorld.getPlayers().get(0).getName() + " won!";
    		} else {
    			winner = gameWorld.getPlayers().get(1).getName() + " won!";
    		}
    	}
		JOptionPane.showMessageDialog(this, "Game over.\n" + winner, "Game over", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
    }

    // EDIT: the method to change the level
    public void setLevel(int level) {
    	this.level = level;
    	background = new ImageIcon(getBackgroundImage(level)).getImage();
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
        
        // get the players
        List<Player> players = gameWorld.getPlayers();
        setLevel(gameWorld.getLevel());
        
        // display the scores
        g.setColor(Color.WHITE);
        g.fillRect(30, 40, 160, 20);
        g.setColor(Color.BLUE);
        g.drawString("GOALS: " + players.get(0).getName() + " " + players.get(0).getScore() + " | " + 
        		players.get(1).getName() + " " + players.get(1).getScore(), 30, 55 );
        
        // display the rounds won
        g.setColor(Color.WHITE);
        g.fillRect(150, 10, 190, 20);
        g.setColor(Color.BLUE);
        g.drawString("LEVELS WON: " + players.get(0).getName() + " " + players.get(0).getRoundsWon() + " | " +
        		players.get(1).getName() + " " + players.get(1).getRoundsWon(), 150, 25);
        
        // display the current level
        g.setColor(Color.WHITE);
        g.fillRect(30, 65, 60, 20);
        g.setColor(Color.RED);
        g.drawString("Level: " + level, 30, 80);
        
        // display the timer
        g.setColor(Color.WHITE);
        g.fillRect(700, 40, 50, 20);
        g.setColor(Color.DARK_GRAY);
        
        long end = System.currentTimeMillis();
		long interval = end - startTime;
		
		long seconds = interval/1000;
		long minutes = seconds/60;
		seconds = seconds%60;
		
		String timer = "";
		
		if( gameWorld.getLevel() <=3 ) {
			timer += minutes<10? "0" : "";
			timer += minutes;
			timer += ":";
			timer += seconds<10? "0" : "";
			timer += seconds;
		} 
		else {
			interval = end - gameWorld.getTimer();
			seconds = interval/1000;
			seconds = (60 - seconds);
			minutes = 0;
			
			timer += minutes<10? "0" : "";
			timer += minutes;
			timer += ":";
			timer += seconds<10? "0" : "";
			timer += seconds;
		}
		
//		timer += minutes<10? "0" : "";
//		timer += minutes;
//		timer += ":";
//		timer += seconds<10? "0" : "";
//		timer += seconds;
		g.drawString(timer, 700, 55);
		
		// add the image for the restart button
		// Image restartButtonImage = new ImageIcon("data/RestartButton.png").getImage();
    }
}
