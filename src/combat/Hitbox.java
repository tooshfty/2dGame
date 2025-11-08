package combat;

import java.awt.*;

public class Hitbox {
    public Rectangle bounds;   // world-space rectangle
    public int damage;
    public int knockback;      // pixels or your own unit

    public Hitbox(Rectangle bounds, int damage, int knockback) {
        this.bounds = bounds;
        this.damage = damage;
        this.knockback = knockback;
    }
}
