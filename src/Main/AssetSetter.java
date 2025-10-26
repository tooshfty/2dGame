package Main;

import Main.monster.*;
import entity.Entity;
import entity.NPC_Big_Rock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import object.*;
import tile_interactive.IT_Destructible_Wall;
import tile_interactive.IT_Dry_Tree;
import tile_interactive.IT_Metal_Plate;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        //Map 0
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 33;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 37;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 19;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 18;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 17;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 35;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 34;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 14;
        gp.obj[mapNum][i].worldY = gp.tileSize * 28;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 12;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Boots(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 10;
        i++;
        gp.obj[mapNum][i] = new OBJ_Lantern(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 18;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        //Map 1
        mapNum = 1;
        i = 0;

        //Map 2
        mapNum = 2;
        i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 40;
        gp.obj[mapNum][i].worldY = gp.tileSize * 41;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 18;
        gp.obj[mapNum][i].worldY = gp.tileSize * 23;
        i++;

        //Map 3
        mapNum = 3;
        i = 0;

    }

    public void setNPC(){

        int mapNum = 0;
        int i = 0;

        //Map 0
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 21;
        gp.npc[mapNum][i].worldY = gp.tileSize * 21;
        gp.npc[mapNum][i].solidArea.x = 8;
        gp.npc[mapNum][i].solidArea.y = 16;
        gp.npc[mapNum][i].solidAreaDefaultX = gp.npc[mapNum][i].solidArea.x;
        gp.npc[mapNum][i].solidAreaDefaultY = gp.npc[mapNum][i].solidArea.y;
        gp.npc[mapNum][i].solidArea.width = 32;
        gp.npc[mapNum][i].solidArea.height = 32;
        i++;

        //Map 1
        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 12;
        gp.npc[mapNum][i].worldY = gp.tileSize * 7;
        i++;

        //Map 2
        mapNum = 2;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Big_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 20;
        gp.npc[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.npc[mapNum][i] = new NPC_Big_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 11;
        gp.npc[mapNum][i].worldY = gp.tileSize * 18;
        i++;
        gp.npc[mapNum][i] = new NPC_Big_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 23;
        gp.npc[mapNum][i].worldY = gp.tileSize * 14;
        i++;
        //Map 3
        mapNum = 3;
        i = 0;
    }

    public void setMonster() {


        //Map 0
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 23;
        i++;
        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 36;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 34;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 35;
        i++;

        //Map 1
        mapNum = 1;
        i = 0;

        //Map 2
        mapNum = 2;
        i = 0;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 39;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 36;
        gp.monster[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;
        i++;

        //Map 3
        mapNum = 3;
        i = 0;
        gp.monster[mapNum][i] = new MON_Skeleton_Lord(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 16;
        i++;

    }

   public void setInteractiveTile() {

        //Map 0
        int mapNum = 0;
        int i = 0;
//        gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,27,12);
//        i++;
//        gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,33,12);
//        i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,18,40);
//       i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,17,40);
//       i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,16,40);
//       i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,15,40);
//       i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,14,40);
//       i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,13,40);
//       i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,13,41);
//       i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,12,41);
//       i++;
//       gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,11,41);
//       i++;
       //Map 1
       mapNum = 1;

       //Map 2
       mapNum = 2;
       i = 0;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,18,30);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,17,31);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,17,32);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,17,34);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,18,34);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,18,33);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,10,22);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,10,24);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,38,18);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,38,19);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,38,20);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,38,21);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,18,13);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,18,14);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,22,28);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,30,28);
       i++;
       gp.iTile[mapNum][i] = new IT_Destructible_Wall(gp,32,28);
       i++;
       gp.iTile[mapNum][i] = new IT_Metal_Plate(gp,20,22);
       i++;
       gp.iTile[mapNum][i] = new IT_Metal_Plate(gp,8,17);
       i++;
       gp.iTile[mapNum][i] = new IT_Metal_Plate(gp,39,31);
       i++;

       //Map 3
       mapNum = 3;


    }
}