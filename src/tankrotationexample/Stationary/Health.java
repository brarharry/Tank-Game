package tankrotationexample.Stationary;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends Stationary {
    private boolean healthHit = false;
    public Health(int x, int y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void checkCollision() {

    }

    public void setHealthHit(boolean healthHit) {
        this.healthHit = healthHit;
    }

    @Override
    public void drawImage(Graphics g) {
        if (!healthHit) {
            super.drawImage(g);
//            g.setColor(Color.RED);
//            g.drawRect(this.getX(), this.getY(), this.getImg().getWidth(), this.getImg().getHeight());
        }
    }
}
