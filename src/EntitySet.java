import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class EntitySet {
    private final Rectangle BROKEN_REC = new Rectangle(0, 0, 0, 0);

    private Boolean broken = false;

    private final DrawOptions ROTATE_UPSIDE_DOWN = new DrawOptions().setRotation(Math.PI);
    private Image entityImage;
    private final int PIPE_GAP = 168;

    private Point entitySetPoint;
    private Entity entityUp;
    private Entity entityDown;

    private Rectangle entityUpHitBox;
    private Rectangle entityDownHitBox;

//    public EntitySet()

    public void increaseMoveSpeed() {
        entityUp.increaseSpeed();
        entityDown.increaseSpeed();
    }

    public void updateEntitySet() {
        if(broken == false) {
            entityUp.updateEntity();
            entityDown.updateEntity();
            entityUpHitBox = entityUp.getEntityImage().getBoundingBoxAt(entityUp.getPoint());
            entityDownHitBox = entityDown.getEntityImage().getBoundingBoxAt(entityDown.getPoint());
        } else {
            entityUpHitBox = BROKEN_REC;
            entityDownHitBox = BROKEN_REC;
        }
    }
}
