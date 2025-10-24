package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Axe extends Entity {

    public static final String objName = "Woodcutter's Axe";
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        type = type_axe;
        name = objName;
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nAn old axe.";
        price = 12;
        knockbackPower = 6;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
