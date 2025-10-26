package Main.monster;

import Main.GamePanel;
import entity.Entity;
import entity.Projectile;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Bat extends Entity {

    GamePanel gp;
    public MON_Bat(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Bat";
        defaultSpeed = 3;
        speed = defaultSpeed;
        maxLife = 1;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 5;

        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();

    }

    public void getImage() {

        up1 = setup("/monster/bat_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("/monster/bat_down_2",gp.tileSize,gp.tileSize);
        down1 = setup("/monster/bat_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/monster/bat_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/monster/bat_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("/monster/bat_down_2",gp.tileSize,gp.tileSize);
        right1 = setup("/monster/bat_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("/monster/bat_down_2",gp.tileSize,gp.tileSize);
    }


    public void setAction() {
        //get random direction
        getRandomDirection(10);
    }

    public void damageReaction() {

        actionLockCounter = 0;


    }

    /**
     * THIS METHOD CAN BE REUSED FOR ANYTHING YOU WANT TO HAVE DROPS ENABLED FOR
     */
    public void checkDrop(){

        //cast a die, random number
        int i = new Random().nextInt(100) + 1;

        // set the monster drop
        if (i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >=75 && i < 100){
            dropItem(new OBJ_Mana_Crystal(gp));
        }
    }
}
