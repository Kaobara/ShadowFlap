import bagel.Image;

import java.util.Random;

public class PlasticPipe extends EntitySet{
    private final int PIPE_GAP = 168;
    private final Image PIPE_IMAGE = new Image("res/level/plasticPipe.png");

    public PlasticPipe() {
        super.entityImage = PIPE_IMAGE;
        super.gap = PIPE_GAP;
    }
}
