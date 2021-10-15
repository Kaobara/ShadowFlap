package Entities;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class SteelPipe extends PipeSet {
    // Attributes for spawning the flames.
    private int framesOnScreen = 0;
    private static final int SPAWN_FLAME_SPEED = 20;

    // Steel pipe image
    private final Image PIPE_IMAGE = new Image("res/level-1/steelPipe.png");

    // Flame Attributes
    private final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private final double FLAME_HEIGHT = FLAME_IMAGE.getHeight();
    private final double FLAME_WIDTH = FLAME_IMAGE.getWidth();
    private Rectangle flameUpHitBox;
    private Rectangle flameDownHitBox;

    /**
     * Constructor for the Steel Pipe
     */
    public SteelPipe() {
        super.entityImage = PIPE_IMAGE;
    }

    // Getters and Setters

    /**
     * This method checks how many frames the steelPipe has been on screen (From moment it spawned until the moment
     * it is disabled)
     * @return returns the number of frames that the pipe has been on screen.
     */
    public int getFramesOnScreen() { return framesOnScreen; }

    // Getters and Setters of Flame

    /**
     * Getter that returns the hit box of the flame coming from the up direction
     * @return returns the hit box of the upper flame
     */
    public Rectangle getFlameUpHitBox() { return flameUpHitBox; }

    /**
     * Getter that returns the hit box of the flame coming from the down direction
     * @return returns the hit box of the lower flame
     */
    public Rectangle getFlameDownHitBox() { return flameDownHitBox; }

    /**
     * Getter that returns how often the flame should spawn
     * @return returns number in which the flame should spawn in
     */
    public static int getSPAWN_FLAME_SPEED() { return SPAWN_FLAME_SPEED; }

    // Update steelPipe

    /**
     * This is the constructor of the Steel Pipe.
     */
    @Override
    public void updatePipeSet() {
        super.updatePipeSet();
        if(!isBroken() && !isOutOfScreen()) {
            framesOnScreen ++;
        }
    }

    // Method of spawning flames

    /**
     * This method spawns in flames as well as additional hit boxes for those flames
     */
    public void spawnFlames() {
        if(!isBroken()) {
            FLAME_IMAGE.drawFromTopLeft(entityUpHitBox.left(), entityUpHitBox.bottom(), ROTATE_UPSIDE_DOWN);
            flameUpHitBox = FLAME_IMAGE.getBoundingBoxAt(new Point(entityUpHitBox.left() + (FLAME_WIDTH / 2),
                    entityUpHitBox.bottom() + (FLAME_HEIGHT / 2)));
            FLAME_IMAGE.drawFromTopLeft(entityDownHitBox.left(), entityDownHitBox.top() - FLAME_HEIGHT);
            flameDownHitBox = FLAME_IMAGE.getBoundingBoxAt(new Point(entityDownHitBox.left() + (FLAME_WIDTH / 2),
                    entityDownHitBox.top() - (FLAME_HEIGHT / 2)));
        }
    }

}
