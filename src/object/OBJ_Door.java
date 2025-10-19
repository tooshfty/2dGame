package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {

    GamePanel gp;


    public OBJ_Door(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Door";
        down1 = setup("/objects/door",gp.tileSize,gp.tileSize);
        collision = true;
        type = type_obstacle;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


    }

    public void interact() {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You need a key to open this door!";
    }

}
