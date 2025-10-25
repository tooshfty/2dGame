package tile_interactive;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * when creating interactive tiles that change appearance after interaction
 * it is a good idea to make the background transparent, that way you do not need
 * to change the actual tile underneath just display a different sprite/entity over it
 * example(cutting down a tree leaves a stump over the terrain, no interference with non grass tile)
 */

public class InteractiveTile extends Entity {

    GamePanel gp;
    public boolean destructible = false;


    //may need to add an additional parameter to get mapNum so we can place them around
    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }


    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    public void playSE(){}
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
    public void update() {

        //need to check invincible counter
        if (invincible){
            invincibleCounter++;

            if (invincibleCounter > 40) {

                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {


        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){


            g2.drawImage(down1, screenX, screenY,null);


        }
    }
}
