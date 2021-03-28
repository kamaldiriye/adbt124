package game;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * A world with some bodies.
 */
public class Game {

    /**
     * The World in which the bodies move and interact.
     */
    private GameWorld world;

    /**
     * A graphical display of the world (a specialised JPanel).
     */
    private GameView view;

    /**
     * Initialise a new Game.
     */
    public Game() {

        // make the world
        GameWorld world = new GameWorld();
        world.setGravity(15);

        // make a view starting with level 1
        view = new GameView(world, 800, 500);
        view.setZoom(20);
        world.setView(view);

        PlayerController playerController = new PlayerController(world.getPlayers());
        view.addKeyListener(playerController);

        view.addMouseListener(new GiveFocus(view));

        // add the view to a frame (Java top level window)
        final JFrame frame = new JFrame("Football heads");
        frame.add(view);
        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        // uncomment this to make a debugging view
        //JFrame debugView = new DebugViewer(world, 500, 500);

        // start our game world simulation!
        world.start();
    }

    /**
     * Run the game.
     */
    public static void main(String[] args) {

        new Game();
    }
}
