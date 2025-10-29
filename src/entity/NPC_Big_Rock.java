package entity;

import Main.GamePanel;
import Main.UtilityTool;
import object.OBJ_Door_Iron;
import tile_interactive.IT_Metal_Plate;
import tile_interactive.InteractiveTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class NPC_Big_Rock extends Entity{

    public static final String npcName = "Big Rock";

    public NPC_Big_Rock(GamePanel gp){
        super(gp);

        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;
        dialogueSet = -1;
        getImage();
        setDialogue();
    }
    public void getImage(){

        up1 = setup("/NPC/bigrock",gp.tileSize,gp.tileSize);
        up2 = setup("/NPC/bigrock",gp.tileSize,gp.tileSize);
        down1 = setup("/NPC/bigrock",gp.tileSize,gp.tileSize);
        down2 = setup("/NPC/bigrock",gp.tileSize,gp.tileSize);
        left1 = setup("/NPC/bigrock",gp.tileSize,gp.tileSize);
        left2 = setup("/NPC/bigrock",gp.tileSize,gp.tileSize);
        right1 = setup("/NPC/bigrock",gp.tileSize,gp.tileSize);
        right2 = setup("/NPC/bigrock",gp.tileSize,gp.tileSize);
    }
    public void update(){}
    public void move(String direction){

        this.direction = direction;

        checkCollision();

        if (!collisionOn){

            switch (direction){
                case "up": worldY-=speed;break;
                case "down": worldY+=speed;break;
                case "left": worldX-=speed;break;
                case "right": worldX+=speed;break;

            }
        }
        detectPlate();
    }
    public void detectPlate(){

        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        //create a plate list
        for (int i = 0; i <gp.iTile[1].length; i++){

            if (gp.iTile[gp.currentMap][i] != null &&
                    gp.iTile[gp.currentMap][i].name !=null&&
                    gp.iTile[gp.currentMap][i].name.equals(IT_Metal_Plate.itName) ){
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }

        //create a rock list
        for (int i = 0; i <gp.npc[1].length; i++){

            if (gp.npc[gp.currentMap][i] != null && gp.npc

                    [gp.currentMap][i].name.equals(NPC_Big_Rock.npcName)){
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }

        int count = 0;

        //scan plate list
        for (int i = 0; i < plateList.size(); i++){

            int xDistance = Math.abs(worldX-plateList.get(i).worldX);
            int yDistance = Math.abs(worldY-plateList.get(i).worldY);
            int distance = Math.max(xDistance,yDistance);

            if (distance < 8){

                if (linkedEntity == null) {
                    linkedEntity = plateList.get(i);
                    gp.playSE(3);
                }
            } else {
                if (linkedEntity == plateList.get(i)) {
                    linkedEntity = null;
                }
            }
        }

        //scan rock list
        for (int i = 0; i < rockList.size(); i++){

            //count rocks on the plate
            if (rockList.get(i).linkedEntity != null){
                count++;
            }
        }

        //if all rocks are on plates the iron door opens
        if (count == rockList.size()){

            for (int i = 0; i < gp.obj[1].length; i++){

                if (gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)){

                    gp.obj[gp.currentMap][i] = null;
                    gp.playSE(21);
                }
            }
        }


    }

    public void setDialogue() {

        //first index is dialogue set, second index is dialogue index
        dialogues[0][0] = "It's a huge rock.";
    }

    public void setAction(){

    }

    public void speak() {

        facePlayer();

        dialogueSet++;
        if (dialogueSet >= dialogues.length ||dialogues[dialogueSet][0] == null){
            dialogueSet = 0;
        }

        startDialogue(this,dialogueSet);
    }

}
