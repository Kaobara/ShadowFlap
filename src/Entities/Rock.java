package Entities;

import bagel.Image;

public class Rock extends Weapon{
    private final int MAX_ROCK_RANGE = 25;
    public Rock(double y) {
        super(new Image("res/level-1/rock.png"), y);
        super.maxRange = MAX_ROCK_RANGE;
    }
}
