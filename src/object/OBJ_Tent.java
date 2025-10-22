package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Tent extends Entity {

    GamePanel gp;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Tent";
        description = "[Tent]\nYou can sleep through\nthe night.";
        down1 = setup("/objects/tent",gp.tileSize,gp.tileSize);
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity) {

        gp.gameState = gp.sleepState;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}
