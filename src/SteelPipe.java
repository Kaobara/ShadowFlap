import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class SteelPipe extends EntitySet{
    private int framesOnScreen = 0;
    private static final int SPAWN_FLAME_SPEED = 20;

    private final int PIPE_GAP = 168;
    private final Image PIPE_IMAGE = new Image("res/level-1/steelPipe.png");

    private final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private final double FLAME_HEIGHT = FLAME_IMAGE.getHeight();
    private final double FLAME_WIDTH = FLAME_IMAGE.getWidth();
    private Rectangle flameUpHitBox;
    private Rectangle flameDownHitBox;

    public SteelPipe() {
        super.entityImage = PIPE_IMAGE;
        super.gap = PIPE_GAP;
    }

    public Rectangle getFlameUpHitBox() { return flameUpHitBox; }

    public Rectangle getFlameDownHitBox() { return flameDownHitBox; }

    @Override
    public void updateEntitySet() {
        super.updateEntitySet();
        if(!isBroken() && !isOutOfScreen()) {
            framesOnScreen ++;
        }
    }

    public int getFramesOnScreen() { return framesOnScreen; }

    public static int getSPAWN_FLAME_SPEED() { return SPAWN_FLAME_SPEED; }

    public void spawnFlames() {
        FLAME_IMAGE.drawFromTopLeft(entityUpHitBox.left(), entityUpHitBox.bottom(), ROTATE_UPSIDE_DOWN);
        flameUpHitBox = FLAME_IMAGE.getBoundingBoxAt(new Point(entityUpHitBox.left() + (FLAME_WIDTH/2),
                entityUpHitBox.bottom()+(FLAME_HEIGHT/2)));
        FLAME_IMAGE.drawFromTopLeft(entityDownHitBox.left(), entityDownHitBox.top()-FLAME_HEIGHT);
        flameDownHitBox = FLAME_IMAGE.getBoundingBoxAt(new Point(entityDownHitBox.left() + (FLAME_WIDTH/2),
                entityDownHitBox.top()-(FLAME_HEIGHT/2)));
    }
}
