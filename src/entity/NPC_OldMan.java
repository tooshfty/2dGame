package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage(){

        up1 = setup("/NPC/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/NPC/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/NPC/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/NPC/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/NPC/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/NPC/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/NPC/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/NPC/oldman_right_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Howdy";
        dialogues[1] = "Howdy1";
        dialogues[2] = "Howdy2 ";
        dialogues[3] = "Howdy3";
        dialogues[4] = "Howdy4";

    }

    public void setAction(){

        if (onPath){
            int goalCol = 12;
            int goalRow = 9;
            //int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            //int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol,goalRow);
        }
        else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

    public void speak() {

        //character specific dialogue
        super.speak();

        onPath = true;
    }

}
