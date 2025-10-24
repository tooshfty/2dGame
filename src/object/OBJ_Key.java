package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {

    GamePanel gp;

    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Key";
        down1 = setup("/objects/key",gp.tileSize,gp.tileSize);
        description = "[" + name + "]\nAn old key.";
        type = type_consumable;
        stackable = true;
        setDialogue();

    }

    public boolean use(Entity entity){

        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != 999){
            startDialogue(this,0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        } else {
            startDialogue(this,1);
            return false;
        }
    }

    public void setDialogue(){
        dialogues[0][0] = "You use the " + name + " and open the door";
        dialogues[1][0] = "Find a locked door to use this on!";
    }
}
