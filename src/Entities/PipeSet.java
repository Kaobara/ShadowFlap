package Entities;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

public class PipeSet {
    private Boolean broken = false;
    private Boolean outOfScreen = false;

    private final int SCREEN_HEIGHT = Window.getHeight();
    protected final DrawOptions ROTATE_UPSIDE_DOWN = new DrawOptions().setRotation(Math.PI);

    protected int ySpawn;
    protected Image entityImage;
    private final int PIPE_GAP = 168;
    private Entity entityUp;
    private Entity entityDown;

    protected Rectangle entityUpHitBox;
    protected Rectangle entityDownHitBox;

    public Rectangle getUpHitBox() {
        return entityUpHitBox;
    }
    public Rectangle getDownHitBox() {
        return entityDownHitBox;
    }
    public void setOutOfScreen() {
        outOfScreen = true;
    }

    public boolean isBroken() {
        return broken;
    }
    public boolean isOutOfScreen() { return outOfScreen; }

    public void spawnEntitySet(int ySpawn) {
        entityUp = new Entity(entityImage, -SCREEN_HEIGHT+ySpawn);
        entityDown = new Entity(entityImage, ySpawn+PIPE_GAP, ROTATE_UPSIDE_DOWN);
    }

    public void increaseMoveSpeed() {
        entityUp.increaseSpeed();
        entityDown.increaseSpeed();
    }

    public void updateEntitySet() {
        entityUp.updateEntity();
        entityDown.updateEntity();
        entityUpHitBox = entityUp.getHitBox();
        entityDownHitBox = entityDown.getHitBox();
    }

    public void breaks() {
        broken = true;
    }
}
