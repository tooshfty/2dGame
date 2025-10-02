package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    GamePanel gp;
    //Debug
    boolean checkDrawTime = false;

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

            if (gp.ui.titleScreenState == 0){
                if (code==KeyEvent.VK_W){
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum<0){
                        gp.ui.commandNum = 2;
                    }
                }
                if (code==KeyEvent.VK_S){
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
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum<0){
                        gp.ui.commandNum = 3;
                    }
                }
                if (code==KeyEvent.VK_S){
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


        //play state
        if (gp.gameState == gp.playState){
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
            }
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }

        //debug
        if (code==KeyEvent.VK_T){
            if (!checkDrawTime){
                checkDrawTime = true;
            } else {
                checkDrawTime = false;
            }
        }

        //pause state
        else if (gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }

        //dialogue state
        else if (gp.gameState == gp.dialogueState){
            if (code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
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
    }
}
