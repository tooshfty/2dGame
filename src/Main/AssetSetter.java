package Main;

import Main.monster.MON_GreenSlime;
import entity.Entity;
import entity.NPC_OldMan;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        int i = 0;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize * 25;
        gp.obj[i].worldY = gp.tileSize * 23;
        i++;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize * 21;
        gp.obj[i].worldY = gp.tileSize * 19;
        i++;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize * 26;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 33;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[i] = new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX = gp.tileSize * 36;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize * 37;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[i] = new OBJ_Mana_Crystal(gp);
        gp.obj[i].worldX = gp.tileSize * 22;
        gp.obj[i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[i] = new OBJ_Heart(gp);
        gp.obj[i].worldX = gp.tileSize * 22;
        gp.obj[i].worldY = gp.tileSize * 31;
        i++;

    }

    public void setNPC(){

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }

    public void setMonster() {
        int i = 0;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 23;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 24;
        gp.monster[i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 34;
        gp.monster[i].worldY = gp.tileSize * 42;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 38;
        gp.monster[i].worldY = gp.tileSize * 42;
        i++;
    }
}




//just in case for assets
//gp.obj[0] = new OBJ_Key(gp);
//gp.obj[0].worldX = 23 * gp.tileSize;
//gp.obj[0].worldY = 7 * gp.tileSize;
//
//gp.obj[1] = new OBJ_Key(gp);
//gp.obj[1].worldX = 23 * gp.tileSize;
//gp.obj[1].worldY = 40 * gp.tileSize;
//
//gp.obj[2] = new OBJ_Key(gp);
//gp.obj[2].worldX = 37 * gp.tileSize;
//gp.obj[2].worldY = 7 * gp.tileSize;
//
//gp.obj[3] = new OBJ_Door(gp);
//gp.obj[3].worldX = 10 * gp.tileSize;
//gp.obj[3].worldY = 11* gp.tileSize;
//
//gp.obj[4] = new OBJ_Door(gp);
//gp.obj[4].worldX = 8 * gp.tileSize;
//gp.obj[4].worldY = 28 * gp.tileSize;
//
//gp.obj[5] = new OBJ_Door(gp);
//gp.obj[5].worldX = 12 * gp.tileSize;
//gp.obj[5].worldY = 22 * gp.tileSize;
//
//gp.obj[6] = new OBJ_Chest(gp);
//gp.obj[6].worldX = 10 * gp.tileSize;
//gp.obj[6].worldY = 7 * gp.tileSize;
//
//gp.obj[7] = new OBJ_Boots(gp);
//gp.obj[7].worldX = 37 * gp.tileSize;
//gp.obj[7].worldY = 42 * gp.tileSize;
