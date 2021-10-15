package Entities;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Entity {
    // The image of the entity
    private Image entityImage;

    // Entity Attributes
    protected double x = Window.getWidth();
    protected double y;
    private DrawOptions entityRotation = new DrawOptions().setRotation(0);
    private final double DEFAULT_MOVE_SPEED = 3;
    private static double moveSpeed = 3;
    private Rectangle entityHitBox;

    // Entities.Entity constructor
    /**
     * Empty Constructor, primarily used to edit and control static attributes such as the Timescale/speed of all
     * entities
     */
    public Entity() {
    }

    /**
     * Constructor of an entity. Automatically  spawns an entity on the far right of the screen.
     * @param entityImage The image of the entity
     * @param y the y-location (vertical position) of the entity.
     */
    public Entity(Image entityImage, double y) {
        this.entityImage = entityImage;
        this.y = y;
    }

    /**
     * Constructor of an entity. Automatically  spawns an entity on the far right of the screen.
     * @param entityImage entityImage The image of the entity
     * @param y the y-location (vertical position) of the entity.
     * @param entityRotation the rotation of the image of the entity, in case of any upside-down entities wanted.
     */
    public Entity(Image entityImage, double y, DrawOptions entityRotation) {
        this.entityImage = entityImage;
        this.y = y;
        this.entityRotation = entityRotation;
    }

    // Getters and Setters

    /**
     * Getter used to find the point of the center of the entity
     * @return Returns a point at the exact center of the entity.
     */
    public Point getPoint() {
        return new Point(x+ (entityImage.getWidth()/2), y+(entityImage.getHeight()/2));
    }

    /**
     * Getter for the image of the entity
     * @return Returns the image of the entity
     */
    public Image getEntityImage() { return entityImage; }

    /**
     * Getter for the hit box of an entity based on the central location of the entity
     * @return Returns the hitbox of an entity
     */
    public Rectangle getHitBox() {
        entityHitBox = entityImage.getBoundingBoxAt(getPoint());
        return entityHitBox;
    }

    /**
     * This method updates an entity, having it move to the left at a constant velocity.
      */
    public void updateEntity() {
        x -= moveSpeed;
        entityImage.drawFromTopLeft(x, y, entityRotation);
        getHitBox();
    }


    // Timescale Methods

    /**
     * This method increases the speed of all entities based on a given timeScaleValue
     * @param timeScaleValue the timescale value all entities should speed up by
     */
    public void increaseEntitySpeed(double timeScaleValue) {
        moveSpeed *= timeScaleValue;
    }

    /**
     * This method decreases the speed of all entities based on a given timeScaleValue
     * @param timeScaleValue timeScaleValue the timescale value all entities should slow down by
     */
    public void decreaseEntitySpeed(double timeScaleValue) {
        moveSpeed /= timeScaleValue;
    }

    /**
     * This method resets the speed of all entities to its default
     */
    public void resetEntitySpeed() {
        moveSpeed = DEFAULT_MOVE_SPEED;
    }
}
