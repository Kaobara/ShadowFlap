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

    private final Point BIRD_POINT = new Point(200, 350);

    // The Backgrounds
    private final Image BACKGROUND_IMAGE0 = new Image("res/level-0/background.png");
    private final Image BACKGROUND_IMAGE1 = new Image("res/level-1/background.png");

    // Images of Bird
    private final Image BIRD_IMAGE_UP0 = new Image("res/level-0/birdWingUp.png");
    private final Image BIRD_IMAGE_DOWN0 = new Image("res/level-0/birdWingDown.png");
    private final Image BIRD_IMAGE_UP1 = new Image("res/level-1/birdWingUp.png");
    private final Image BIRD_IMAGE_DOWN1 = new Image("res/level-1/birdWingDown.png");
    // Bird
    private Bird bird = new Bird(BIRD_POINT.x, BIRD_POINT.y, BIRD_IMAGE_UP0, BIRD_IMAGE_DOWN0);

    // Game states. Default state is start,
    private enum GameState{
        Start,
        Running,
        WinEnd,
        LoseEnd
    };
    private GameState state = GameState.Start;

    // Current level
    private enum LevelState{
        Level0,
        Level1
    };
    private LevelState currentLevelState = LevelState.Level0;
    private Level level;

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
        if(currentLevelState == LevelState.Level0) {
            level = new Level0(bird);

            BACKGROUND_IMAGE0.draw(SCREEN_WIDTH/2.0, SCREEN_HEIGHT/2.0);
            // Starting state. Press space to start
            if(state == GameState.Start) {
                level.updateStart(input);
                if (input.isDown(Keys.SPACE)) {
                    level.frameCount = 0;  // Think about protection and being able to directly edit frameCount ltr
                    state = GameState.Running;
                }
            }

            if(state == GameState.Running) {
                level.updateRunning(input);
            }
        }
    }

}
