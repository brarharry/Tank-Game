package tankrotationexample.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Collection;

public abstract class GameObject implements Collidable {
    public int x;
    public int y;
    public float angle;
    public Rectangle hitBox;
    public BufferedImage img;
    public static boolean debugger;
    public GameObject(int x, int y, float angle, BufferedImage img){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.img = img;
        this.hitBox = new Rectangle(x,y, img.getWidth(), img.getHeight());
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void drawImage(Graphics g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        if (debugger){
        g2d.setColor(Color.RED);
        g2d.drawRect(x, y, this.getImg().getWidth(), this.getImg().getHeight());}
    }
    public abstract void update();

}
