package game;

import city.cs.engine.*;

public class Ball extends DynamicBody {
    private static final Shape ballShape = new CircleShape(1);

    private static final BodyImage image =
            new BodyImage("data/football.png", 2f);

    public Ball(World world) {
        super(world, ballShape);
        addImage(image);
    }
}
