import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Rectangle;

public class Level0 extends Level {
    private final int LEVEL0_POINT_THRESHOLD = 10;
    private final int STARTING_LIVES = 3;
    private final int LEVELNUM = 0;
    private final int LEVEL0_SPAWN_OPTIONS = 3;

    // Bird Images
    private final bagel.Image BIRD_IMAGE_UP0 = new bagel.Image("res/level-0/birdWingUp.png");
    private final bagel.Image BIRD_IMAGE_DOWN0 = new Image("res/level-0/birdWingDown.png");

    // Bird
    protected Bird bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP0, BIRD_IMAGE_DOWN0);

    public Level0() {
        super();
        super.lifebar = new Lives(STARTING_LIVES);
    }

    private void loseLife() {
        lifebar.loseLife();;
        bird = new Bird(BIRD_SPAWN.x, BIRD_SPAWN.y, BIRD_IMAGE_UP0, BIRD_IMAGE_DOWN0);
    }

    public void updateRunning(Input input) {
        super.updateRunning(input);
        if (frameCount % spawnSpeed == 0) {
            pipeSetArray.add(pipeSetNum, new PlasticPipe(LEVELNUM));
            pipeSetNum++;
            mostRecentPipeSetNum++;

            int rand_position = super.rand.nextInt(LEVEL0_SPAWN_OPTIONS);
            if(rand_position==0) {
                super.pipeSetArray.get(mostRecentPipeSetNum).spawnEntitySet(MAX_SPAWN);
            } else if (rand_position==1) {
                super.pipeSetArray.get(mostRecentPipeSetNum).spawnEntitySet(MID_SPAWN);
            } else {
                super.pipeSetArray.get(mostRecentPipeSetNum).spawnEntitySet(MIN_SPAWN);
            }
        }

        for(int i = super.leastRecentPipeSetNum; i<=super.mostRecentPipeSetNum; i++) {
            if(!pipeSetArray.get(i).getIsBroken()) {
                super.pipeSetArray.get(i).updateEntitySet();
            }
        }

        if(pipeSetArray.get(leastRecentPipeSetNum).getUpHitBox().right() < 0) {
            pipeSetArray.get(leastRecentPipeSetNum).setOutOfScreen();
            leastRecentPipeSetNum++ ;
        }

        Rectangle birdHitBox1 = bird.getBirdImageUp().getBoundingBoxAt(bird.getPoint());
        Rectangle birdHitBox2 = bird.getBirdImageDown().getBoundingBoxAt(bird.getPoint());

        // Inputs for actual flying motion
        if(input.wasPressed(Keys.SPACE)) {
            bird.setInitialFlyVelocity();
        }

        if(bird.getVelocity() < 0) {
            bird.fly();
        } else {
            bird.fall();
        }

        //Win condition
        for(int i = super.leastRecentPipeSetNum; i<=super.mostRecentPipeSetNum; i++) {
            if(!pipeSetArray.get(i).getIsBroken()) {
                if (birdHitBox1.centre().x < pipeSetArray.get(i).getUpHitBox().right() && birdHitBox1.centre().x > pipeSetArray.get(i).getUpHitBox().left() + 50
                        || birdHitBox2.centre().x < pipeSetArray.get(i).getUpHitBox().right() && birdHitBox2.centre().x > pipeSetArray.get(i).getUpHitBox().left() + 50) {
                    score++;
                }
            }
        }

        //Losing Lives
        // If bird goes out of bounds game over
        // Lower bound
        if(birdHitBox1.top()>SCREEN_HEIGHT|| birdHitBox2.top()>SCREEN_HEIGHT) {
            loseLife();
        }
        // Upper bound
        if(birdHitBox1.bottom()<0|| birdHitBox2.bottom()<0) {
            loseLife();
        }

        // If hitboxes collide, game over
        for(int i = super.leastRecentPipeSetNum; i<=super.mostRecentPipeSetNum; i++) {
            if(!pipeSetArray.get(i).getIsBroken()) {
                if (birdHitBox1.intersects(pipeSetArray.get(i).getUpHitBox()) || birdHitBox1.intersects(pipeSetArray.get(i).getDownHitBox())
                        || birdHitBox2.intersects(pipeSetArray.get(i).getUpHitBox()) || birdHitBox2.intersects(pipeSetArray.get(i).getDownHitBox())) {
                    loseLife();
                    pipeSetArray.get(i).breaks();
                }
            }
        }

        if(score == 10) {
            passedThreshold = true;
        }

        frameCount++;
    }
}