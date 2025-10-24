package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;


    public BufferedImage up1, up2, down1, down2,
            left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1,
            attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage guardUp, guardDown, guardLeft, guardRight;
    public String direction = "down";


    public int spriteNum = 1;


    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public boolean invincible = false;


    //first index is dialogue set, second index is dialogue index
    public String[][] dialogues = new String[20][20];
    public int dialogueIndex = 0;
    public Entity attacker;

    //Character status
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    public Entity currentLight;
    public int knockbackPower;
    public int useCost;
    public int price;
    public int ammo;
    public int value;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int defaultSpeed;
    public boolean stackable = false;
    public int amount = 1;
    public boolean guarding = false;
    public boolean transparent = false;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int lightRadius;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockback = false;
    public String knockbackDirection;

    //states
    public boolean offBalance;
    public Entity loot;
    public boolean opened = false;
    public int dialogueSet = 0;

    //Counters
    public int spriteCounter = 0;
    public int guardCounter = 0;
    int offBalanceCounter = 0;
    int knockbackCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter;
    public int shotAvailableCounter;

    //Typing
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;


    public Entity(GamePanel gp){

        this.gp = gp;
    }

    public int getLeftX() {
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getBotY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol() {
        return (worldX + solidArea.x)/gp.tileSize;
    }
    public int getRow() {
        return (worldY + solidArea.y)/gp.tileSize;
    }
    public int getXDistance(Entity target){return Math.abs(worldX-target.worldX);}
    public int getYDistance(Entity target){return Math.abs(worldY-target.worldY);}
    public int getTileDistance(Entity target){return (getXDistance(target) + getYDistance(target))/gp.tileSize;}
    public int getGoalCol(Entity target){return (target.worldX + target.solidArea.x)/gp.tileSize;}
    public int getGoalRow(Entity target){return (target.worldY + target.solidArea.y)/gp.tileSize;}

    public void setAction(){}
    public void setLoot(Entity loot){}
    public void resetCounter(){

        spriteCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
        knockbackCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        shotAvailableCounter = 0;
    }
    public void damageReaction(){}
    public void knockback(Entity target,Entity attacker, int knockbackPower){

        this.attacker = attacker;
        target.knockbackDirection = attacker.direction;
        target.speed += knockbackPower;
        target.knockback = true;

    }
    public void speak(){}
    public void facePlayer(){

        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void startDialogue(Entity entity, int setNum){

        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }
    public void interact() {

    }
    public boolean use(Entity entity){
        return false;
    }
    public void checkDrop() {

    }
    public void dropItem(Entity droppedItem){

        for(int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;//very important to have break
            }
        }
    }
    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize(){
        int size = 0; // pixels
        return size;
    }

    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public String getOppositDiretion(String direction){
        String oppositeDirection = "";

        switch (direction){
            case "up": oppositeDirection = "down"; break;
            case "down": oppositeDirection = "up"; break;
            case "left": oppositeDirection = "right"; break;
            case "right": oppositeDirection = "left"; break;
        }
        return oppositeDirection;
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp,target,color,size,speed,maxLife,-2,-1);
        Particle p2 = new Particle(gp,target,color,size,speed,maxLife,2,-1);
        Particle p3 = new Particle(gp,target,color,size,speed,maxLife,-2,1);
        Particle p4 = new Particle(gp,target,color,size,speed,maxLife,2,1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this,gp.npc);
        gp.cChecker.checkEntity(this,gp.monster);
        gp.cChecker.checkEntity(this,gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }
    }
    public void update(){

        if (knockback){

            checkCollision();

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
        } else if (attacking) {
            attacking();
        } else {
            setAction();
            checkCollision();
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
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if (offBalance){
            offBalanceCounter++;
            if (offBalanceCounter > 60){
                offBalance = false;
                offBalanceCounter = 0;
            }
        }
    }

    public void checkStopChasing(Entity target, int distance, int rate){

        if (getTileDistance(target) > distance){
            int i = new Random().nextInt(rate);
            if (i == 0){
                onPath = false;
            }
        }
    }
    public void checkStartChasing(Entity target, int distance, int rate){

        if (getTileDistance(target) < distance){
            int i = new Random().nextInt(rate);
            if (i == 0){
                onPath = true;
            }
        }
    }
    public void getRandomDirection(){

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

    public void checkAttack(int rate, int straight, int horizontal) {

        boolean targetInRange = false;
        int xDistance = getXDistance(gp.player);
        int yDistance = getYDistance(gp.player);

        switch (direction){
            case "up":
                if (gp.player.worldY < worldY && yDistance < straight && xDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "down":
                if (gp.player.worldY > worldY && yDistance < straight && xDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "left":
                if (gp.player.worldX < worldX && yDistance < straight && xDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "right":
                if (gp.player.worldX > worldX && yDistance < straight && xDistance < horizontal){
                    targetInRange = true;
                }
                break;
        }
        if (targetInRange){
            //check if it indicates an attack
            int i = new Random().nextInt(rate);
            if (i==0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }
    public void checkShoot(int rate, int shotInterval){

        int i = new Random().nextInt(rate);
        if (i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
            projectile.set(worldX, worldY, direction, true, this);

            //check vacancy
            for (int j = 0; j < gp.projectile[1].length; j++) {
                if (gp.projectile[gp.currentMap][j] == null) {
                    gp.projectile[gp.currentMap][j] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    public void attacking() {

        spriteCounter++;

        if (spriteCounter <= motion1_duration) {
            spriteNum = 1;
        }
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
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

            if (type == type_monster){

                if (gp.cChecker.checkPlayer(this)){
                    damagePlayer(attack);
                }
            }
            //for player
            else {
                //check monster collision with updated worldX/Y and solid area
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex,this, attack, currentWeapon.knockbackPower);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cChecker.checkEntity(this,gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }


            //after checking collision, reset original positions
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > motion2_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }


    }

    public void damagePlayer(int attack){

        if (!gp.player.invincible){
            gp.playSE(6);
            int damage = attack - gp.player.defense;
            //get opposite direction of this attacker
            String canGuardDirection = getOppositDiretion(direction);

            //if player is guarding correct direction
            if (gp.player.guarding && gp.player.direction.equals(canGuardDirection)){

                //parry
                if (gp.player.guardCounter < 10){
                    damage = 0;
                    gp.playSE(16);
                    knockback(this,gp.player,knockbackPower);
                    offBalance = true;
                    //temporary stun effect
                    spriteCounter -= 60;
                }
                else {
                    //normal guard
                    damage /= 3;
                    gp.playSE(15);
                }
            }
            else {
                //not guarding
                gp.playSE(6);
                if (damage < 1) {
                    damage = 1;
                }
            }

            if (damage!=0){
                gp.player.transparent = true;
                knockback(gp.player,this,knockbackPower);
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }

    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

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
                    break;
            }

            //monster hp bar
            if (type == type_monster && hpBarOn) {

                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35,35,35));
                g2.drawRect(screenX - 1,screenY - 16,gp.tileSize + 2,12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }


            //set monster transparency when damaged
            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4f);
            }
            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY,null);

            //reset transparency
            changeAlpha(g2,1f);

            //2 lines below draws a hitbox for non player entity
            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public void dyingAnimation(Graphics2D g2)  {

        dyingCounter++;

        int i = 5;

        //to change animation, change the sprite instead of the alphaValue
        if (dyingCounter <= i) {
            changeAlpha(g2,0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2,1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2,0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2,1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2,0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2,1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2,0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2,1f);
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }



    public BufferedImage setup(String imagePath, int width, int height){

        UtilityTool utool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = utool.scaleImage(image,  width, height);

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void searchPath(int goalCol, int goalRow){

        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow);

        //if search returns true
        if (gp.pFinder.search()) {

            //next worldX and worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            //entity solid area position
            int entityLeftX = worldX + solidArea.x;
            int entityRightX = worldX + solidArea.x + solidArea.width;
            int entityTopY = worldY + solidArea.y;
            int entityBotY = worldY + solidArea.y + solidArea.height;

            if (entityTopY > nextY && entityLeftX >= nextX && entityRightX <= nextX + gp.tileSize){
                direction = "up";
                //entityRightX <= nextX + gp.tileSize
            }
            else if (entityTopY < nextY && entityLeftX >= nextX && entityRightX <= nextX + gp.tileSize){
                direction = "down";
                //entityRightX <= nextX + gp.tileSize
            }
            else if (entityTopY >= nextY && entityBotY <= nextY + gp.tileSize) {
                //entityBotY <= nextY + gp.tileSize
                //left or right
                if (entityLeftX > nextX){
                    direction = "left";
                }
                if (entityLeftX < nextX){
                    direction = "right";
                }
            }
            else if (entityTopY > nextY && entityLeftX > nextX) {
                //up or left
                direction = "up";
                checkCollision();
                if (collisionOn){
                    direction = "left";
                }
            }
            else if (entityTopY > nextY && entityLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn){
                    direction = "right";
                }
            }
            else if (entityTopY < nextY && entityLeftX > nextX) {
                //down or left
                direction = "down";
                checkCollision();
                if (collisionOn){
                    direction = "left";
                }
            }
            else if (entityTopY < nextY && entityLeftX < nextX) {
                //down or right
                direction = "down";
                checkCollision();
                if (collisionOn){
                    direction = "right";
                }
            }

            //if reaches goal stop pathing
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if (nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }
        }
    }
    public int getDetected(Entity user, Entity[][] target,String targetName){
        int index = 999;

        //check surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction){
            case "up":
                nextWorldY = user.getTopY() - gp.player.speed;
                break;
            case "down":
                nextWorldY = user.getBotY() + gp.player.speed;
                break;
            case "left":
                nextWorldX = user.getLeftX() - gp.player.speed;
                break;
            case "right":
                nextWorldX = user.getRightX() + gp.player.speed;
                break;
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for (int i = 0; i < target[1].length; i++){
            if (target[gp.currentMap][i] != null){
                if (target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
