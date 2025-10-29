package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{


    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCancel = false;
    public boolean lightUpdated = false;


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

        //attackArea.width = 36;
        //attackArea.height = 36;

        setDefaultValues();

    }

    public void setDefaultPositions() {

        gp.currentMap = 3;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 26;
        direction = "down";
    }


    public void setDefaultValues(){

        gp.currentMap = 2;
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 10;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        //player status
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 5;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 999;


        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        currentLight = null;
        attack = getAttack();
        defense = getDefense();
        getPlayerImage();
        getPlayerAttackImage();
        getPlayerGuardImage();
        setItems();

    }

    public void setItems() {

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Tent(gp));
        inventory.add(new OBJ_Lantern(gp));
        inventory.add(new OBJ_Pickaxe(gp));

    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
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

    public void getSleepingImage(BufferedImage image){

        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }

    public void getPlayerAttackImage() {

        if (currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_axe){
            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_pickaxe){
            attackUp1 = setup("/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
        }

    }

    public void getPlayerGuardImage(){
        guardUp = setup("/player/boy_guard_up",gp.tileSize, gp.tileSize);
        guardDown = setup("/player/boy_guard_down",gp.tileSize, gp.tileSize);
        guardLeft = setup("/player/boy_guard_left",gp.tileSize, gp.tileSize);
        guardRight = setup("/player/boy_guard_right",gp.tileSize, gp.tileSize);

    }
    public int getCurrentWeaponSlot(){

        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot(){

        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    public void restoreStatus() {
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockback = false;
        lightUpdated = true;
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

        if (knockback){

            //Check Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check obj collision
            gp.cChecker.checkObject(this, true);

            //check NPC collision
            gp.cChecker.checkEntity(this, gp.npc);

            //check monster collision
            gp.cChecker.checkEntity(this,gp.monster);

            //check interactive tile collision
            gp.cChecker.checkEntity(this,gp.iTile);

            if (collisionOn){
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            } else if (!collisionOn) {
                switch (knockbackDirection){
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
            knockbackCounter++;
            if (knockbackCounter == 5){
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            }
        }
        else if (attacking) {
            attacking();

        } else if (keyH.spacePressed) {
            guarding = true;
            guardCounter++;
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed
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

            //check interactive tile collision
            int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);

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

            if (keyH.enterPressed && !attackCancel){
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCancel = false;
            gp.keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;

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
            guarding = false;
            guardCounter = 0;
        }

        if (gp.keyH.shotKeyPressed && !projectile.alive
                && shotAvailableCounter == 30 && projectile.haveResource(this)){

            //set default coords, direction, user
            projectile.set(worldX,worldY,direction,true,this);

            //subtract use cost
            projectile.subtractResource(this);

            //check vacancy
            for (int i = 0; i < gp.projectile[1].length;i++){
                if (gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gp.playSE(10);
        }

        //needs to be outside key if statement
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
        if (!keyH.godModeOn) {
            if (life <= 0) {
                gp.ui.commandNum = -1; //sets the cursor to off to avoid instantly restarting if you died during combat
                gp.stopMusic();
                gp.playSE(12);
                gp.gameState = gp.gameOverState;
            }
        }
    }



    public void pickUpObject(int i){

        if (i != 999) {

            //pickup only items
            if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            //obstacle
            else if (gp.obj[gp.currentMap][i].type == type_obstacle ) {
                if (keyH.enterPressed){
                    attackCancel = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            } else {

                String text;

                if (canObtainItem(gp.obj[gp.currentMap][i])) {
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                } else {
                    text = "Inventory full!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }

    public void interactNPC(int index){


        if (index != 999) {

            if (gp.keyH.enterPressed){
                attackCancel = true;
                gp.npc[gp.currentMap][index].speak();
            }

            gp.npc[gp.currentMap][index].move(direction);
        }
    }

    public void contactMonster(int i) {

        if (i != 999) {
            if (!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.playSE(6);
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }

    public void damageProjectile(int i) {

        if (i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void damageMonster(int i,Entity attacker, int attack, int knockbackPower) {
        if (i!=999) {
            if (!gp.monster[gp.currentMap][i].invincible){

                gp.playSE(5);
                if (knockbackPower > 0){
                    knockback(gp.monster[gp.currentMap][i],attacker,knockbackPower);
                }
                if (gp.monster[gp.currentMap][i].offBalance){
                    attack *= 2;
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage;

                gp.ui.addMessage(damage + " damage");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("exp + " + gp.monster[gp.currentMap][i].exp + "!");
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }

        }
    }



    public void damageInteractiveTile(int i){

        //check index and whether the correct item to interact is held and whether the tile is invincible
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible){
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;//important to prevent 1 shotting tiles with >1 hp due to damage frames

            //generate particle
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
            if (gp.iTile[gp.currentMap][i].life == 0) {
                //gp.iTile[gp.currentMap][i].checkDrop();
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            setDialogue();
            startDialogue(this,0);
        }
    }
    public void setDialogue(){
        dialogues[0][0] = "You are level " + level + " now\n";
    }

    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe) {

                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == type_shield) {

                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == type_consumable) {
                if (selectedItem.use(this)){
                    if (selectedItem.amount > 1){
                        selectedItem.amount --;
                    }else {
                        inventory.remove(itemIndex);
                    }
                }
                //decide what to do with consumables, condition check that you can use the consumable
            }
            if (selectedItem.type == type_light){
                if (currentLight == selectedItem){
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
        }

    }

    public int searchItemInInventory(String itemName){

        int itemIndex = 999;

        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item){

        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.name);

        //check if stackable
        if (newItem.stackable){

            int index = searchItemInInventory(newItem.name);
            if (index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }else {
                if (inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        //not stackable
        else {
            if (inventory.size()!= maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking){
                    if (spriteNum == 1){image = up1;}
                    if (spriteNum == 2){image = up2;}
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                if (guarding){
                    image = guardUp;
                }
                break;
            case "down":
                if (!attacking){
                    if (spriteNum == 1){image = down1;}
                    if (spriteNum == 2){image = down2;}
                }
                if (attacking) {
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                if (guarding){
                    image = guardDown;
                }
                break;
            case "left":
                if (!attacking){
                    if (spriteNum == 1){image = left1;}
                    if (spriteNum == 2){image = left2;}
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                if (guarding){
                    image = guardLeft;
                }
                break;
            case "right":
                if (!attacking){
                    if (spriteNum == 1){image = right1;}
                    if (spriteNum == 2){image = right2;}
                }
                if (attacking) {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                if (guarding){
                    image = guardRight;
                }
                break;
        }

        //set player transparency to indicate invincible status
        if (transparent) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        if (drawing){
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }


        //reset transparency
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
        // Uncomment to see the player hitbox for testing
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        //uncomment to see player invincibility timer
        //g2.setFont(new Font("Arial", Font.PLAIN, 26));
        //g2.setColor(Color.white);
        //g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }


}
