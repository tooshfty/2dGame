// File: combat/BattleAxe.java
package combat;

import entity.Entity;
import Main.GamePanel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BattleAxe extends AxeBase {
    // Authorable params — tweak to taste
    private final int reach;          // radius from player center (pixels)
    private final int boxW;           // hitbox width (pixels)
    private final int boxH;           // hitbox height (pixels)
    private final int swingFrames;    // active frames
    private final int cdFrames;       // cooldown frames
    private final double startDeg;    // -90 for right-to-left
    private final double endDeg;      // +90 for right-to-left sweep

    // Track which monsters we've hit this swing (so we don't double-hit)
    private final List<Integer> hitIds = new ArrayList<>();

    public BattleAxe(GamePanel gp) {
        super(gp);
        this.damage = 3;
        this.reach = gp.tileSize;       // 1 tile away from player center
        this.boxW = gp.tileSize;        // size of blade hitbox
        this.boxH = gp.tileSize / 2;
        this.swingFrames = 10;
        this.cdFrames = 15;
        this.startDeg = -90.0;
        this.endDeg = +90.0;
    }

    @Override
    public void startAttack(Entity user) {
        if (!ready()) return;
        active = true;
        frame = 0;
        totalFrames = swingFrames;
        hitIds.clear();
        // Optionally: trigger player swing animation here
    }

    @Override
    public void updateAttack(Entity user) {
        if (!active) {
            tickCooldown();
            return;
        }
        frame++;
        if (frame >= totalFrames) {
            active = false;
            cooldownFrames = cdFrames;
        }
    }

    @Override
    public List<Hitbox> getCurrentHitboxes(Entity user) {
        List<Hitbox> list = new ArrayList<>();
        if (!active) return list;

        // Player center in world space
        int cx = user.worldX + user.solidArea.x + user.solidArea.width / 2;
        int cy = user.worldY + user.solidArea.y + user.solidArea.height / 2;

        // Facing basis—so the arc is oriented to current player direction
        int[] dir = Facing.dirVector(user.direction);     // ex: (1,0) for right
        int[] right = Facing.perp(dir[0], dir[1]);        // perpendicular vector

        // Map facing to a base angle (cardinal). 0° = right, 90° = down, etc.
        double baseDeg = 0;
        if (dir[0] == 1 && dir[1] == 0) baseDeg = 0;     // right
        else if (dir[0] == -1 && dir[1] == 0) baseDeg = 180;
        else if (dir[0] == 0 && dir[1] == -1) baseDeg = -90;
        else if (dir[0] == 0 && dir[1] == 1) baseDeg = 90;

        // Interpolate swing angle (relative), then add base orientation
        double t = (double) frame / (double) Math.max(1, totalFrames - 1);
        double relDeg = startDeg + (endDeg - startDeg) * t;
        double deg = baseDeg + relDeg;
        double rad = Math.toRadians(deg);

        // Compute hitbox center along the arc
        int hx = cx + (int) Math.round(Math.cos(rad) * reach);
        int hy = cy + (int) Math.round(Math.sin(rad) * reach);

        // Build a rectangle centered at (hx, hy)
        Rectangle r = new Rectangle(hx - boxW / 2, hy - boxH / 2, boxW, boxH);
        list.add(new Hitbox(r, damage, gp.tileSize / 2)); // example knockback

        return list;
    }

    // Optionally add a method for “already hit” tracking by entity id
    // Probably need to add hit ids for each monster
    public boolean registerHit(int targetId) {
        if (hitIds.contains(targetId)) return false;
        hitIds.add(targetId);
        return true;
    }
}
