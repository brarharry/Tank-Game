package tankrotationexample.Movable;

import tankrotationexample.game.TRE;

import java.awt.*;
import java.awt.image.BufferedImage;
import tankrotationexample.Stationary.*;

public class Bullet extends Movable {
    boolean bulletHit = false;

    public Bullet(int x, int y, float angle, BufferedImage bulletImage) {
        super(x, y, angle, bulletImage);
        this.R = 7;
        super.hitBox= new Rectangle(x, y, bulletImage.getWidth(), bulletImage.getHeight());
    }

    public Rectangle getBulletHitBox() {
        return this.hitBox.getBounds();
    }

    public void moveForwards(){
        if(!bulletHit) {
            super.moveForwards();
            this.hitBox.setLocation(x, y);
            checkCollision();
        }
    }

    public void checkCollision() {
        for (int i = 0; i < TRE.gameObjs.size(); i++) {

            if (TRE.gameObjs.get(i) instanceof UnbreakableWall) {
                if (this.getBulletHitBox().intersects(((UnbreakableWall) TRE.gameObjs.get(i)).getHitBox())) {
                    bulletHit = true;

                }
            }

            if (TRE.gameObjs.get(i) instanceof BreakableWall) {
                if (this.getBulletHitBox().intersects(((BreakableWall) TRE.gameObjs.get(i)).getHitBox())) {
                    bulletHit = true;
                   ((BreakableWall) TRE.gameObjs.get(i)).state++;

                }
            }

            if (TRE.gameObjs.get(i) instanceof Tank){
                    if ((this.getBulletHitBox().intersects(TRE.gameObjs.get(i).hitBox)) && !((Tank) TRE.gameObjs.get(i)).isMyBullet(this)) {
                        bulletHit = true;
                        ((Tank) TRE.gameObjs.get(i)).setHealth(((Tank) TRE.gameObjs.get(i)).getHealth() - 5);
                    }
            }
        }
    }


            public void update () {
                moveForwards();
            }

            public void drawImage (Graphics g){
                if(!bulletHit) {
                    super.drawImage(g);
//                    g.setColor(Color.RED);
//                    g.drawRect(x, y, super.getImg().getWidth(), super.getImg().getHeight());
                }
            }


        }

