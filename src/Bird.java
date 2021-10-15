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

    /**
     * This is a constructor for the bird
     * @param x This is the initial x position of the bird whilst spawning
     * @param y This is the initial y position of the bird whilst spawning
     * @param birdImageUp This is the image of the bird whilst its wings are up
     * @param birdImageDown This is the image of the bird whilst its wings are down
     */
    public Bird(double x, double y, Image birdImageUp, Image birdImageDown) {
        this.x = x;
        this.y = y;
        this.BIRD_IMAGE_UP = birdImageUp;
        this.BIRD_IMAGE_DOWN = birdImageDown;
    }

    // Getters and Setters for attributes of the bird

    /**
     * Getter used to get the image of the bird whilst its wings are up
     * @return Returns the image of bird with wings up
     */
    public Image getBirdImageUp() {
        return BIRD_IMAGE_UP;
    }
    /**
     * Getter used to get the image of the bird whilst its wings are down
     * @return Returns the image of bird with wings down
     */
    public Image getBirdImageDown() {
        return BIRD_IMAGE_DOWN;
    }

    /**
     * Getter that checks whether a bird's wings are up or down
     * @return Returns the bird's wings state
     */
    public boolean getIsUp() {
        return isUp;
    }

    // Methods for Bird Physics

    /**
     * This method is used to find the bird's current position
     * @return Returns a point of the bird's center
     */
    public Point getPoint() {
        return new Point(x, y);
    }

    /**
     * This method is used to find the bird's current velocity
     * @return Returns the current velocity of the bird
     */
    public double getVelocity() {
        return velocity;
    }

    // Methods regarding Equipped Items

    /**
     * This is a getter to find whether a bird is equipped with an item
     * @return Returns a boolean if a bird is equiiped with an item
     */
    public boolean isEquipped() {
        return equippedItem;
    }

    /**
     * This is a setter for when a bird equips an item
     */
    public void equip() {
        equippedItem = true;
    }

    /**
     * This is a setter for when a bird unequips an item (by shooting)
     */
    public void unequip() {
        equippedItem = false;
    }

    // Method for when the bird "flies" when space key is inputted

    /**
     * This is a setter for when a bird flies upwards
     */
    public void setInitialFlyVelocity() {
        velocity = INITIAL_FLY_VELOCITY;
    }

    /**
     * This method is used for the bird whilst is drawn with its wings down
     */
    public void fall() {
        y += velocity;
        if(velocity < MAX_FALL_SPEED) {
            velocity += GRAVITY;
        }
        isUp = false;
        BIRD_IMAGE_DOWN.draw(x, y);
    }

    /**
     * This method is used for the bird whilst is drawn with its wings up
     */
    public void fly() {
        y += velocity;
        if(velocity < MAX_FALL_SPEED) {
            velocity += GRAVITY;
        }
        isUp = true;
        BIRD_IMAGE_UP.draw(x, y);
    }
}
