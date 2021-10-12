import bagel.Input;
import bagel.Keys;
import bagel.util.Rectangle;

public class Level0 extends Level {
    private final int LEVEL0_POINT_THRESHOLD = 10;
    private final int STARTING_LIVES = 3;
    private final int LEVELNUM = 0;

    public Level0(Bird bird) {
        super(bird);
        super.lifebar = new Lives(STARTING_LIVES);
    }

    public void updateRunning(Input input) {
        super.updateRunning(input);
        if (frameCount % spawnSpeed == 0) {
            pipeSetArray.add(pipeSetNum, new PlasticPipe(LEVELNUM));
            pipeSetNum++;
            mostRecentPipeSetNum++;
        }

        // Inputs for actual flying motion
        if(input.wasPressed(Keys.SPACE)) {
            bird.setInitialFlyVelocity();
        }

        if(bird.getVelocity() < 0) {
            bird.fly();
        } else {
            bird.fall();
        }
    }
}
