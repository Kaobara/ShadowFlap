import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Window;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Level {
    // Width Constants... THINK ABOUT PROGRAM-WIDE CONSTANTS!!!!
    protected final int SCREEN_WIDTH = bagel.Window.getWidth();
    protected final int SCREEN_HEIGHT = Window.getHeight();

    // Font size constants
    protected final int FINAL_SCORE_HEIGHT_DIFFERENCE = 75;
    protected final int INSTRUCTIONS_WIDTH = 612;
    protected final int GAME_OVR_WIDTH = 287;
    protected final int CONGRATS_WIDTH = 483;
    protected final int FINAL_SCORE_WIDTH = 376;
    protected final int FONT_SIZE = 48;
    protected final int FONT_HEIGHT = 29;
    protected final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);

    protected final Point INSTRUCTION_POINT = new Point((SCREEN_WIDTH-INSTRUCTIONS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    protected final Point GAME_OVR_POINT = new Point((SCREEN_WIDTH-GAME_OVR_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    protected final Point CONGRATS_POINT = new Point((SCREEN_WIDTH-CONGRATS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    protected final Point FINAL_SCORE_POINT = new Point((SCREEN_WIDTH-FINAL_SCORE_WIDTH)/2,
            ((SCREEN_HEIGHT-FONT_HEIGHT)/2)+FINAL_SCORE_HEIGHT_DIFFERENCE);
    protected final Point COUNTER_POINT = new Point(100, 100);


    // Pipes
    protected ArrayList<EntitySet> pipeSetArray = new ArrayList<EntitySet>();
    protected int leastRecentPipeSetNum = 0;
    protected int mostRecentPipeSetNum = -1;
    protected int pipeSetNum = 0;

    protected Point pipeSetPoint;

    protected final Point BIRD_SPAWN = new Point(200, 350);



    // Lives
    protected Lives lifebar;


    // Scores
    protected int score = 0;
    protected int frameCount = 0;

    protected int currentTimeScale = 0;
    protected int spawnSpeed = 100;

    // Pipe Options
    protected final int MAX_SPAWN = 100;
    protected final int MID_SPAWN = 300;
    protected final int MIN_SPAWN = 500;

    Random rand = new Random();

    protected boolean passedThreshold = false;

    public Level() {

    }

    public void updateStart(Input input) {
        FONT.drawString("PRESS SPACE TO START", INSTRUCTION_POINT.x, INSTRUCTION_POINT.y);
    }

    public void updateRunning(Input input) {
        // A constant score.
        FONT.drawString("SCORE:" + score, COUNTER_POINT.x, COUNTER_POINT.y);
        lifebar.drawLives();

    }

    public int getLives() {
        return lifebar.getLives();
    }

    public boolean isPassedThreshold() {
        return passedThreshold;
    }

}
