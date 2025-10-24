package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {

    GamePanel gp;
    public static final String objName = "Door";

    public OBJ_Door(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = objName;
        down1 = setup("/objects/door",gp.tileSize,gp.tileSize);
        collision = true;
        type = type_obstacle;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void interact() {
        startDialogue(this,0);
    }
    public void setDialogue(){

        dialogues[0][0] = "You need a key to open this door!";

    }

}
