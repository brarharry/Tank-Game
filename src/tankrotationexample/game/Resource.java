package tankrotationexample.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, BufferedImage> resources;

    static {
        Resource.resources = new HashMap<>();
        try {
            Resource.resources.put("RedTank",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("rtank.png"))));
            Resource.resources.put("wallpaper",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("wallpaper.png"))));
            Resource.resources.put("BlueTank",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("bluetank.png"))));
            Resource.resources.put("BreakableWall",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("bwall.png"))));
            Resource.resources.put("UnBreakableWall",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("outerwall.png"))));
            Resource.resources.put("Bullet",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("croppedbullet.png"))));
            Resource.resources.put("Health",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tankwallpaper.png"))));
            Resource.resources.put("Live",read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("live.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map getHashMap(){
        return resources;
    }
}
