import bagel.Image;

public class Bomb extends Weapon{
    private final int MAX_BOMB_RANGE = 50;
    public Bomb(double y) {
        super(new Image("res/level-1/bomb.png"), y);
        super.maxRange = MAX_BOMB_RANGE;
    }
}
