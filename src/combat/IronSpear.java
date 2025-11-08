package combat;

import Main.GamePanel;

import java.util.List;

public class IronSpear extends SpearBase{

    public IronSpear(GamePanel gp) { super(gp); /* set damage/reach/frames */ }
    @Override public void startAttack(entity.Entity user) { /* thrust startup */ }
    @Override public void updateAttack(entity.Entity user) { /* advance frames */ }
    @Override public List<Hitbox> getCurrentHitboxes(entity.Entity user) { /* thrust box */
        return List.of();
    }
}

