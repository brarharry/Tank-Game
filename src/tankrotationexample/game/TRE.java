/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Movable.*;
import tankrotationexample.Stationary.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private BufferedImage background;
    private Launcher lf;
    public static long tick = 0;
    public static ArrayList<GameObject> gameObjs;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.gameInitialize();
           while (true) {
                this.tick++;
                this.gameObjs.forEach(obj -> obj.update());
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(Tank.dead ){
                    this.lf.setFrame("end");
                    return;
                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                                       GameConstants.GAME_WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        gameObjs = new ArrayList<>();
        try {
            background = (BufferedImage)Resource.getHashMap().get("wallpaper");
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("map/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("nothing here");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);
            for (int curRow = 0; curRow < numRows; curRow++){
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol< numCols; curCol++){
                    switch(mapInfo[curCol]){
                        case "2" :
                            BreakableWall brwall = new BreakableWall(curCol*50, curRow*50, (BufferedImage) Resource.getHashMap().get("BreakableWall"));
                            this.gameObjs.add(brwall);
                            break;
                        case "4" :
                            Health health = new Health(curCol*50, curRow*50, (BufferedImage) Resource.getHashMap().get("Health"));
                            this.gameObjs.add(health);
                            break;
                        case "3" :
                        case "9" :
                            UnbreakableWall sowall = new UnbreakableWall(curCol*50, curRow*50, (BufferedImage) Resource.getHashMap().get("UnBreakableWall"));
                            this.gameObjs.add(sowall);
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        Tank t1 = new Tank(150, 150, 0, 0, 90, (BufferedImage) Resource.getHashMap().get("RedTank"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        Tank t2 = new Tank(2275, 1650, 0, 0, 270, (BufferedImage) Resource.getHashMap().get("BlueTank"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc2);
        this.gameObjs.add(t1);
        this.gameObjs.add(t2);

    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.drawImage(this.background,0,0,null);

        buffer.setColor(Color.BLACK);
        //buffer.fillRect(0,0,GameConstants.GAME_WORLD_WIDTH,GameConstants.GAME_WORLD_HEIGHT);
        Tank tank1 = null, tank2 = null;
        int check = 0;
        for (int i = 0; i < gameObjs.size(); i++){
            if (gameObjs.get(i) instanceof Tank && check == 0){
                tank1 = (Tank) gameObjs.get(i);
                check++;
            }
            else if( gameObjs.get(i) instanceof Tank && check >= 1){
                tank2 = (Tank) gameObjs.get(i);
            }
        }

        this.gameObjs.forEach(obj -> obj.drawImage(buffer));
        BufferedImage leftHalf = world.getSubimage(tank1.cx, tank1.cy,GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);//t1
        BufferedImage rightHalf = world.getSubimage(tank2.cx,tank2.cy,GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);//t2
        BufferedImage mm = world.getSubimage(0,0, GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2+4,0,null);
        g2.scale(.15, .15);
        g2.drawImage(mm, GameConstants.GAME_WORLD_WIDTH-300,GameConstants.GAME_WORLD_HEIGHT+870, null);
    }


}
