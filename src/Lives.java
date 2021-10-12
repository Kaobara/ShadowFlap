import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class Lives {
    private final Image FULL_LIFE_IMAGE = new Image("res/level/fullLife.png");
    private final Image NO_LIFE_IMAGE = new Image("res/level/noLife.png");
    private final Point FIRST_HEART_POINT = new Point(100, 15);
    private Point tempPoint;

    private int numLives;
    private int maxLives;

    public Lives(int numLives) {
        this.numLives = numLives;
        this.maxLives = numLives;
    }

    public void drawLives() {
        for(int i = 0; i<maxLives; i++) {
            if(i==0) {
                FULL_LIFE_IMAGE.draw(FIRST_HEART_POINT.x, FIRST_HEART_POINT.y);
                tempPoint = FIRST_HEART_POINT;
            } else {
                tempPoint = new Point(tempPoint.x+50, tempPoint.y);
                NO_LIFE_IMAGE.draw(tempPoint.x, tempPoint.y);
            }
        }
    }
}
