import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Rectangle;

public class Weapon extends Entity{
    protected int maxRange;
    private int currentRange = 0;
    private final int SHOOT_STEP = 5;

    protected Rectangle weaponHitBox = super.getEntityImage().getBoundingBoxAt(getPoint());
    private boolean disabled = false;
    private boolean outOfScreen = false;
    private boolean isHeld = false;
    private boolean isShot = false;
    private Bird bird;

    public Weapon(Image entityImage, double y) {
        super(entityImage, y);
    }

    public void updateEntity() {
        super.updateEntity();
        weaponHitBox = getEntityImage().getBoundingBoxAt(getPoint());
    }

    public void updateHeldWeapon(Rectangle birdHitBox) {
        x = birdHitBox.right();
        y = birdHitBox.top();
        getEntityImage().drawFromTopLeft(x, y);
    }

    public void updateShootingWeapon() {
        if(isShot && !disabled) {
            if (currentRange < maxRange) {
                x += SHOOT_STEP;
                currentRange++;
                getEntityImage().draw(getPoint().x, getPoint().y);
                weaponHitBox = getEntityImage().getBoundingBoxAt(getPoint());
            } else {
                disableWeapon();
            }
        }
    }

    public void shootWeapon() {
        isShot = true;
    }

    public boolean isBeingShot() {
        return isShot;
    }

    public Rectangle getWeaponHitBox() {
        weaponHitBox = super.getEntityImage().getBoundingBoxAt(getPoint());
        return weaponHitBox;
    }

    public boolean checkOffScreen() {
        return outOfScreen;
    }

    public boolean checkIfHeld() {
        return isHeld;
    }

    public void disableWeapon() {
        disabled = true;
    }

    public void isOffScreen() {
        outOfScreen = true;
    }

    public void caughtWeapon() {
        isHeld = true;
    }

    public boolean checkDisabled() {
        return disabled;
    }

}
