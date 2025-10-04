package Main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    //BufferedImage keyImage;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0: first screen, 1: second screen


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

        //not currently using key obj
        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image;
    }

    public void showMessage(String text){

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

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
        }
        //Pause state
        if (gp.gameState == gp.pauseState) {
            //pause state
            drawPlayerLife();
            drawPauseScreen();

        }

        //Dialogue State
        if (gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueState();
        }

    }

    public void drawDialogueState() {

        //window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 5;
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32));
        x += gp.tileSize ;
        y += gp.tileSize;

        // essentially line breaks at the given character
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x ,y);
            y += 40;
        }

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
    }

    public void drawTitleScreen(){

        if (titleScreenState == 0){
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

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

            text = "Quit Game";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x , y);
            if (commandNum == 2) {
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

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,100));
        String text = "paused";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x,y);

    }

    public int getXForCenteredText(String text){

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.screenWidth/2 - length /2;
    }
}









//treasure game info
//        if (gameFinished) {
//
//            g2.setFont(arial_40);
//            g2.setColor(Color.yellow);
//
//            String text;
//            int textLength;
//            int x;
//            int y;
//
//            text = "You found the treasure!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
//            x = gp.screenWidth/2 - textLength/2;
//            y = gp.screenHeight/2 - (gp.tileSize * 3);
//            g2.drawString(text, x,y);
//
//
//            text = "Your time is: " + dFormat.format(playTime) + "!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
//            x = gp.screenWidth/2 - textLength/2;
//            y = gp.screenHeight/2 - (gp.tileSize * 4);
//            g2.drawString(text, x,y);
//
//            g2.setFont(arial_80B);
//            g2.setColor(Color.yellow);
//            text = "Congratulations!";
//            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
//            x = gp.screenWidth/2 - textLength/2;
//            y = gp.screenHeight/2 + (gp.tileSize * 2);
//            g2.drawString(text, x,y);
//
//            gp.gameThread = null;
//
//        } else {
//            g2.setFont(arial_40);
//            g2.setColor(Color.yellow);
//            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize,null);
//            g2.drawString("x " + gp.player.hasKey, 74, 65);
//
//            playTime += (double) 1/60;
//            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);
//
//            if (messageOn){
//                g2.setFont(g2.getFont().deriveFont(30F));
//                g2.drawString(message, gp.tileSize/2, gp.tileSize * 5 );
//
//                messageCounter++;
//                if (messageCounter > 120) {
//                    messageCounter = 0;
//                    messageOn = false;
//                }
//            }
//        }
