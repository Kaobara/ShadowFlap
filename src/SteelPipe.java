import bagel.Image;

public class SteelPipe extends EntitySet{
    private final int PIPE_GAP = 168;
    private final Image PIPE_IMAGE = new Image("res/level-1/steelPipe.png");

    public SteelPipe() {
        super.entityImage = PIPE_IMAGE;
        super.gap = PIPE_GAP;
    }
}
