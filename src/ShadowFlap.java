import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import org.lwjgl.system.CallbackI;

import java.awt.*;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Mohamad Danielsyah Mahmud
 */
public class ShadowFlap extends AbstractGame {
    // Screen Widths and Height
    private final int SCREEN_WIDTH = Window.getWidth();
    private final int SCREEN_HEIGHT = Window.getHeight();

    // The Backgrounds
    private final Image BACKGROUND_IMAGE0 = new Image("res/level-0/background.png");
    private final Image BACKGROUND_IMAGE1 = new Image("res/level-1/background.png");

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
    private Level level = new Level1();

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
        if(input.wasPressed(Keys.L)) {
            level.increaseTimeScale();
        }
        if(input.wasPressed(Keys.K)) {
            level.decreaseTimeScale();
        }
        if(currentLevelState == LevelState.Level0) {


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
                if(level.getLives()==0) {
                    state = GameState.LoseEnd;
                }
                if(level.isPassedThreshold()) {
                    currentLevelState = LevelState.Level1;
                    state = GameState.Start;
                    level = new Level1();
                }
            }

            if(state == GameState.LoseEnd) {
                level.updateLoseEnd(input);
            }
        }

        if(currentLevelState == LevelState.Level1) {
            BACKGROUND_IMAGE1.draw(SCREEN_WIDTH/2.0, SCREEN_HEIGHT/2.0);

            if(state == GameState.Start) {
                level.updateStart(input);
                if (input.isDown(Keys.SPACE)) {
                    level.frameCount = 0;  // Think about protection and being able to directly edit frameCount ltr
                    state = GameState.Running;
                }
            }

            if(state == GameState.Running) {
                level.updateRunning(input);
                if(level.getLives()==0) {
                    state = GameState.LoseEnd;
                }
                if(level.isPassedThreshold()) {
                    state = GameState.WinEnd;
                }
            }
        }

        if(state == GameState.LoseEnd) {
            level.updateLoseEnd(input);
        }

        if(state == GameState.WinEnd) {
            level.updateWinEnd(input);

        }
    }

}
