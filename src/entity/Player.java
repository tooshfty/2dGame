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
        solidArea.width = 29;
        solidArea.height = 29;

        attackArea.width = 36;
        attackArea.height = 36;





        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){

        worldX = gp.tileSize * 23 - (gp.tileSize/2);
        worldY = gp.tileSize * 21 - (gp.tileSize/2);
        speed = 4;
        direction = "down";

        //player status
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage(){

        up1 = setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/player/boy_right_2",gp.tileSize,gp.tileSize);
    }

    public void getPlayerAttackImage() {

        attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize,gp.tileSize*2);
        attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize,gp.tileSize*2);
        attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize,gp.tileSize*2);
        attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize,gp.tileSize*2);
        attackLeft1 = setup("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
        attackLeft2 = setup("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
        attackRight1 = setup("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
        attackRight2 = setup("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);

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

        if (attacking) {
            attacking();

        }else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed
         || keyH.enterPressed) {

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

            //check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            contactMonster(monsterIndex);

            //check event
            gp.eHandler.checkEvent();



            //If collision is false, player can move
            if (!collisionOn && !keyH.enterPressed) {

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

            gp.keyH.enterPressed = false;

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

        //needs to be outside key if statement
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking() {

        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //save current worldX,worldY and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //adjust player worldx/y for attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            //attack area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //check monster collision with updated worldX/Y and solid area
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);
            //after checking collision, reset original positions
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }


    }

    public void pickUpObject(int i){

        if (i != 999){

        }
    }

    public void interactNPC(int index){


        if (gp.keyH.enterPressed) {

            if (index != 999){
                gp.gameState = gp.dialogueState;
                gp.npc[index].speak();

            } else {
                attacking = true;
        }
        }
    }

    public void contactMonster(int i) {

        if (i != 999) {
            if (!invincible) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if (i!=999) {
            if (!gp.monster[i].invincible){

                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;

                if (gp.monster[i].life <= 0) {
                    gp.monster[i] = null;
                }
            }

        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking){
                    if (spriteNum == 1){
                        image = up1;
                    }
                    if (spriteNum == 2){
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (!attacking){
                    if (spriteNum == 1){
                        image = down1;
                    }
                    if (spriteNum == 2){
                        image = down2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (!attacking){
                    if (spriteNum == 1){
                        image = left1;
                    }
                    if (spriteNum == 2){
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if (!attacking){
                    if (spriteNum == 1){
                        image = right1;
                    }
                    if (spriteNum == 2){
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }

        //set player transparency to indicate invincible status
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //reset transparency
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
        // Uncomment to see the player hitbox for testing
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        //uncomment to see player invincibility timer
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
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