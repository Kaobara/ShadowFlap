import bagel.Image;

import java.util.Random;

public class PlasticPipe extends EntitySet{
    private final int PIPE_GAP = 168;
    private final Image PIPE_IMAGE = new Image("res/level/plasticPipe.png");
    private int currentLevel;
    Random rand = new Random();
    private final int LEVEL0_SPAWN_OPTIONS = 3;

    // Pipe Options
    protected final int MAX_SPAWN = 100;
    protected final int MID_SPAWN = 300;
    protected final int MIN_SPAWN = 500;

    public PlasticPipe(int currentLevel) {
        super.entityImage = PIPE_IMAGE;
        super.gap = PIPE_GAP;
        if(currentLevel == 0) {
            int rand_position = rand.nextInt(LEVEL0_SPAWN_OPTIONS);
            if(rand_position==0) {
                spawnEntitySet(MAX_SPAWN);
            } else if (rand_position==1) {
                spawnEntitySet(MID_SPAWN);
            } else {
                spawnEntitySet(MIN_SPAWN);
            }
        }
    }
}
