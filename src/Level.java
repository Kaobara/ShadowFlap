import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Level {
    // Width Constants... THINK ABOUT PROGRAM-WIDE CONSTANTS!!!!
    protected final int SCREEN_WIDTH = bagel.Window.getWidth();
    protected final int SCREEN_HEIGHT = Window.getHeight();

    // Font constants
    private final int FINAL_SCORE_HEIGHT_DIFFERENCE = 75;
    protected final int INSTRUCTIONS_WIDTH = 612;
    private final int GAME_OVR_WIDTH = 287;
    private final int CONGRATS_WIDTH = 483;
    private final int FINAL_SCORE_WIDTH = 376;
    private final int FONT_SIZE = 48;
    protected final int FONT_HEIGHT = 29;
    protected final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);

    protected final Point INSTRUCTION_POINT = new Point((SCREEN_WIDTH-INSTRUCTIONS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point GAME_OVR_POINT = new Point((SCREEN_WIDTH-GAME_OVR_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point CONGRATS_POINT = new Point((SCREEN_WIDTH-CONGRATS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point FINAL_SCORE_POINT = new Point((SCREEN_WIDTH-FINAL_SCORE_WIDTH)/2,
            ((SCREEN_HEIGHT-FONT_HEIGHT)/2)+FINAL_SCORE_HEIGHT_DIFFERENCE);
    private final Point COUNTER_POINT = new Point(100, 100);

    // Scores
    protected int levelScoreThreshold;
    protected boolean passedThreshold = false;
    protected int score = 0;

    private final int PIPE_SCORE_BUFFER_RANGE = 50;

    // Timescale Attributes
    private final int MAX_TIME_SCALE = 5;
    private final double TIME_SCALE_VALUE = 1.5;
    protected Entity entityTimeScale = new Entity();
    private int currentTimeScale = 0;

    // Pipes
    protected ArrayList<EntitySet> pipeSetArray = new ArrayList<EntitySet>();
    protected int leastRecentPipeSetNum = 0;
    protected int mostRecentPipeSetNum = -1;
    protected int pipeSetNum = 0;
    protected double spawnSpeed = 100;

    // Bird
    protected final Point BIRD_SPAWN = new Point(200, 350);
    protected Bird bird;
    bagel.util.Rectangle birdHitBox1;
    bagel.util.Rectangle birdHitBox2;

    // Lives
    protected Lives lifebar;
    abstract void loseLife();


    protected int frameCount = 0;

    // Pipe Options
    protected final int MAX_SPAWN = 100;
    protected final int MID_SPAWN = 300;
    protected final int MIN_SPAWN = 500;



    public Level() {
    }

    public int getLives() {
        return lifebar.getLives();
    }

    public boolean isPassedThreshold() {
        return passedThreshold;
    }

    // Timescale Options


    public void increaseTimeScale() {
        if(currentTimeScale<MAX_TIME_SCALE) {
            entityTimeScale.increaseEntitySpeed(TIME_SCALE_VALUE);
            spawnSpeed = spawnSpeed / TIME_SCALE_VALUE;
            spawnSpeed = Math.round(spawnSpeed);
            currentTimeScale++;
        }
    }

    public void decreaseTimeScale() {
        if(currentTimeScale>0) {
            entityTimeScale.decreaseEntitySpeed(TIME_SCALE_VALUE);
            spawnSpeed = spawnSpeed * TIME_SCALE_VALUE;
            spawnSpeed = Math.round(spawnSpeed);
            currentTimeScale--;
        }
    }

    public void updateStart(Input input) {
        FONT.drawString("PRESS SPACE TO START", INSTRUCTION_POINT.x, INSTRUCTION_POINT.y);
    }

    public void updateRunning(Input input) {
        // A constant score.
        FONT.drawString("SCORE:" + score, COUNTER_POINT.x, COUNTER_POINT.y);
        lifebar.drawLives();

        // Inputs for actual flying motion
        if (input.wasPressed(Keys.SPACE)) {
            bird.setInitialFlyVelocity();
        }

        if (bird.getVelocity() < 0) {
            bird.fly();
        } else {
            bird.fall();
        }

        birdHitBox1 = bird.getBirdImageUp().getBoundingBoxAt(bird.getPoint());
        birdHitBox2 = bird.getBirdImageDown().getBoundingBoxAt(bird.getPoint());

        // If bird goes out of bounds game over
        // Lower bound
        if (birdHitBox1.top() > SCREEN_HEIGHT || birdHitBox2.top() > SCREEN_HEIGHT) {
            loseLife();
        }
        // Upper bound
        if (birdHitBox1.bottom() < 0 || birdHitBox2.bottom() < 0) {
            loseLife();
        }

        // Updating/Moving pipes
        if(leastRecentPipeSetNum<=mostRecentPipeSetNum) {
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!pipeSetArray.get(i).isBroken()) {
                    pipeSetArray.get(i).updateEntitySet();
                }
            }

            if (pipeSetArray.get(leastRecentPipeSetNum).getUpHitBox().right() < 0) {
                pipeSetArray.get(leastRecentPipeSetNum).setOutOfScreen();
                leastRecentPipeSetNum++;
            }

            //Scoring condition
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!pipeSetArray.get(i).isBroken()) {
                    if ((birdHitBox1.centre().x < pipeSetArray.get(i).getUpHitBox().right()
                            && birdHitBox1.centre().x > pipeSetArray.get(i).getUpHitBox().left() + PIPE_SCORE_BUFFER_RANGE)
                            || (birdHitBox2.centre().x < pipeSetArray.get(i).getUpHitBox().right()
                            && birdHitBox2.centre().x > pipeSetArray.get(i).getUpHitBox().left() + PIPE_SCORE_BUFFER_RANGE)) {
                        score++;
                    }
                }
            }

            // If hitboxes collide, game over
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!pipeSetArray.get(i).isBroken()) {
                    if (birdHitBox1.intersects(pipeSetArray.get(i).getUpHitBox())
                            || birdHitBox1.intersects(pipeSetArray.get(i).getDownHitBox())
                            || birdHitBox2.intersects(pipeSetArray.get(i).getUpHitBox())
                            || birdHitBox2.intersects(pipeSetArray.get(i).getDownHitBox())) {
                        loseLife();
                        pipeSetArray.get(i).breaks();
                        leastRecentPipeSetNum++;
                    }
                }
            }

            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if(pipeSetArray.get(i) instanceof SteelPipe) {
                    if(((SteelPipe) pipeSetArray.get(i)).getFramesOnScreen()%SteelPipe.getSPAWN_FLAME_SPEED() == 0) {
                        ((SteelPipe) pipeSetArray.get(i)).spawnFlames();
                        if (birdHitBox1.intersects(((SteelPipe) pipeSetArray.get(i)).getFlameUpHitBox()) ||
                                birdHitBox1.intersects(((SteelPipe) pipeSetArray.get(i)).getFlameDownHitBox()) ||
                                birdHitBox2.intersects(((SteelPipe) pipeSetArray.get(i)).getFlameUpHitBox()) ||
                                birdHitBox2.intersects(((SteelPipe) pipeSetArray.get(i)).getFlameDownHitBox())) {
                            loseLife();
                            pipeSetArray.get(i).breaks();
                            leastRecentPipeSetNum++;
                        }
                    }
                }
            }
        }

        if(score == levelScoreThreshold) {
            passedThreshold = true;
        }
    }

    public void updateLoseEnd(Input input) {
        FONT.drawString("GAME OVER", GAME_OVR_POINT.x,GAME_OVR_POINT.y);
        FONT.drawString("FINAL SCORE: " + score, FINAL_SCORE_POINT.x, FINAL_SCORE_POINT.y);
    }

    public void updateWinEnd(Input input) {
        FONT.drawString("CONGRATULATIONS", CONGRATS_POINT.x,CONGRATS_POINT.y);
    }


}
