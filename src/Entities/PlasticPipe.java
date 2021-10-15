package Entities;

import bagel.Image;

public class PlasticPipe extends PipeSet {
    // Plastic pipe image
    private final Image PIPE_IMAGE = new Image("res/level/plasticPipe.png");

    public PlasticPipe() {
        super.entityImage = PIPE_IMAGE;
    }
}
