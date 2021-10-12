import bagel.Input;

public class Level0 extends Level{
    private final int LEVEL0_POINT_THRESHOLD = 10;
    private final int STARTING_LIVES = 3;

    public Level0(Bird bird) {
        super(bird);
        super.lifebar = new Lives(STARTING_LIVES);
    }

    public void updateRunning(Input input) {
        super.updateRunning(input);
        lifebar.drawLives();
    }
}
