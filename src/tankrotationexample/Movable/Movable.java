package tankrotationexample.Movable;

import tankrotationexample.GameConstants;
import tankrotationexample.game.GameObject;

import java.awt.image.BufferedImage;

public abstract class Movable extends GameObject {
    int vx;
    int vy;
    int R;
    public Movable(int x, int y, float angle, BufferedImage img) {
        super(x, y, angle, img);

    }

    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    public void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 49) {
            x = GameConstants.GAME_WORLD_WIDTH - 49;
        }
        if (y < 30) {
            y = 30;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 49) {
            y = GameConstants.GAME_WORLD_HEIGHT - 49;
        }
    }

    public abstract void update();
}
