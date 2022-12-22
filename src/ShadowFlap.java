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

    // Game states. Default state is start,
    private enum GameState{
        Start,
        Running,
        WinEnd,
        LoseEnd,
        LevelUp
    };
    private GameState state = GameState.Start;

    // Current level
    private enum LevelState{
        Level0,
        Level1
    };

    private LevelState currentLevelState = LevelState.Level0;
    private Level level = new Level0();

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
        // Timescale options. If L is pressed, increase the timescale of the level.
        // If K is pressed, decrease the timescale of the level
        if(input.wasPressed(Keys.L)) {
            level.increaseTimeScale();
        }
        if(input.wasPressed(Keys.K)) {
            level.decreaseTimeScale();
        }

        // Check if the level is level 0
        if(currentLevelState == LevelState.Level0) {
            level.updateBackground();

            // Starting state. Press space to start
            if(state == GameState.Start) {
                level.updateStart();
                if (input.isDown(Keys.SPACE)) {
                    state = GameState.Running;
                }
            }

            // Running State of level 0
            if(state == GameState.Running) {
                level.updateRunning(input);

                // If the lives reach 0, end. If you pass the threshold of the level, you level up.
                if(level.getLives()==0) {
                    state = GameState.LoseEnd;
                }
                if(level.isPassedThreshold()) {
                    level.resetFrames();
                    state = GameState.LevelUp;
                }
            }

            // Levelup state of the level. When it's been in the LEVEL_UP screen for as long as the "level up frames"
            // it will move to the next level.
            if(state == GameState.LevelUp) {
                if(level.getFrames()%level.getLEVEL_UP_FRAMES() == 0 && level.getFrames() != 0) {
                    currentLevelState = LevelState.Level1;
                    state = GameState.Start;
                    level = new Level1();
                } else {
                    level.updateLevelUp();
                }
            }

            // Lose screen
            if(state == GameState.LoseEnd) {
                level.updateLoseEnd();
            }
        }

        // Check if a level is level 1
        if(currentLevelState == LevelState.Level1) {
            level.updateBackground();

            // Starting state. Press space to start
            if(state == GameState.Start) {
                level.updateStart();
                if (input.isDown(Keys.SPACE)) {
                    state = GameState.Running;
                }
            }

            // Running State of level 1
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

        // Lose screen
        if(state == GameState.LoseEnd) {
            level.updateLoseEnd();
        }

        // Win Screen
        if(state == GameState.WinEnd) {
            level.updateWinEnd();

        }
    }

}
