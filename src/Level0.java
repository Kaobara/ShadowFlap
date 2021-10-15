import Entities.PlasticPipe;
import bagel.Image;
import bagel.Input;

import java.util.concurrent.ThreadLocalRandom;

public class Level0 extends Level {
    private static final Image BACKGROUND_IMAGE0 = new Image("res/level-0/background.png");
    private final int LEVEL0_POINT_THRESHOLD = 10;
    private final int STARTING_LIVES = 3;
    private final int LEVEL0_SPAWN_OPTIONS = 3;

    // Bird Images
    private final bagel.Image BIRD_IMAGE_UP0 = new bagel.Image("res/level-0/birdWingUp.png");
    private final bagel.Image BIRD_IMAGE_DOWN0 = new Image("res/level-0/birdWingDown.png");

    /**
     * This method is a constructor for Level 0, setting up most of the required attributes of the level
     */
    public Level0() {
        super.lifebar = new Lives(STARTING_LIVES);
        super.bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP0, BIRD_IMAGE_DOWN0);
        super.levelScoreThreshold = LEVEL0_POINT_THRESHOLD;
        super.entityTimeScale.resetEntitySpeed();
        super.backgroundImage = BACKGROUND_IMAGE0;
    }

    protected void loseLifeSpawnBird() {
        lifebar.loseLife();
        bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP0, BIRD_IMAGE_DOWN0);
    }

    /**
     * This method is the level0-specific version for updating the game whilst it is running.
     * It has special specifications for spawning pipes (specifically, only plastic pipes, and only in 3 specific
     * positions)
     * @param input this variable is the input given by the user
     */
    public void updateRunning(Input input) {
        // Spawn Plastic Pipes at three predetermined positions
        if (frameCount % spawnSpeed == 0) {
            pipeSetArray.add(pipeSetNum, new PlasticPipe());
            pipeSetNum++;
            mostRecentPipeSetNum++;
            int rand_position = ThreadLocalRandom.current().nextInt(LEVEL0_SPAWN_OPTIONS);
            if(rand_position==0) {
                super.pipeSetArray.get(mostRecentPipeSetNum).spawnPipeSet(MAX_SPAWN);
            } else if (rand_position==1) {
                super.pipeSetArray.get(mostRecentPipeSetNum).spawnPipeSet(MID_SPAWN);
            } else {
                super.pipeSetArray.get(mostRecentPipeSetNum).spawnPipeSet(MIN_SPAWN);
            }
        }
        super.updateRunning(input);
        frameCount++;
    }
}