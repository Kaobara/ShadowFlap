package Entities;

import bagel.Image;
import bagel.util.Rectangle;

public class Weapon extends Entity{
    protected int maxRange;
    private int currentRange = 0;
    private final int SHOOT_STEP = 5;

    private boolean disabled = false;
    private boolean isHeld = false;
    private boolean isShot = false;

    // Getters and Setters
    /**
     * Setter that causes the Weapon to be in a "caught" state
     */
    public void caughtWeapon() {
        isHeld = true;
    }

    /**
     * Getter that checks if a Weapon is in a "Held" state
     * @return returns the "held" state of the weapon
     */
    public boolean checkIfHeld() {
        return isHeld;
    }

    /**
     * Setter that causes the Weapon to be in a "shooting" state
     */
    public void shootWeapon() {
        isShot = true;
    }

    /**
     * Getter that checks if a Weapon is in a "Shooting" state
     * @return returns the "shooting" state of the weapon
     */
    public boolean isBeingShot() {
        return isShot;
    }

    /**
     * Setter that disables a Weapon
     */
    public void disableWeapon() {
        disabled = true;
    }

    /**
     * Getter that checks if a weapon is disabled
     * @return This returns the disable/enabled state of a weapon
     */
    public boolean checkDisabled() {
        return disabled;
    }

    // Other Methods
    /**
     * This is the constructor of the weapon
     * @param entityImage This is the image of the entity
     * @param y This is the initial y-position of the entity
     */
    public Weapon(Image entityImage, double y) {
        super(entityImage, y);
    }

    /**
     * This method updates the position and draws the weapon that is being held based on the position
     * of the bird that is holding it.
     * @param birdHitBox The first parameter that is used to find the position of the weapon.
     */
    public void updateHeldWeapon(Rectangle birdHitBox) {
        x = birdHitBox.right();
        y = birdHitBox.top();
        getEntityImage().drawFromTopLeft(x, y);
    }

    /**
     * This method updates the position and hit box, as well as draws the weapon whilst it is being shot.
     * If the weapon has already travelled to it's maximum range, the weapon automatically gets disabled.
     */
    public void updateShootingWeapon() {
        if(isShot && !disabled) {
            if (currentRange < maxRange) {
                x += SHOOT_STEP;
                currentRange++;
                getEntityImage().draw(getPoint().x, getPoint().y);
                getHitBox();
            } else {
                disableWeapon();
            }
        }
    }



}
