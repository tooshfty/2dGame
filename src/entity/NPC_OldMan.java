package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;
        dialogueSet = -1;
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

    //currently having an issue with cycling dialogue, not a big problem atm but will need to address it in the future
    public void setDialogue() {

        //first index is dialogue set, second index is dialogue index
        dialogues[0][0] = "Howdy";
        dialogues[0][1] = "Howdy there";
        dialogues[0][2] = "Howdy there partner";
        dialogues[0][3] = "Howdy there partner how ";

        dialogues[1][0] = "Hello";
        dialogues[1][1] = "Hello how";
        dialogues[1][2] = "Hello how are";

        dialogues[2][0] = "Hi";
        dialogues[2][1] = "Hi there";

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
        facePlayer();
        startDialogue(this,dialogueSet);
        dialogueSet++;
        if (dialogues[dialogueSet][0] == null){

            dialogueSet--;
        }
        //onPath = true;
    }

}
