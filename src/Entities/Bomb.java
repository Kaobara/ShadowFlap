package Entities;

import bagel.Image;

public class Bomb extends Weapon{
    private final int MAX_BOMB_RANGE = 50;

    private static final Image BOMB_IMAGE =  new Image("res/level-1/bomb.png");
    /**
     * This is the constructor of the Bomb class
     * @param y is the initial spawn point of the Bomb entity
     */
    public Bomb(double y) {
        super(BOMB_IMAGE, y);
        super.maxRange = MAX_BOMB_RANGE;
    }
}
