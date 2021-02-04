package tankrotationexample.Stationary;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {

    public UnbreakableWall(int x, int y, BufferedImage img) {
        super(x,y,img);
    }

    @Override
    public void checkCollision() {

    }
}
