package game;

import city.cs.engine.*;

public class RightGoal extends StaticBody {
    private static final Shape goalShape = new PolygonShape(1.2f,7.2f, 4.6f,7.2f, 5.8f,-1.9f, 1.2f,-2.0f, 1.2f,7.2f);
    private static final String imagePath = "data/rightgoal.png";

    public RightGoal(World world) {
        super(world, goalShape);
        BodyImage image = new BodyImage(imagePath, 40f);
        addImage(image);
    }
}