package combat;

import Main.GamePanel;
import entity.Entity;

import java.util.List;

public abstract class Weapon {
    protected GamePanel gp;

    // Simple state machine
    protected boolean active = false;
    protected int frame = 0;          // current attack frame
    protected int totalFrames = 0;    // duration of attack
    protected int cooldownFrames = 0; // frames remaining to re-attack
    protected int damage = 1;

    public Weapon(GamePanel gp) { this.gp = gp; }

    public boolean isActive() { return active; }
    public boolean isOnCooldown() { return cooldownFrames > 0; }
    public boolean ready() { return !active && cooldownFrames == 0; }

    public void tickCooldown() { if (cooldownFrames > 0) cooldownFrames--; }

    public abstract void startAttack(Entity user);
    public abstract void updateAttack(Entity user);
    public abstract List<Hitbox> getCurrentHitboxes(Entity user);
}
