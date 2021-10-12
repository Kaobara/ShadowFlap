import bagel.*;
import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import  bagel.util.Vector2;

public class Bird {
    // Images of the bird
    private Image birdImageUp;
    private Image birdImageDown;

    // Position Coordinates
    private double x;
    private double y;

    // Acceleration and Velocity attributes of the bird
    private final double MAX_FALL_SPEED = 10;
    private final double GRAVITY = 0.4;
    private double velocity=0;

    // Constructors
    public Bird(double x, double y, Image birdImageUp, Image birdImageDown) {
        this.x = x;
        this.y = y;
        this.birdImageUp = birdImageUp;
        this.birdImageDown = birdImageDown;
    }

    // Getters for attributes of the bird
    public double getVelocity() {
        return velocity;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public Image getBirdImageDown() {
        return birdImageDown;
    }

    public Image getBirdImageUp() {
        return birdImageUp;
    }

    // Method for gravity downwards acceleration
    public void fall() {
        y += velocity;
        if(velocity < MAX_FALL_SPEED) {
            velocity += GRAVITY;
        }
        birdImageDown.draw(x, y);
    }

    // Method for drawing the bird with wings up
    public void fly() {
        birdImageUp.draw(x, y);
        y += velocity;
        velocity += GRAVITY;
    }

    // Method for when the bird "flies" when space key is inputted
    public void setInitialFlyVelocity() {
        velocity = -6;
    }
}
