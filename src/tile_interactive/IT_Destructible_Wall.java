package tile_interactive;

import Main.GamePanel;
import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;

import java.awt.*;
import java.util.Random;

public class IT_Destructible_Wall extends InteractiveTile{

    GamePanel gp;

    public IT_Destructible_Wall(GamePanel gp, int col, int row) {
        super(gp,col,row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 1; //can change the hp for each tile, leads into a switch statement for different appearances per hp

    }
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if (entity.currentWeapon.type == type_pickaxe){
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE(){
        gp.playSE(20);
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(65,65,65);
        return color;
    }

    public int getParticleSize(){
        int size = 6; //6 pixels
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
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
