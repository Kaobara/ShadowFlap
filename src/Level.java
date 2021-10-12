import bagel.Font;
import bagel.Input;
import bagel.Window;

import java.awt.*;

public abstract class Level {
    // Width Constants... THINK ABOUT PROGRAM-WIDE CONSTANTS!!!!
    private final int SCREEN_WIDTH = bagel.Window.getWidth();
    private final int SCREEN_HEIGHT = Window.getHeight();

    // Font size constants
    private final int FINAL_SCORE_HEIGHT_DIFFERENCE = 75;
    private final int INSTRUCTIONS_WIDTH = 612;
    private final int GAME_OVR_WIDTH = 287;
    private final int CONGRATS_WIDTH = 483;
    private final int FINAL_SCORE_WIDTH = 376;
    private final int FONT_SIZE = 48;
    private final int FONT_HEIGHT = 29;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);

    private final Point INSTRUCTION_POINT = new Point((SCREEN_WIDTH-INSTRUCTIONS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point SHOOT_INSTRUCT_POINT = new Point((SCREEN_WIDTH-INSTRUCTIONS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2 - 68);
    private final Point GAME_OVR_POINT = new Point((SCREEN_WIDTH-GAME_OVR_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point CONGRATS_POINT = new Point((SCREEN_WIDTH-CONGRATS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2);
    private final Point FINAL_SCORE_POINT = new Point((SCREEN_WIDTH-FINAL_SCORE_WIDTH)/2,
            ((SCREEN_HEIGHT-FONT_HEIGHT)/2)+FINAL_SCORE_HEIGHT_DIFFERENCE);
    private final Point COUNTER_POINT = new Point(100, 100);


    public void updateStart(Input input) {
        FONT.drawString("PRESS SPACE TO START", INSTRUCTION_POINT.x, INSTRUCTION_POINT.y);
    }
}
