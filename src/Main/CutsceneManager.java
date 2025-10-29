package Main;

import Main.monster.MON_Skeleton_Lord;
import entity.PlayerDummy;
import object.OBJ_Blue_Heart;
import object.OBJ_Door_Iron;

import java.awt.*;

public class CutsceneManager {

    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha = 0f;
    int y;
    String endCredit;

    //Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;

    public CutsceneManager(GamePanel gp){
        this.gp = gp;

        endCredit = "Program/Music/Art\n"
                + "RyiSnow"
                +"\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "Special Thanks\n"
                + "Someone\n"
                + "Someone\n"
                + "Someone\n"
                + "Thanks for playing!";

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        switch (sceneNum){
            case skeletonLord: scene_skeletonLord(); break;
            case ending: scene_ending(); break;
        }
    }
    public void scene_skeletonLord(){

        if (scenePhase == 0){

            gp.bossBattleOn = true;

            //shut iron door
            for (int i = 0; i < gp.obj[1].length; i++){

                if (gp.obj[gp.currentMap][i] == null){
                    gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize * 25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize * 28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.playSE(21);
                    break;
                }
            }
            //search for vacant slot for dummy
            for (int i = 0; i < gp.npc[1].length; i++){
                if (gp.npc[gp.currentMap][i] == null){
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }

            gp.player.drawing = false;

            scenePhase++;
        }
        if (scenePhase == 1){
            gp.player.worldY -= 2;

            if (gp.player.worldY < gp.tileSize*16){
                scenePhase++;
            }
        }
        if (scenePhase == 2){

            //search the boss
            for (int i = 0; i < gp.monster[1].length; i++){

                if (gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals(MON_Skeleton_Lord.monName)){

                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];
                    gp.ui.resetDialogueText();
                    scenePhase++;
                    break;
                }
            }
        }
        if (scenePhase == 3){

            //boss speaks
            gp.ui.drawDialogueState();
        }
        if (scenePhase == 4){

            //return camera to player

            //search for the dummy
            for (int i = 0; i < gp.npc[1].length; i++){

                if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)){
                    //restore player position
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                    //delete dummy
                    gp.npc[gp.currentMap][i] = null;
                    break;
                }
            }

            //redraw player
            gp.player.drawing = true;

            //reset
            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = gp.playState;

            //change music
            gp.stopMusic();
            gp.playMusic(22);
        }
    }

    public void scene_ending(){

        if (scenePhase == 0){

            gp.stopMusic();
            gp.ui.npc = new OBJ_Blue_Heart(gp);
            scenePhase++;
        }
        if (scenePhase == 1){

            //display dialogues
            gp.ui.drawDialogueState();
        }
        if (scenePhase == 2){

            gp.playSE(4);
            scenePhase++;
        }
        if (scenePhase == 3){
            //300 frames is 5 seconds
            if (counterReached(300)){
                scenePhase++;
            }
        }
        if (scenePhase == 4){

            //screen gets darker
            alpha += 0.005f;
            if (alpha > 1f){
                alpha = 1f;
            }
            drawBlackBackground(alpha);
            if (alpha == 1f){
                alpha = 0;
                scenePhase++;
            }
        }
        if (scenePhase == 5){

            drawBlackBackground(1f);
            alpha += 0.005f;
            if (alpha > 1f){
                alpha = 1f;
            }

            String text = "After the fierce battle with the Skeleton Lord, \n"
                    + "the Blue Boy finally found the legendary treasure.\n"
                    + "But this is not the end of his journey.\n"
                    + "The Blue Boy's adventure has just begun.";
            drawString(alpha,38f,200,text,70);

            //wait 10 seconds
            if (counterReached(600)){
                gp.playMusic(0);
                scenePhase++;
            }
        }
        if (scenePhase == 6){

            drawBlackBackground(1f);

            drawString(1f,120,gp.screenHeight/2,"Blue Boy Adventure",40);

            //wait 8 seconds
            if (counterReached(480)){

                scenePhase++;
            }
        }
        if (scenePhase == 7){

            drawBlackBackground(1f);

            y = gp.screenHeight /2;

            drawString(1f,38f,y, endCredit, 40);

            //wait 8 seconds
            if (counterReached(480)){
                scenePhase++;
            }
        }
        if (scenePhase == 8) {

            drawBlackBackground(1f);
            //scrolling credits
            y--;
            drawString(1f,38f,y,endCredit,40);

            int textHeight = getTextHeight(endCredit, 40);
            if (y + textHeight < 0){
                scenePhase++;
            }
        }
        if (scenePhase == 9){

            drawBlackBackground(1f);
            sceneNum = NA;
            scenePhase = 0;
            alpha = 0f;
            counter = 0;
            gp.ui.npc = null;
            gp.ui.titleScreenState = 0;
            gp.gameState = gp.titleState;
        }

    }

    public boolean counterReached(int target){

        boolean counterReached = false;

        counter++;
        if (counter > target){
            counterReached = true;
            counter = 0;
        }
        return counterReached;
    }

    public void drawBlackBackground(float alpha){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g2.setColor(Color.black);
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for (String line : text.split("\n")){
            int x = gp.ui.getXForCenteredText(line);
            g2.drawString(line,x,y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
private int getTextHeight(String text, int lineHeight){
    return text.split("\n").length * lineHeight;
}
}
