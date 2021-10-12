import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;

public class Entity {
    // The image of the entity
    private Image entityImage;

    // Entity Attributes
    private double x;
    private double y;
    private DrawOptions entityRotation = new DrawOptions().setRotation(0);
    private double moveSpeed = 10;

    // Entity constructor
    public Entity(Image entityImage, double x, double y) {
        this.entityImage = entityImage;
        this.x = x;
        this.y = y;
    }

    public Entity(Image entityImage, double x, double y, DrawOptions entityRotation) {
        this.entityImage = entityImage;
        this.x = x;
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
}
