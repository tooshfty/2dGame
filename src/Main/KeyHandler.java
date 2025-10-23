package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;
    GamePanel gp;
    //Debug
    boolean showDebugText = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //title state
        if (gp.gameState == gp.titleState){
            titleState(code);
        }
        //play state
        else if (gp.gameState == gp.playState){
            playState(code);
        }

        //pause state
        else if (gp.gameState == gp.pauseState){
            pauseState(code);
        }
        //dialogue state
        else if (gp.gameState == gp.dialogueState){
            dialogueState(code);
        }
        //Character state
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }
        //Option state
        else if (gp.gameState == gp.optionsState) {
            optionsState(code);
        }
        //Game Over state
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        //Trade state
        else if (gp.gameState == gp.tradeState){
            tradeState(code);
        }
        //Map state
        else if (gp.gameState == gp.mapState){
            mapState(code);
        }

        //debug
        if (code==KeyEvent.VK_T){
            if (!showDebugText){
                showDebugText = true;
            } else {
                showDebugText = false;
            }
        }
        //use this to debug without closing the game and relaunching 
        if (code == KeyEvent.VK_R){
            switch (gp.currentMap){
                case 0:
                    gp.tileM.loadMap("/maps/worldV3.txt",0);
                    break;
                case 1:
                    gp.tileM.loadMap("/maps/interior01.txt",1);
            }
        }
    }
    public void tradeState(int code){

        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
                gp.playSE(9);
            }
            if (code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
                gp.playSE(9);
            }
        }
        if (gp.ui.subState == 1){
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
        if (gp.ui.subState == 2){
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_ENTER){
            if (gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            } else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            gp.playSE(9);
            if (gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            gp.playSE(9);
            if (gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
        }


    }

    public void optionsState(int code){

        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0:
                maxCommandNum = 5;
                break;
            case 3:
                maxCommandNum = 1;
                break;
        }
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            gp.playSE(9);
            if (gp.ui.commandNum<0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            gp.playSE(9);
            if (gp.ui.commandNum>maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A){
            if (gp.ui.subState == 0){
                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.ui.subState == 0){
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5){
                    gp.se.volumeScale++;
                    gp.playSE(9);
                }
            }
        }
    }

    public void titleState(int code){

        if (gp.ui.titleScreenState == 0){
            if (code==KeyEvent.VK_W){
                gp.playSE(9);
                gp.ui.commandNum--;
                if (gp.ui.commandNum<0){
                    gp.ui.commandNum = 2;
                }
            }
            if (code==KeyEvent.VK_S){
                gp.playSE(9);
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum){
                    case 0:
                        gp.ui.titleScreenState = 1;
                        gp.playSE(1);
                        break;
                    case 1:
                        System.out.println("not ready");
                        break;
                    case 2:
                        System.exit(0);
                }
            }
        } else if (gp.ui.titleScreenState == 1){
            if (code==KeyEvent.VK_W){
                gp.playSE(9);
                gp.ui.commandNum--;
                if (gp.ui.commandNum<0){
                    gp.ui.commandNum = 3;
                }
            }
            if (code==KeyEvent.VK_S){
                gp.playSE(9);
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3){
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum){
                    case 0:
                        System.out.println("fighter");
                        gp.playSE(1);
                        gp.playMusic(0);
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        System.out.println("thief");
                        gp.playSE(1);
                        break;
                    case 2:
                        System.out.println("sorcerer");
                        gp.playSE(1);
                        break;
                    case 3:
                        gp.ui.titleScreenState = 0;
                        break;
                }
            }
        }
    }
    public void playState(int code){
        if (code==KeyEvent.VK_W){
            upPressed = true;
        }
        if (code==KeyEvent.VK_A){
            leftPressed = true;
        }
        if (code==KeyEvent.VK_S){
            downPressed = true;
        }
        if (code==KeyEvent.VK_D){
            rightPressed = true;
        }
        if (code==KeyEvent.VK_P){
            gp.gameState = gp.pauseState;
            System.out.println("pause pressed");
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_F){
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionsState;
        }
        if (code == KeyEvent.VK_M){
            gp.gameState = gp.mapState;
        }
        if (code == KeyEvent.VK_X){
            if (!gp.map.miniMapOn){
                gp.map.miniMapOn = true;
            }
            else {
                gp.map.miniMapOn = false;
            }
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }

    }
    public void pauseState(int code){
        System.out.println("pState called");
        if (code == KeyEvent.VK_P){
            System.out.println("p pressed");
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code){

        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }

        playerInventory(code);
    }

    public void mapState(int code){

        if (code == KeyEvent.VK_M){
            gp.gameState = gp.playState;
        }
    }
    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0){
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3){
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4){
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }
    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0){
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3){
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4){
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code==KeyEvent.VK_W){
            upPressed = false;
        }
        if (code==KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code==KeyEvent.VK_S){
            downPressed = false;
        }
        if (code==KeyEvent.VK_D){
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F){
            shotKeyPressed = false;
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
    }
}
