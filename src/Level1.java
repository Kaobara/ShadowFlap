import bagel.Image;
import bagel.Input;
import bagel.Window;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Level1 extends Level{
    private final int LEVEL1_POINT_THRESHOLD = 30;
    private final int STARTING_LIVES = 6;
    private final int LEVEL1_PIPE_TYPES = 2;
    private final Point SHOOT_INSTRUCT_POINT = new Point((SCREEN_WIDTH-INSTRUCTIONS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2 + 68);

    // Images of Bird
    private final bagel.Image BIRD_IMAGE_UP1 = new bagel.Image("res/level-1/birdWingUp.png");
    private final bagel.Image BIRD_IMAGE_DOWN1 = new Image("res/level-1/birdWingDown.png");
//
//    // Bird
//    protected Bird bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP1, BIRD_IMAGE_DOWN1);



    // Weapons
//    private ArrayList<Weapon> Weapons = new ArrayList<Weapon>();
//    private int leastRecentWeaponNum = 0;
//    private int mostRecentWeaponNum = -1;
//    private int weaponNum = 0;
//    private Point weaponPoint;

    public Level1() {
        super();
        super.lifebar = new Lives(STARTING_LIVES);
        super.bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP1, BIRD_IMAGE_DOWN1);
        super.levelThreshold = LEVEL1_POINT_THRESHOLD;
        super.entityTimeScale.resetEntitySpeed();
    }

    protected void loseLife() {
        lifebar.loseLife();;
        bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP1, BIRD_IMAGE_DOWN1);
    }

    @Override
    public void updateStart(Input input) {
        super.updateStart(input);
        FONT.drawString("PRESS S TO SHOOT", SHOOT_INSTRUCT_POINT.x, SHOOT_INSTRUCT_POINT.y);
    }

    @Override
    public void updateRunning(Input input) {
        // Spawn Pipes
        if (frameCount % spawnSpeed == 0) {
            int rand_pipe_type = ThreadLocalRandom.current().nextInt(LEVEL1_PIPE_TYPES);
            if(rand_pipe_type == 0) {
                pipeSetArray.add(pipeSetNum, new PlasticPipe());
            } else {
                pipeSetArray.add(pipeSetNum, new SteelPipe());
            }
            pipeSetNum++;
            mostRecentPipeSetNum++;

            int rand_position = ThreadLocalRandom.current().nextInt(MAX_SPAWN, MIN_SPAWN+1);
            super.pipeSetArray.get(mostRecentPipeSetNum).spawnEntitySet(rand_position);
        }

        super.updateRunning(input);

        frameCount++;
    }
}
