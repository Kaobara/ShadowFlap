import bagel.Input;
import bagel.Window;

import java.awt.*;
import java.util.ArrayList;

public class Level1 extends Level{
    private final int LEVEL1_POINT_THRESHOLD = 30;
    private final int STARTING_LIVES = 6;

    private final Point SHOOT_INSTRUCT_POINT = new Point((SCREEN_WIDTH-INSTRUCTIONS_WIDTH)/2,
            (SCREEN_HEIGHT-FONT_HEIGHT)/2 - 68);

    // Weapons
//    private ArrayList<Weapon> Weapons = new ArrayList<Weapon>();
//    private int leastRecentWeaponNum = 0;
//    private int mostRecentWeaponNum = -1;
//    private int weaponNum = 0;
//    private Point weaponPoint;

    public Level1() {
        super();
        super.lifebar = new Lives(STARTING_LIVES);
    }

    @Override
    public void updateStart(Input input) {
        super.updateStart(input);
        FONT.drawString("PRESS S TO SHOOT", INSTRUCTION_POINT.x, INSTRUCTION_POINT.y);
    }

    @Override
    public void updateRunning(Input input) {
        super.updateRunning(input);
    }
}
