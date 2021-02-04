package tankrotationexample.Stationary;

import tankrotationexample.game.GameObject;

import java.awt.image.BufferedImage;

public abstract class Stationary extends GameObject {
    public Stationary(int x, int y, BufferedImage img) {
        super(x, y, 0, img);
    }

    @Override
    public void update(){}
}
