package tankrotationexample.Movable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lives extends Movable{
    private int liveHealth = 100;
    public Lives(int x, int y, float angle, BufferedImage img) {
        super(x, y, angle,img);
        this.R = 2;
    }

    @Override
    public void checkCollision() {

    }


    @Override
    public void update() {

    }

    public void setLiveHealth(int x){
        this.liveHealth = 0;
    }

    public void drawImage(Graphics g){
        if (this.liveHealth != 0) {
            super.drawImage(g);
//            g.setColor(Color.RED);
//            g.drawRect(this.getX(), this.getY(), this.getImg().getWidth(), this.getImg().getHeight());
        }
    }
}
