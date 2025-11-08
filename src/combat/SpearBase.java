package combat;

import Main.GamePanel;

public abstract class SpearBase extends Weapon implements ThrowableWeapon {
    public SpearBase(GamePanel gp) {
        super(gp);
    }
    @Override public void launch(entity.Entity user) { /* spawn spear projectile */ }
}
