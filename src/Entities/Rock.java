package Entities;

import bagel.Image;

public class Rock extends Weapon{
    private final int MAX_ROCK_RANGE = 25;
    private static final Image ROCK_IMAGE =  new Image("res/level-1/rock.png");

    /**
     * This is the constructor of the Rock class
     * @param y is the initial spawn point of the Rock entity
     */
    public Rock(double y) {
        super(ROCK_IMAGE, y);
        super.maxRange = MAX_ROCK_RANGE;
    }
}
