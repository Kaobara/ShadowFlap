import bagel.*;
import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import  bagel.util.Vector2;

public class Bird {
    // Images of the bird
    private final Image BIRD_IMAGE_UP;
    private final Image BIRD_IMAGE_DOWN;

    // Position Coordinates
    private double x;
    private double y;

    // Check if it's currently up or down
    private boolean isUp = false;

    // Acceleration and Velocity attributes of the bird
    private final double MAX_FALL_SPEED = 10;
    private final double GRAVITY = 0.4;
    private final double INITIAL_FLY_VELOCITY = -6;
    private double velocity=0;

    // Weapons
    private boolean equippedItem = false;

    // Constructors
    public Bird(double x, double y, Image birdImageUp, Image birdImageDown) {
        this.x = x;
        this.y = y;
        this.BIRD_IMAGE_UP = birdImageUp;
        this.BIRD_IMAGE_DOWN = birdImageDown;
    }

    // Getters and Setters for attributes of the bird
    public Image getBirdImageUp() {
        return BIRD_IMAGE_UP;
    }
    public Image getBirdImageDown() {
        return BIRD_IMAGE_DOWN;
    }
    public boolean getIsUp() {
        return isUp;
    }

    // Methods for Bird Physics
    public Point getPoint() {
        return new Point(x, y);
    }
    public double getVelocity() {
        return velocity;
    }

    // Methods regarding Equipped Items
    public boolean isEquipped() {
        return equippedItem;
    }
    public void equip() {
        equippedItem = true;
    }
    public void unequip() {
        equippedItem = false;
    }

    // Method for when the bird "flies" when space key is inputted
    public void setInitialFlyVelocity() {
        velocity = INITIAL_FLY_VELOCITY;
    }

    // Method for gravity downwards acceleration
    public void fall() {
        y += velocity;
        if(velocity < MAX_FALL_SPEED) {
            velocity += GRAVITY;
        }
        isUp = false;
        BIRD_IMAGE_DOWN.draw(x, y);
    }

    // Method for drawing the bird with wings up
    public void fly() {
        isUp = true;
        BIRD_IMAGE_UP.draw(x, y);
        y += velocity;
        velocity += GRAVITY;
    }
}
