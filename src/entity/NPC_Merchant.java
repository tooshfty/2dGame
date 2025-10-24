package entity;

import Main.GamePanel;
import object.OBJ_Boots;
import object.OBJ_Mana_Crystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;

import java.util.Random;

public class NPC_Merchant extends Entity{

    public NPC_Merchant(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
        setItems();
    }
    public void getImage(){

        up1 = setup("/NPC/merchant_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("/NPC/merchant_down_2",gp.tileSize,gp.tileSize);
        down1 = setup("/NPC/merchant_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/NPC/merchant_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/NPC/merchant_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("/NPC/merchant_down_2",gp.tileSize,gp.tileSize);
        right1 = setup("/NPC/merchant_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("/NPC/merchant_down_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0][0] = "What can I do ya for?";
        dialogues[1][0] = "Come again.";
        dialogues[2][0] = "You don't have enough coin!";
        dialogues[3][0] = "You cannot carry any more!";
        dialogues[4][0] = "You cannot sell an item when equipped!";

    }

    public void speak() {

        //character specific dialogue
        facePlayer();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }

    public void setItems() {
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Boots(gp));
        inventory.add(new OBJ_Shield_Blue(gp));

    }

}
