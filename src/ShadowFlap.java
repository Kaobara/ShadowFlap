import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;

import java.awt.*;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Mohamad Danielsyah Mahmud
 */
public class ShadowFlap extends AbstractGame {
    private final int SCREEN_WIDTH = Window.getWidth();
    private final int SCREEN_HEIGHT = Window.getHeight();



    private final int PIPE_GAP = 168;
    private final int MAX_SPAWN = 100;
    private final int MID_SPAWN = 300;
    private final int MIN_SPAWN = 500;

    private final Point BIRD_POINT = new Point(200, 350);
    private Point pipeSetPoint;
    private Point weaponPoint;


    // The Backgrounds
    private final Image BACKGROUND_IMAGE0 = new Image("res/level-0/background.png");
    private final Image BACKGROUND_IMAGE1 = new Image("res/level-1/background.png");

    // The Pipes
    private ArrayList<EntitySet> PipeSetArray = new ArrayList<EntitySet>();
    private int leastRecentPipeSetNum = 0;
    private int mostRecentPipeSetNum = -1;
    private int pipeSetNum = 0;

    // Weapons
    private ArrayList<Weapon> Weapons = new ArrayList<Weapon>();
    private int leastRecentWeaponNum = 0;
    private int mostRecentWeaponNum = -1;
    private int weaponNum = 0;

    // Lives
//    private int Lives = new Lives();


    // Images of Bird
    private final Image BIRD_IMAGE_UP0 = new Image("res/level-0/birdWingUp.png");
    private final Image BIRD_IMAGE_DOWN0 = new Image("res/level-0/birdWingDown.png");
    private final Image BIRD_IMAGE_UP1 = new Image("res/level-1/birdWingUp.png");
    private final Image BIRD_IMAGE_DOWN1 = new Image("res/level-1/birdWingDown.png");
    // Bird
    private Bird bird = new Bird(BIRD_POINT.x, BIRD_POINT.y, BIRD_IMAGE_UP0, BIRD_IMAGE_DOWN0);

    // Scores
    private int score = 0;
    private int frameCount;
    private final int LEVEL0_POINT_THRESHOLD = 10;
    private final int LEVEL1_POINT_THRESHOLD = 30;

    // Game states. Default state is start,
    private enum GameState{
        Start,
        Running,
        WinEnd,
        LoseEnd
    };
    private GameState state = GameState.Start;

    // Current level
    private enum Level{
        Level0,
        Level1
    };
    private Level currentLevel = Level.Level0;
    private int currentTimeScale = 0;

    public ShadowFlap() {
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if(currentLevel == Level.Level0) {
            BACKGROUND_IMAGE0.draw(SCREEN_WIDTH/2.0, SCREEN_HEIGHT/2.0);


        }
    }

}
