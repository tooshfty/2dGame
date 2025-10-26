package data;

import Main.GamePanel;
import entity.Entity;
import object.*;

import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp){
        this.gp = gp;

    }

    /**
     * currently using string itemName for switch case handling, might be better to replace with an item index so names
     * can be changed freely as item number indexes shouldn't change
     * There are also issues with nullpointerexception if ANY items on the map are not instantiated in the switch
     * below.
     * @param itemName
     * @return
     */
    public Entity getObject(String itemName){

        Entity obj = null;
        if (itemName == null){
            return null;
        }
        switch (itemName){
            case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break;
            case OBJ_Boots.objName: obj = new OBJ_Boots(gp); break;
            case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
            case OBJ_Coin_Bronze.objName: obj = new OBJ_Coin_Bronze(gp); break;
            case OBJ_Door.objName: obj = new OBJ_Door(gp); break;
            case OBJ_Door_Iron.objName: obj = new OBJ_Door_Iron(gp); break;
            case OBJ_Fireball.objName: obj = new OBJ_Fireball(gp); break;
            case OBJ_Heart.objName: obj = new OBJ_Heart(gp); break;
            case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
            case OBJ_Mana_Crystal.objName: obj = new OBJ_Mana_Crystal(gp); break;
            case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp); break;
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp); break;
            case OBJ_Rock.objName: obj = new OBJ_Rock(gp); break;
            case OBJ_Shield_Blue.objName: obj = new OBJ_Shield_Blue(gp); break;
            case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break;
            case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp); break;
            case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;

        }
        return obj;
    }

    public void save(){

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            //player stats
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;

            //player inventory
            for (int i = 0; i < gp.player.inventory.size(); i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }

            //player equipment
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

            //map objects
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++){

                for (int i = 0; i < gp.obj[1].length; i++){

                    if (gp.obj[mapNum][i] == null){
                        ds.mapObjectNames[mapNum][i] = "NA";
                    }
                    else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        if (gp.obj[mapNum][i].loot!=null){
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            //write data storage object
            oos.writeObject(ds);
        }catch (Exception e){
            System.out.println("Save exception");
            e.printStackTrace();
        }
    }
    public void load(){

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //read dataStorage object
            DataStorage ds = (DataStorage)ois.readObject();

            //player stats
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            //player inventory
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++){
                gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }
            //player equipment - consider using this style to save current lighting so you spawn at the time you left
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getPlayerAttackImage();


            //objects on map
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++){

                for (int i = 0; i < gp.obj[1].length; i++){

                    if (ds.mapObjectNames[mapNum][i].equals("NA")){
                        gp.obj[mapNum][i] = null;
                    }
                    else {
                        gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if (ds.mapObjectLootNames != null){
                            gp.obj[mapNum][i].loot = gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]);
                        }
                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if (gp.obj[mapNum][i].opened){
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }

        }catch (Exception e){
            System.out.println("Load exception");
            e.printStackTrace();
        }
    }
}
