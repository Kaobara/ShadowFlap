import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;

public class Entity {
    // The image of the entity
    private Image entityImage;

    // Entity Attributes
    private double x = Window.getWidth();
    private double y;
    private DrawOptions entityRotation = new DrawOptions().setRotation(0);
    private static double moveSpeed = 10;

    // Entity constructor
    public Entity() {
    }

    public Entity(Image entityImage, double y) {
        this.entityImage = entityImage;
        this.y = y;
    }

    public Entity(Image entityImage, double y, DrawOptions entityRotation) {
        this.entityImage = entityImage;
        this.y = y;
        this.entityRotation = entityRotation;
    }

    // Getters and Setters
    public Point getPoint() {
        return new Point(x+ (entityImage.getWidth()/2), y+(entityImage.getHeight()/2));
    }

    public Image getEntityImage() { return entityImage; }

    public void increaseSpeed() {
        moveSpeed *= 1.5;
    }

    // Entity moves to the left at a constant velocity.
    public void updateEntity() {
        x -= moveSpeed;
        entityImage.drawFromTopLeft(x, y, entityRotation);
    }

    // Increase speed
    public void increaseEntitySpeed() {
        moveSpeed *= 1.5;
    }
    public void decreaseEntitySpeed() {
        moveSpeed /= 1.5;
    }

    public void resetEntitySpeed() {
        moveSpeed = 10;
    }
}
