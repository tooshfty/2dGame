package inventory;

import Main.GamePanel;
import entity.Entity;

public class ItemPickup extends Entity {
    public final InventoryItem payload;

    public ItemPickup(GamePanel gp, InventoryItem payload) {
        super(gp);
        this.payload = payload;
        this.collision = false;  // walkable
        // set sprite for UI display if you want; not required
    }
}
