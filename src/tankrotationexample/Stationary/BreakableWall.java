package tankrotationexample.Stationary;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    public int state = 1;

    public BreakableWall(int x, int y, BufferedImage img) {
        super(x,y,img);
    }

    @Override
    public void checkCollision() {
        this.setX(0);
        this.setY(0);
    }

    @Override
    public void drawImage(Graphics g){
        if (state == 1) {
            super.drawImage(g);
        }
        else {
            this.hitBox.setLocation(0,0);
        }

    }
}
