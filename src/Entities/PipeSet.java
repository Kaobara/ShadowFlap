package Entities;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

public class PipeSet {
    // Booleans for checking pipeset states
    protected Boolean broken = false;
    private Boolean outOfScreen = false;
    
    // Constants and attributes for drawing pipes
    private final int SCREEN_HEIGHT = Window.getHeight();
    protected final DrawOptions ROTATE_UPSIDE_DOWN = new DrawOptions().setRotation(Math.PI);    
    private final int PIPE_GAP = 168;
    
    // Attributes for the pipe entities
    protected Image entityImage;
    protected int ySpawn;
    private Entity entityUp;
    private Entity entityDown;
    protected Rectangle entityUpHitBox;
    protected Rectangle entityDownHitBox;
    
    // Setters and Getters

    /**
     * Setter, setting a pipeSet as "broken"
     */
    public void breaks() {
        broken = true;
    }

    /**
     * Getter, checking if a pipeset is broken
     * @return Returns the broken/not-broken state of the pipe
     */
    public boolean isBroken() {
        return broken;
    }
    
    /**
     * Setter for making a pipeSet "out of screen"
     */
    public void setOutOfScreen() {
        outOfScreen = true;
    }

    /**
     * Getter, checking if a pipeSet is in or out of screen
     * @return Returns the in-screen state of the pipe
     */
    public boolean isOutOfScreen() { return outOfScreen; }
    
    /**
     * Getter for the hit box of the upper pipe
     * @return the hit box of upper pipe
     */
    public Rectangle getUpHitBox() {
        return entityUpHitBox;
    }

    /**
     * Getter for the hit box of the lower pipe
     * @returnthe hit box of lower pipe
     */
    public Rectangle getDownHitBox() {
        return entityDownHitBox;
    }

    /**
     * This method spawns in a pipeset from the very right of the screen
     * @param ySpawn This attribute is the y coordinate in which the pipes should spawn in
     */
    public void spawnPipeSet(int ySpawn) {
        entityUp = new Entity(entityImage, -SCREEN_HEIGHT+ySpawn);
        entityDown = new Entity(entityImage, ySpawn+PIPE_GAP, ROTATE_UPSIDE_DOWN);
    }

    /**
     * This method updates the pipeset, moving it to the left in a constant velocity
     */
    public void updatePipeSet() {
        entityUp.updateEntity();
        entityDown.updateEntity();
        entityUpHitBox = entityUp.getHitBox();
        entityDownHitBox = entityDown.getHitBox();
    }
}
