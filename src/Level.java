import Entities.Entity;
import Entities.PipeSet;
import Entities.SteelPipe;
import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;

import java.awt.*;
import java.util.ArrayList;

public abstract class Level {
    // Width Constants... THINK ABOUT PROGRAM-WIDE CONSTANTS!!!!
    protected final int SCREEN_WIDTH = bagel.Window.getWidth();
    protected final int SCREEN_HEIGHT = Window.getHeight();

    // Background image
    protected Image backgroundImage;

    // Font constants
    private final int FINAL_SCORE_HEIGHT_DIFFERENCE = 75;
    protected final int INSTRUCTIONS_WIDTH = 612;
    private final int GAME_OVR_WIDTH = 287;
    private final int CONGRATS_WIDTH = 483;
    private final int FINAL_SCORE_WIDTH = 376;
    private final int FONT_SIZE = 48;
    protected final int FONT_HEIGHT = 29;
    protected final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);

    // Points Constants
    protected final Point INSTRUCTION_POINT = new Point((SCREEN_WIDTH-INSTRUCTIONS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point GAME_OVR_POINT = new Point((SCREEN_WIDTH-GAME_OVR_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point CONGRATS_POINT = new Point((SCREEN_WIDTH-CONGRATS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point FINAL_SCORE_POINT = new Point((SCREEN_WIDTH-FINAL_SCORE_WIDTH)/2,
            ((SCREEN_HEIGHT-FONT_HEIGHT)/2)+FINAL_SCORE_HEIGHT_DIFFERENCE);
    private final Point COUNTER_POINT = new Point(100, 100);

    // Scores and Thresholds
    protected int levelScoreThreshold;
    protected boolean passedThreshold = false;
    protected int score = 0;
    private int pipeScoreBufferRange = 63;
    private final int LEVEL_UP_FRAMES = 20;

    // Timescale Attributes
    private final int MAX_TIME_SCALE = 5;
    private final double TIME_SCALE_VALUE = 1.5;
    private int currentTimeScale = 0;
    private int pipeScoreBufferTimeIncrement = 3;
    protected Entity entityTimeScale = new Entity();
    protected int frameCount = 0;

    // Pipes
    protected ArrayList<PipeSet> pipeSetArray = new ArrayList<PipeSet>();
    protected int leastRecentPipeSetNum = 0;
    protected int mostRecentPipeSetNum = -1;
    protected int pipeSetNum = 0;
    protected double spawnSpeed = 100;

    // Bird
    private final int BIRD_FLAP_FRAME = 10;
    protected final Point BIRD_SPAWN = new Point(200, 350);
    protected Bird bird;
    bagel.util.Rectangle birdHitBox1;
    bagel.util.Rectangle birdHitBox2;

    // Lives
    protected Lives lifebar;
    abstract void loseLifeSpawnBird();

    // Pipe Options
    protected final int MAX_SPAWN = 100;
    protected final int MID_SPAWN = 300;
    protected final int MIN_SPAWN = 500;

    /**
     * This method forwards the amount of lives that is remaining in a specific instance of a level
     * @return Returns the remaining lives of a level
     */
    public int getLives() {
        return lifebar.getLives();
    }

    /**
     * This method checks whether or not the score of a level has passed the level's score threshold
     * @return Returns whether it passed the level's score threshold or not
     */
    public boolean isPassedThreshold() {
        return passedThreshold;
    }

    // Timescale Options

    /**
     * This method increases the timescale, changing the spawnspeed, entity movement speed, and attempts to correct
     * the changes in scoring
     */
    public void increaseTimeScale() {
        if(currentTimeScale<MAX_TIME_SCALE) {
            entityTimeScale.increaseEntitySpeed(TIME_SCALE_VALUE);
            spawnSpeed = spawnSpeed / TIME_SCALE_VALUE;
            spawnSpeed = Math.round(spawnSpeed);
            currentTimeScale++;
            pipeScoreBufferRange -= pipeScoreBufferTimeIncrement;
        }
    }

    /**
     * This method decreases the timescale, changing the spawnspeed, entity movement speed, and attempts to correct
     * the changes in scoring
     */
    public void decreaseTimeScale() {
        if(currentTimeScale>0) {
            entityTimeScale.decreaseEntitySpeed(TIME_SCALE_VALUE);
            spawnSpeed = spawnSpeed * TIME_SCALE_VALUE;
            spawnSpeed = Math.round(spawnSpeed);
            currentTimeScale--;
            pipeScoreBufferRange += pipeScoreBufferTimeIncrement;
        }
    }

    /**
     * Setter that resets the frameCount of a level back to 0
     */
    public void resetFrames() {
        frameCount = 0;
    }

    /**
     * Getter that checks how many frames has passed whilst running a level
     * @return Returns the framecount of an instance level
     */
    public int getFrames() { return frameCount; }

    /**
     * Getter that returns how many frames the "Level Up" screen should be up
     * @return the number of frames for a "Level Up" screen
     */
    public int getLEVEL_UP_FRAMES() { return LEVEL_UP_FRAMES; }

    /**
     * This method updates the game with an image of the background of a level
     */
    public void updateBackground() {
        backgroundImage.draw(SCREEN_WIDTH/2.0, SCREEN_HEIGHT/2.0);
    }

    /**
     * This method updates the game in a "Start Screen"
     */
    public void updateStart() {
        FONT.drawString("PRESS SPACE TO START", INSTRUCTION_POINT.x, INSTRUCTION_POINT.y);
    }

    /**
     * This method updates the game whilst it is "running", or in a "playnig" state
     * @param input this variable is the input given by the user
     */
    public void updateRunning(Input input) {
        // When SPACE is pressed, the bird "flies" upwards
        if (input.wasPressed(Keys.SPACE)) {
            bird.setInitialFlyVelocity();
        }

        // For aesthetic. Every 10 frames (BIRD_FLAP_FRAME), the bird is drawn with its wings up
        if (frameCount%BIRD_FLAP_FRAME == 0) {
            bird.fly();
        } else {
            bird.fall();
        }

        // Get the hit boxes of the bird
        birdHitBox1 = bird.getBirdImageUp().getBoundingBoxAt(bird.getPoint());
        birdHitBox2 = bird.getBirdImageDown().getBoundingBoxAt(bird.getPoint());

        // If bird goes out of bounds game over
        // Lower bound
        if (birdHitBox1.top() > SCREEN_HEIGHT || birdHitBox2.top() > SCREEN_HEIGHT) {
            loseLifeSpawnBird();
        }
        // Upper bound
        if (birdHitBox1.bottom() < 0 || birdHitBox2.bottom() < 0) {
            loseLifeSpawnBird();
        }

        // Updating/Moving pipes
        if(leastRecentPipeSetNum<=mostRecentPipeSetNum) {
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!pipeSetArray.get(i).isBroken()) {
                    pipeSetArray.get(i).updatePipeSet();
                }
            }

            // If a pipe is out of the screen, set it as "out Of screen"
            if (pipeSetArray.get(leastRecentPipeSetNum).getUpHitBox().right() < 0) {
                pipeSetArray.get(leastRecentPipeSetNum).setOutOfScreen();
                leastRecentPipeSetNum++;
            }

            //Scoring condition
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!pipeSetArray.get(i).isBroken()) {
                    if ((birdHitBox1.centre().x < pipeSetArray.get(i).getUpHitBox().right()
                            && birdHitBox1.centre().x > pipeSetArray.get(i).getUpHitBox().left() + pipeScoreBufferRange)
                            || (birdHitBox2.centre().x < pipeSetArray.get(i).getUpHitBox().right()
                            && birdHitBox2.centre().x > pipeSetArray.get(i).getUpHitBox().left() + pipeScoreBufferRange)) {
                        score++;
                    }
                }
            }

            // If hit boxes of a bird and the pipes collide, lose a life and break the pipe
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if (!pipeSetArray.get(i).isBroken()) {
                    if (birdHitBox1.intersects(pipeSetArray.get(i).getUpHitBox())
                            || birdHitBox1.intersects(pipeSetArray.get(i).getDownHitBox())
                            || birdHitBox2.intersects(pipeSetArray.get(i).getUpHitBox())
                            || birdHitBox2.intersects(pipeSetArray.get(i).getDownHitBox())) {
                        lifebar.loseLife();
                        pipeSetArray.get(i).breaks();
                        leastRecentPipeSetNum++;
                    }
                }
            }

            // If a steel pipe exists in a level, a flame is spawned from the steel pipe.
            for (int i = leastRecentPipeSetNum; i <= mostRecentPipeSetNum; i++) {
                if(pipeSetArray.get(i) instanceof SteelPipe) {
                    if(((SteelPipe) pipeSetArray.get(i)).getFramesOnScreen()%SteelPipe.getSPAWN_FLAME_SPEED() == 0) {
                        ((SteelPipe) pipeSetArray.get(i)).spawnFlames();

                        // If a bird is hit by the flames, break the pipe and lose a life.
                        if (birdHitBox1.intersects(((SteelPipe) pipeSetArray.get(i)).getFlameUpHitBox()) ||
                                birdHitBox1.intersects(((SteelPipe) pipeSetArray.get(i)).getFlameDownHitBox()) ||
                                birdHitBox2.intersects(((SteelPipe) pipeSetArray.get(i)).getFlameUpHitBox()) ||
                                birdHitBox2.intersects(((SteelPipe) pipeSetArray.get(i)).getFlameDownHitBox())) {
                            pipeSetArray.get(i).breaks();
                            lifebar.loseLife();
                            leastRecentPipeSetNum++;
                        }
                    }
                }
            }
        }

        // Drawing the lives and the score
        FONT.drawString("SCORE:" + score, COUNTER_POINT.x, COUNTER_POINT.y);
        lifebar.drawLives();

        // For score threshold
        if(score == levelScoreThreshold) {
            passedThreshold = true;
        }
    }

    /**
     * This method updates the game whilst it is in the "Level Up" state
     */
    public void updateLevelUp() {
        FONT.drawString("LEVEL-UP!", GAME_OVR_POINT.x,GAME_OVR_POINT.y);
        frameCount++;
    }

    /**
     * This method updates the game whilst it is in a "Lose" state
     */
    public void updateLoseEnd() {
        FONT.drawString("GAME OVER", GAME_OVR_POINT.x,GAME_OVR_POINT.y);
        FONT.drawString("FINAL SCORE: " + score, FINAL_SCORE_POINT.x, FINAL_SCORE_POINT.y);
    }

    /**
     * This method updates the game whilst it is in a "Win" state
     */
    public void updateWinEnd() {
        FONT.drawString("CONGRATULATIONS", CONGRATS_POINT.x,CONGRATS_POINT.y);
    }
}
