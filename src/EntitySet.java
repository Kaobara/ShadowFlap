import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

public class EntitySet {
    private final Rectangle BROKEN_REC = new Rectangle(20, 0, 0, 0);
    private Boolean broken = false;
    private Boolean outOfScreen = false;

    private final int SCREEN_HEIGHT = Window.getHeight();
    private final DrawOptions ROTATE_UPSIDE_DOWN = new DrawOptions().setRotation(Math.PI);

    protected int ySpawn;
    protected Image entityImage;
    protected int gap;
    private Point entitySetPoint;
    private Entity entityUp;
    private Entity entityDown;

    private Rectangle entityUpHitBox;
    private Rectangle entityDownHitBox;

    public Rectangle getUpHitBox() {
        return entityUpHitBox;
    }
    public Rectangle getDownHitBox() {
        return entityDownHitBox;
    }
    public void setOutOfScreen() {
        outOfScreen = true;
    }

    public boolean getIsBroken() {
        return broken;
    }

    public void spawnEntitySet(int ySpawn) {
        entityUp = new Entity(entityImage, -SCREEN_HEIGHT+ySpawn);
        entityDown = new Entity(entityImage, ySpawn+gap, ROTATE_UPSIDE_DOWN);
    }

    public void increaseMoveSpeed() {
        entityUp.increaseSpeed();
        entityDown.increaseSpeed();
    }

    public void updateEntitySet() {
        entityUp.updateEntity();
        entityDown.updateEntity();
        entityUpHitBox = entityUp.getEntityImage().getBoundingBoxAt(entityUp.getPoint());
        entityDownHitBox = entityDown.getEntityImage().getBoundingBoxAt(entityDown.getPoint());
        if(broken == false) {
        } else {
            entityUpHitBox = BROKEN_REC;
            entityDownHitBox = BROKEN_REC;
        }
    }

    public void breaks() {
        broken = true;
    }
}
