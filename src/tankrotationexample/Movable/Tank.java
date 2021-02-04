package tankrotationexample.Movable;



import tankrotationexample.GameConstants;
import tankrotationexample.game.*;
import tankrotationexample.Stationary.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author anthony-pc
 */
public class Tank extends Movable {

    //private int hit = 0;
    public int cx;
    public int cy;
    int sfx=0;
    int sfy=0;
    public static boolean dead;
    private int health = 50;
    //private boolean sameTank = false;

    private final float ROTATIONSPEED = 3.0f;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private ArrayList<Bullet> bulls;
    private int lives = 3;
    private Stack<Lives> live;


    public Tank(int x, int y, int vx, int vy, float angle, BufferedImage img) {
        super(x,y,angle,img);
        this.vx = vx;
        this.vy = vy;
        cx = x - GameConstants.GAME_SCREEN_WIDTH/4;
        cy = y - GameConstants.GAME_SCREEN_HEIGHT/4;
        checkScreen();
        this.R = 2;
        this.bulls = new ArrayList<Bullet>();
        this.live = new Stack<Lives>();
        createLives();
    }

    private void createLives() {
        for (int i=0; i < lives; i++){
            if(live.isEmpty()){
                live.add(new Lives(this.getX(),this.getY()+55,0, (BufferedImage) Resource.getHashMap().get("Live")));
            }
            else {
                live.add(new Lives(live.peek().getX()+18, this.getY()+55,0, (BufferedImage) Resource.getHashMap().get("Live")));
            }
        }

    }

    public int getLives(){
        return lives;
    }

    public void setLives(int x){
        this.lives = x;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.shootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.shootPressed = false;
    }

    public void update() {


        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }

        if (this.shootPressed && TRE.tick % 30 == 0) {
            Bullet bull = new Bullet(x+15, y+15, angle, (BufferedImage) Resource.getHashMap().get("Bullet"));
            this.bulls.add(bull);
        }

        this.bulls.forEach(bulls -> bulls.update());


        if (lives == 0){
            dead = true;
        }
        checkCollision();
        checkLives();
    }

    public void checkLives(){
        if(health == 0){
           live.get(lives-1).hitBox.setLocation(0,0);
           live.get(lives -1).setLiveHealth(0);
           lives--;
           this.health = 50;
        }
        for (int i=0; i < lives; i++){
            if(i==0){
                live.get(i).setX(x);
                live.get(i).setY(y+55);
            }
            else if (i == 1){
                live.get(i).setX(x+18);
                live.get(i).setY(y+55);
            }
            else if (i == 2){
                live.get(i).setX(x+18+18);
                live.get(i).setY(y+55);
            }

        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }



        private void moveBackwards () {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));//
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        cx = x - GameConstants.GAME_SCREEN_WIDTH / 4;
        cy = y - GameConstants.GAME_SCREEN_HEIGHT / 4;
        this.hitBox.setLocation(x, y);
        checkScreen();
    }

        public void moveForwards () {
        super.moveForwards();
        cx = x - GameConstants.GAME_SCREEN_WIDTH / 4;
        cy = y - GameConstants.GAME_SCREEN_HEIGHT / 4;
        this.hitBox.setLocation(x, y);
        checkScreen();
    }


    private void checkScreen() {
        if (cx >= GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2){
            cx = GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2;
        }
        else if (cx < 0) {
            cx = 0;
        }

       if (cy >= GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
            cy = GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
       }
       else if (cy < 0){
            cy = 0;
       }

    }

    public void checkCollision() {

        for (int i = 0; i < TRE.gameObjs.size(); i++) {

            if (TRE.gameObjs.get(i) instanceof UnbreakableWall) {
                if (this.getHitBox().intersects(((UnbreakableWall) TRE.gameObjs.get(i)).getHitBox()))  {
                    int newX = this.getX();
                    int newY = this.getY();
                    if (this.DownPressed){
                        newX =+ this.vx;
                        newY += this.vy;
                    }
                    else if(this.UpPressed){
                        newX -= this.vx;
                        newY -= this.vy;
                    }
                    this.setX(newX);
                    this.setY(newY);
                }

            }
            if (TRE.gameObjs.get(i) instanceof BreakableWall) {
                if (this.getHitBox().intersects(((BreakableWall) TRE.gameObjs.get(i)).getHitBox()))  {
                    int newX = this.getX();
                    int newY = this.getY();
                    if (this.DownPressed){
                        newX =+ this.vx;
                        newY += this.vy;
                    }
                    else if(this.UpPressed){
                        newX -= this.vx;
                        newY -= this.vy;
                    }
                    this.setX(newX);
                    this.setY(newY);

                }
            }

            if (TRE.gameObjs.get(i) instanceof Health) {
                if (this.getHitBox().intersects(TRE.gameObjs.get(i).getHitBox()) )  {
                    if (lives != 3){
                        ((Health) TRE.gameObjs.get(i)).setHealthHit(true);

                        lives++;
                        this.live.add(this.lives-1,new Lives(live.get(lives-1).getX()+18, y+55,0, (BufferedImage) Resource.getHashMap().get("Live")));
                        TRE.gameObjs.get(i).getHitBox().setLocation(0,0);
                        checkLives();

                    }
                }
            }
        }
    }
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        super.drawImage(g);
        this.bulls.forEach(bullet -> bullet.drawImage(g));
        this.live.forEach(life -> life.drawImage(g));
//        g.setColor(Color.RED);
//        g.drawRect(x, y, this.img.getWidth(), this.img.getHeight());
        g.setColor(Color.BLUE);
        g.fillRect(x,y - 10,health,5);
        g.drawRect(x,y - 10,health,5);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isMyBullet(Bullet b){
        if (bulls.contains(b)){
            return true;
        }
        else {
            return false;
        }
    }
}
