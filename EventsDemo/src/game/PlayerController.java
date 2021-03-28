package game;

import org.jbox2d.common.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlayerController implements KeyListener {
    private static final float WALKING_SPEED = 10;
    private static final float JUMP_SPEED = 12;
    private final List<Player> players;
    
    // private static final SoundPlayer HEADER_KICKING_SOUND = new SoundPlayer("data/audio/HeaderAndKickingSound.wav");

    public PlayerController(List<Player> players) {
        this.players = players;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE && !players.get(0).isKicking()) {
            System.out.println("Player 1 kick");
            players.get(0).setKicking(true);
            players.get(0).move(new Vec2(1f, -1f));
            players.get(0).rotate(45);
            
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
            
            // HEADER_KICKING_SOUND.play();
            
        } else if (code == KeyEvent.VK_P && !players.get(1).isKicking()) {
            System.out.println("Player 2 kick");
            players.get(1).setKicking(true);
            players.get(1).move(new Vec2(-1f, -1f));
            players.get(1).rotate(-45);
            
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
            
        } else {
            this.players.forEach(player -> {
                if (player.getActionKeys().contains(code)) {
                    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                        player.startWalking(-WALKING_SPEED);
                    } else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                        player.startWalking(WALKING_SPEED);
                    } else if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                        if (player.getPosition().y <= -9) {
                            player.jump(JUMP_SPEED);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            System.out.println("Player 1 stop kick");
            players.get(0).setKicking(false);
            players.get(0).move(new Vec2(-1f, 1f));
            players.get(0).rotate(-45);
        } else if (code == KeyEvent.VK_P) {
            System.out.println("Player 2 stop kick");
            players.get(1).setKicking(false);
            players.get(1).move(new Vec2(1f, 1f));
            players.get(1).rotate(45);
        } else {
            this.players.forEach(player -> {
                if (player.getActionKeys().contains(code)) {
                    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT ||
                            code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                        player.stopWalking();
                    }
                }
            });
        }
    }
}
