package Main;

import Main.monster.*;
import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana_Crystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UI {

    // Core References
    GamePanel gp;
    Graphics2D g2;

    // Fonts & HUD Assets
    public Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;

    // Messaging & Dialogue
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    int charIndex = 0;
    String combinedText = "";

    // Menu & Navigation State
    public int commandNum = 0;
    public int titleScreenState = 0; //0: first screen, 1: second screen
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    public int monsterSlotCol = 0;
    public int monsterSlotRow = 0;
    static final int SPAWN_NAVIGATE = 0;
    static final int SPAWN_QTY = 1;
    int subState = 0;
    int spawnSubState = SPAWN_NAVIGATE;
    private int spawnQty = 1;
    private static final int SPAWN_QTY_MIN = 1;
    private static final int SPAWN_QTY_MAX = 5;
    int counter = 0;
    public Entity npc;



    public UI(GamePanel gp){
        this.gp = gp;


        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e){
            e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }

        //create hud image
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJ_Mana_Crystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;

    }

    public void resetMonsterSpawnMenu() {
        spawnSubState = SPAWN_NAVIGATE;
        spawnQty = SPAWN_QTY_MIN;

        // Clamp the selection cursor so it always points to a valid registry entry.
        int maxIndex = MonsterRegistry.size() - 1;
        if (maxIndex < 0) {
            monsterSlotCol = 0;
            monsterSlotRow = 0;
            return;
        }

        int cursorIndex = getItemIndexOnSlot(monsterSlotCol, monsterSlotRow);
        if (cursorIndex > maxIndex) {
            monsterSlotCol = 0;
            monsterSlotRow = 0;
        }
    }

    public void resetDialogueText() {
        currentDialogue = "";
        combinedText = "";
        charIndex = 0;
    }

    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);

    }

    public void draw(Graphics2D g2) {
        //maybe replace all this with a switch statement
        this.g2 = g2;

        g2.setFont(maruMonica);
        //g2.setFont(purisaB);
        //g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        //Title State
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //Play state
        if (gp.gameState == gp.playState){
            drawPlayerLife();
            drawMonsterLife();
            drawMessage();
        }
        //Pause state
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        //Dialogue State
        if (gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueState();
        }
        //Character State
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player,true);
        }
        //Options state
        if (gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
        //Game Over state
        if (gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        //Transition state
        if (gp.gameState == gp.transitionState){
            drawTransition();
        }
        //Trade state
        if (gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
        //Sleep state
        if (gp.gameState == gp.sleepState){
            drawSleepScreen();
        }
    }

    public void drawSleepScreen() {

        counter++;

        if (counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;
            if (gp.eManager.lighting.filterAlpha > 1f){
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }
        if (counter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.001f;
            if (gp.eManager.lighting.filterAlpha <= 0f){
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.playState;
                gp.player.getPlayerImage();
            }
        }
    }


    public void drawTradeScreen() {

        switch (subState){
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }
        gp.keyH.enterPressed = false;
    }
    public void trade_select() {

        npc.dialogueSet = 0;
        drawDialogueState();

        //draw window
        int x = gp.tileSize * 14;
        int y = (int)(gp.tileSize * 5.5);
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x,y,width,height);

        //draw text
        x += gp.tileSize;
        y += gp.tileSize;

        g2.drawString("Buy",x,y);
        if (commandNum == 0){
            g2.drawString(">",x-24,y);
            if (gp.keyH.enterPressed){
                subState = 1;
            }
        }
        y += gp.tileSize;

        g2.drawString("Sell",x,y);
        if (commandNum == 1){
            g2.drawString(">",x-24,y);
            if (gp.keyH.enterPressed){
                subState = 2;
            }
        }
        y += gp.tileSize;

        g2.drawString("Leave",x,y);
        if (commandNum == 2){
            g2.drawString(">",x-24,y);
            if (gp.keyH.enterPressed){
                commandNum = 0;
                npc.startDialogue(npc,1);
            }
        }
    }
    public void trade_buy(){

        //draw player inventory
        drawInventory(gp.player, false);
        //draw npc inventory
        drawInventory(npc, true);

        //draw hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //draw coin window
        x = gp.tileSize * 12;
        y = (int)(gp.tileSize * 6);
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Coins: " + gp.player.coin, x+24, y+60);

        //draw price window
        int itemIndex = getItemIndexOnSlot(npcSlotCol,npcSlotRow);
        if (itemIndex < npc.inventory.size()) {

            x = (int) (gp.tileSize * 5.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);
            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXForAlignToRightText(text, gp.tileSize * 8 - 20);
            g2.drawString(text, x, y + 34);


            //buy item---------for some reason the transaction ends when you don't have enough space or coins
            if (gp.keyH.enterPressed) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    npc.startDialogue(npc,2);
                }
                else {
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex))){
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else {
                        subState = 0;
                        npc.startDialogue(npc,3);
                    }
                }
            }
        }
    }
    public void trade_sell(){

        //draw player inventory
        drawInventory(gp.player, true);
        int x;
        int y;
        int width;
        int height;
        //draw hint window
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //draw coin window
        x = gp.tileSize * 12;
        y = (int)(gp.tileSize * 9);
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Coins: " + gp.player.coin, x+24, y+60);

        //draw price window
        int itemIndex = getItemIndexOnSlot(playerSlotCol,playerSlotRow);
        if (itemIndex < gp.player.inventory.size()) {

            x = (int) (gp.tileSize * 15.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);
            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXForAlignToRightText(text, gp.tileSize * 18 - 20);
            g2.drawString(text, x, y + 34);


            //sell item
            if (gp.keyH.enterPressed) {

                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentShield) {

                    commandNum = 0;
                    subState = 0;
                    npc.startDialogue(npc, 4);
                } else {
                    if (gp.player.inventory.get(itemIndex).amount > 1){
                        gp.player.inventory.get(itemIndex).amount--;
                    }
                    else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }
            }
        }

    }

    public void drawTransition() {

        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        if (counter == 50) {
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }

    public void drawGameOverScreen() {

        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "Game Over";
        //Shadow
        g2.setColor(Color.black);
        x = getXForCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text,x,y);
        //text
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry?";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.drawString(">",x-40,y);
        }

        //back to title screen
        text = "Quit";
        x = getXForCenteredText(text);
        y += 55;
        g2.drawString(text,x,y);
        if (commandNum == 1){
            g2.drawString(">",x-40,y);
        }
    }

    public void drawOptionsScreen(){

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch (subState){
            case 0:
                options_top(frameX,frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX,frameY);
                break;
            case 2:
                options_control(frameX,frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX,frameY);
                break;
        }
        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY){

        int textX;
        int textY;

        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        //Full screen toggle
        textX = frameX + gp.tileSize;
        textY += gp.tileSize *2;
        g2.drawString("Fullscreen",textX,textY);
        if (commandNum == 0) {
            g2.drawString(">",textX - 25,textY);
            if (gp.keyH.enterPressed){
                if (!gp.fullScreenOn){
                    gp.fullScreenOn = true;
                } else if (gp.fullScreenOn) {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        //music toggle
        textY += gp.tileSize;
        g2.drawString("Music",textX,textY);
        if (commandNum == 1) {
            g2.drawString(">",textX - 25,textY);
        }
        //se
        textY += gp.tileSize;
        g2.drawString("SE",textX,textY);
        if (commandNum == 2) {
            g2.drawString(">",textX - 25,textY);
        }
        //control
        textY += gp.tileSize;
        g2.drawString("Control",textX,textY);
        if (commandNum == 3) {
            g2.drawString(">",textX - 25,textY);
            if (gp.keyH.enterPressed){
                subState = 2;
                commandNum =0;
            }
        }
        //quit game
        textY += gp.tileSize;
        g2.drawString("Exit",textX,textY);
        if (commandNum == 4) {
            g2.drawString(">",textX - 25,textY);
            if (gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }
        //back
        textY += gp.tileSize * 2;
        g2.drawString("Back",textX,textY);
        if (commandNum == 5) {
            g2.drawString(">",textX - 25,textY);
            if (gp.keyH.enterPressed){
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //fullscreen check box
        textX = frameX + (int)(gp.tileSize * 4.5);
        textY = frameY + (gp.tileSize * 2) + 24;
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(textX,textY,24,24);
        if (gp.fullScreenOn){
            g2.fillRect(textX,textY,24,24);
        }

        //music volume
        textY +=gp.tileSize;
        g2.drawRect(textX,textY,120,24); // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        //se volume
        textY +=gp.tileSize;
        g2.drawRect(textX,textY,120,24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        gp.config.saveConfig();
    }
    public void options_endGameConfirmation(int frameX, int frameY){

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "quit game and \nreturn to the title screen?";

        for (String line: currentDialogue.split("\n")){
            g2.drawString(line, textX,textY);
            textY += 40;
        }
        //yes
        String text = "Yes";
        textX = getXForCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text,textX,textY);
        if (commandNum == 0){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState;
                gp.music.stop();
                gp.resetGame(true);
            }
        }
        //no
        text = "no";
        textX = getXForCenteredText(text);
        textY += gp.tileSize ;
        g2.drawString(text,textX,textY);
        if (commandNum == 1){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed){
                subState = 0;
                commandNum = 4;
            }
        }

    }
    public void options_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        currentDialogue = "The change will take \neffect after restarting \nthe game";

        for (String line : currentDialogue.split("\n")){
            g2.drawString(line, textX,textY);
            textY += 40;
        }


        //Back
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX,textY);
        if (commandNum == 0){
            g2.drawString(">",textX -25,textY);
            if (gp.keyH.enterPressed){
                subState = 0;
            }
        }

    }

    public void options_control(int frameX, int frameY){

        int textX;
        int textY;

        //string title
        String text = "Control";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("Confirm/Attack", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("Shoot/Cast", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("Character Screen", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("Pause", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("Options", textX,textY);

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("Enter", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("F", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("C", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("P", textX,textY);
        textY+= gp.tileSize;
        g2.drawString("Escape", textX,textY);

        //Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX,textY);
        if (commandNum == 0) {
            g2.drawString(">",textX -25, textY);
            if (gp.keyH.enterPressed){
                subState = 0;
                commandNum = 3;
            }
        }
    }


    public void drawDialogueState() {

        //window
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += gp.tileSize ;
        y += gp.tileSize;

        if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null){

            //currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];

            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if (charIndex < characters.length){
                gp.playSE(17);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if (gp.keyH.enterPressed){
                charIndex = 0;
                combinedText = "";

                if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState){

                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        }
        //if no text in array
        else {
            npc.dialogueIndex = 0;

            if (gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
            }
            if (gp.gameState == gp.cutsceneState){
                gp.csManager.scenePhase++;
            }
        }

        // essentially line breaks at the given character
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x ,y);
            y += 40;
        }

    }
    public void drawCharacterScreen() {

        //create frame
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        //text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        //names
        g2.drawString("Level", textX,textY);
        textY += lineHeight;
        g2.drawString("Life", textX,textY);
        textY += lineHeight;
        g2.drawString("Mana", textX,textY);
        textY += lineHeight;
        g2.drawString("Strength", textX,textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX,textY);
        textY += lineHeight;
        g2.drawString("Attack", textX,textY);
        textY += lineHeight;
        g2.drawString("Defense", textX,textY);
        textY += lineHeight;
        g2.drawString("Exp", textX,textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX,textY);
        textY += lineHeight;
        g2.drawString("Coin", textX,textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX,textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX,textY);
        textY += lineHeight;

        //values
        int tailX = (frameX + frameWidth) - 30;

        //reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1,tailX - gp.tileSize,textY - 24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24 , null);

    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5,y+5,width - 10,height -10,25 ,25);
    }
    public void drawInventory(Entity entity, boolean cursor){

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player){
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }
        //Frame
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        //cursor
        if (cursor){
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;
            //draw cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;

            //draw description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
            if (itemIndex < entity.inventory.size()) {

                drawSubWindow(dFrameX,dFrameY, dFrameWidth,dFrameHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }

            }
        }

        //draw items
        for (int i = 0; i < entity.inventory.size(); i++){

            //draw cursor
            if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i)== entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize, 10, 10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            //display amount
            if (entity == gp.player && entity.inventory.get(i).amount > 1) {

                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;
                String s = "" + entity.inventory.get(i).amount;
                amountX = getXForAlignToRightText(s,slotX + 44);
                amountY = slotY + gp.tileSize;
                //shadow
                g2.setColor(new Color(60,60,60));
                g2.drawString(s,amountX,amountY);
                //number
                g2.setColor(Color.white);
                g2.drawString(s,amountX-3,amountY-3);
            }

            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }


    }
    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }



    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {

            if (message.get(i) != null) {

                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX +2, messageY +2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i,counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen(){

        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        if (titleScreenState == 0){


            //Title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "Game Game";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;

            //shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);
            //main color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //blue boy image
            x = gp.screenWidth / 2 - (gp.tileSize*2)/2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2, null);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "New Game";
            x = getXForCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x , y);
            if (commandNum == 0) {
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Load Game";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x , y);
            if (commandNum == 1) {
                g2.drawString(">",x-gp.tileSize,y);
            }
            text = "Test Game";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x , y);
            if (commandNum == 2) {
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Quit Game";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x , y);
            if (commandNum == 3) {
                g2.drawString(">",x-gp.tileSize,y);
            }
        }
        else if (titleScreenState == 1){

            //Class selection screen
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text,x,y);

            text = "Fighter";
            x = getXForCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text,x,y);
            if (commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Thief";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Back";
            x = getXForCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text,x,y);
            if (commandNum == 3){
                g2.drawString(">", x-gp.tileSize, y);
            }

        }
    }

    public void drawPauseScreen(){

        if(gp.currentMap == gp.tileM.TESTING_MAP){
            drawTestMonSpawner(true);
        }else {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100));
            String text = "paused";
            int x = getXForCenteredText(text);
            int y = gp.screenHeight / 2;

            g2.drawString(text, x, y);
        }

    }
    public void drawTestMonSpawner(boolean cursor){

        // 1) MAIN FRAME (unchanged)
        int frameX = gp.tileSize * 12;
        int frameY = gp.tileSize;
        int frameWidth  = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // 2) GRID DRAW (unchanged except source is MonsterRegistry + MonsterIconCache)
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        int count = MonsterRegistry.size();
        if (count == 0) {
            g2.setFont(g2.getFont().deriveFont(20F));
            g2.drawString("No monsters registered", slotXStart, slotYStart + gp.tileSize);
        }else {
            for (int i = 0; i < count; i++) {
                var meta = MonsterRegistry.get(i);
                if (meta != null) {
                    var icon = MonsterIconCache.getIcon(gp, meta.key());
                    if (icon != null) {
                        g2.drawImage(icon, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    } else {
                        g2.drawString(meta.displayName(), slotX + 6, slotY + gp.tileSize / 2 + 6);
                    }
                }
                slotX += slotSize;
                if (i == 4 || i == 9 || i == 14) { slotX = slotXStart; slotY += slotSize; }
            }
        }

        // 3) CURSOR HIGHLIGHT (show during both substates)
        if (cursor) {
            int cursorX = slotXStart + (slotSize * monsterSlotCol);
            int cursorY = slotYStart + (slotSize * monsterSlotRow);
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, gp.tileSize, gp.tileSize, 10, 10);
        }

        // 4) DESCRIPTION PANEL (your existing label.draw loop, only uses selected index)
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth  = frameWidth;
        int dFrameHeight = gp.tileSize * 3;
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        int selIndex = getItemIndexOnSlot(monsterSlotCol, monsterSlotRow);
        if (selIndex >= 0 && selIndex < MonsterRegistry.size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            g2.setFont(g2.getFont().deriveFont(28F));
            String label = MonsterRegistry.getDisplayNameAt(selIndex);
            if (label == null) label = "(unknown)";
            for (String line : label.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
            g2.setFont(g2.getFont().deriveFont(18F));
            String hint = (spawnSubState == SPAWN_QTY)
                    ? "Confirm quantity with Enter"
                    : "Press Enter to choose quantity";
            g2.drawString(hint, textX, dFrameY + dFrameHeight - 24);
        }

        // 5) QTY OVERLAY (ONLY when sub-state is QTY)
        if (spawnSubState == SPAWN_QTY) {
            int qFrameW = gp.tileSize * 3;
            int qFrameH = gp.tileSize * 2;
            int qFrameX = frameX + (frameWidth - qFrameW)/2;
            int qFrameY = frameY + gp.tileSize; // slight drop
            drawSubWindow(qFrameX, qFrameY, qFrameW, qFrameH);

            int tx = qFrameX + 16;
            int ty = qFrameY + gp.tileSize/2 + 10;
            g2.setFont(g2.getFont().deriveFont(24F));
            g2.drawString("Quantity:", tx, ty);
            g2.setFont(g2.getFont().deriveFont(28F));
            g2.drawString(String.valueOf(spawnQty), tx + gp.tileSize * 2, ty);

            g2.setFont(g2.getFont().deriveFont(14F));
            g2.drawString("←/→ adjust, Enter confirm, Esc cancel", tx, ty + 24);
        }
    }
    public void updateSpawnMenu() {
        // Debounce-friendly: consume keys you use
        if (spawnSubState == SPAWN_NAVIGATE) {
            // Here, only handle the Enter transition into QTY mode:
            if (gp.keyH.enterPressed) {
                int idx = getItemIndexOnSlot(monsterSlotCol, monsterSlotRow);
                if (idx >= 0 && idx < MonsterRegistry.size()) {
                    spawnQty = SPAWN_QTY_MIN;
                    spawnSubState = SPAWN_QTY;
                }
                gp.keyH.enterPressed = false;
            }
        } else if (spawnSubState == SPAWN_QTY) {

            // In QTY mode: lock cursor; Left/Right adjust quantity; Enter confirms; Esc cancels
            if (gp.keyH.leftPressed)  { spawnQty = Math.max(SPAWN_QTY_MIN, spawnQty - 1); gp.keyH.leftPressed = false; }
            if (gp.keyH.rightPressed) { spawnQty = Math.min(SPAWN_QTY_MAX, spawnQty + 1); gp.keyH.rightPressed = false; }

            if (gp.keyH.enterPressed) {
                int idx = getItemIndexOnSlot(monsterSlotCol, monsterSlotRow);
                String key = MonsterRegistry.getKeyAt(idx);
                if (key != null) {
                    int spawned = MonsterFactory.spawnNearPlayer(key, spawnQty, gp, /*cap*/ 5);
                    // Optional: toast/message
                    System.out.println("Spawned " + spawned + " x " + key);
                }
                gp.keyH.enterPressed = false;
                spawnSubState = SPAWN_NAVIGATE;
                spawnQty = SPAWN_QTY_MIN;
            }
            // If you have a back/cancel key in KeyHandler, use it here:
            if (gp.keyH.escapePressed) {
                spawnSubState = SPAWN_NAVIGATE;
                spawnQty = SPAWN_QTY_MIN;
                gp.keyH.escapePressed = false;
            }
        }
    }

    public void drawPlayerLife(){


        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //draw max life
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x+= gp.tileSize;

        }

        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //draw current life
        while(i<gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+= gp.tileSize;
        }

        //draw max mana
        x = (gp.tileSize / 2) - 5;
        y = (int)(gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_blank,x,y,null);
            i++;
            x += 35;
        }

        //draw mana
        x = (gp.tileSize / 2) - 5;
        y = (int)(gp.tileSize * 1.5);
        i = 0;
        while(i< gp.player.mana){
            g2.drawImage(crystal_full,x,y,null);
            i++;
            x+=35;
        }
    }
    public void drawMonsterLife(){


        for (int i = 0; i < gp.monster[1].length; i++){
            Entity monster = gp.monster[gp.currentMap][i];

            if (monster != null && monster.inCamera()){

                if (monster.hpBarOn && !monster.boss) {

                    double oneScale = (double)gp.tileSize/monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    g2.setColor(new Color(35,35,35));
                    g2.drawRect(monster.getScreenX() - 1,monster.getScreenY() - 16,gp.tileSize + 2,12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int)hpBarValue, 10);

                    monster.hpBarCounter++;

                    if (monster.hpBarCounter > 600) {
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                } else if (monster.boss) {

                    double oneScale = (double)gp.tileSize*8/monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    int x = gp.screenWidth/2 - gp.tileSize*4;
                    int y = gp.tileSize * 10;

                    g2.setColor(new Color(35,35,35));
                    g2.drawRect(x-1,y-1,gp.tileSize*8 + 2,22);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int)hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD,24f));
                    g2.setColor(Color.white);
                    g2.drawString(monster.name, x+4, y-10);

                }
            }
        }


    }

    public int getXForCenteredText(String text){

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.screenWidth/2 - length /2;
    }

    public int getXForAlignToRightText(String text, int tailX) {

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
