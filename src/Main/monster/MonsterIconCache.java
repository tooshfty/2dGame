package Main.monster;

import Main.GamePanel;
import entity.Entity;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class MonsterIconCache {
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    private MonsterIconCache() {}

    /** Returns a preview icon for the given key; lazy-loads once via a temp entity. */
    public static BufferedImage getIcon(GamePanel gp, String key) {
        if (key == null) return null;
        BufferedImage hit = CACHE.get(key);
        if (hit != null) return hit;

        // Lazy build: create a lightweight instance just long enough to read its down1 frame.
        Entity temp = switch (key) {
            case "Bat"   -> new MON_Bat(gp);
            case "Green Slime" -> new MON_GreenSlime(gp);
            case "Orc"   -> new MON_Orc(gp);
            case "Red Slime" -> new MON_RedSlime(gp);
            case "Skeleton Lord" -> new MON_Skeleton_Lord(gp);
            default      -> null;
        };

        BufferedImage icon = (temp != null) ? temp.down1 : null;
        CACHE.put(key, icon);
        return icon;
    }

    /** Clear cache if you ever reload spritesets at runtime. */
    public static void clear() {
        CACHE.clear();
    }
}
