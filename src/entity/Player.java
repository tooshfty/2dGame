package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{


    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2;
        screenY = gp.screenHeight / 2;

        //Setting player hitbox dimensions
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){

        worldX = gp.tileSize * 23 - (gp.tileSize/2);
        worldY = gp.tileSize * 21 - (gp.tileSize/2);
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){

        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");
    }

    public BufferedImage setup(String imageName){

        UtilityTool utool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream( imageName + ".png"));
            image = utool.scaleImage(image,  gp.tileSize, gp.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }


    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
            spriteCounter++;

            //Check Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check obj collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //check NPC collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //If collision is false, player can move
            if (!collisionOn) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }


            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }else {
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }

    public void pickUpObject(int i){

        if (i != 999){

        }
    }

    public void interactNPC(int index){


        if (index != 999){



        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum==1){
                    image = up1;
                }
                if (spriteNum ==2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum==1){
                    image = down1;
                }
                if (spriteNum ==2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum==1){
                    image = left1;
                }
                if (spriteNum ==2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum==1){
                    image = right1;
                }
                if (spriteNum ==2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);

        // Uncomment to see the player hitbox for testing
        //g2.setColor(Color.red);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }


}




//pickup key just in case
//String objectName = gp.obj[i].name;
//
//            switch (objectName) {
//        case "Key":
//        gp.playSE(1);
//hasKey++;
//gp.obj[i] = null;
//        gp.ui.showMessage("You got a key!");
//                    break;
//                            case "Door":
//                            if (hasKey > 0) {
//        gp.playSE(3);
//gp.obj[i] = null;
//hasKey--;
//        gp.ui.showMessage("You opened the door!");
//                    } else {
//                            gp.ui.showMessage("You need a key!");
//                    }
//                            break;
//                            case "Boots":
//                            gp.playSE(2);
//speed += 2;
//gp.obj[i] = null;
//        gp.ui.showMessage("Speed up!");
//                    break;
//                            case "Chest":
//gp.ui.gameFinished = true;
//        gp.stopMusic();
//                    gp.playSE(4);
//                    break;
//                            }