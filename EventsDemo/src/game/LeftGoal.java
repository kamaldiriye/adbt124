package game;

import city.cs.engine.*;

public class LeftGoal extends StaticBody {
    private static final Shape goalShape = new PolygonShape(0.3f,7.1f, -0.6f,-2.0f, 4.1f,-2.0f, 4.0f,7.2f, 0.3f,7.1f);
    private static final String imagePath = "data/leftgoal.png";

    public LeftGoal(World world) {
        super(world, goalShape);
        BodyImage image = new BodyImage(imagePath, 40f);
        addImage(image);
    }
}