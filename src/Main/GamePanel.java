package Main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    final int ogTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = ogTileSize * scale; //48x48 tile

    //maintain 4:3 ratio
    public final int maxScreenCol = 16; // 16 horizontal tiles
    public final int maxScreenRow = 12; // 12 vertical tiles
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    //System
    public KeyHandler keyH = new KeyHandler(this);
    TileManager tileM = new TileManager(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public EventHandler eHandler = new EventHandler(this);


    //Entity and object
    public Player player = new Player(this,keyH);
    public Entity[] obj = new Entity[20];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    public InteractiveTile[] iTile = new InteractiveTile[50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAMESTATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;





    //GamePanel constructor
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = titleState;
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; //1 billion nanoseconds over 60fps is updating every .0166 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer > 1000000000) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }


        for (int i = 0; i < monster.length; i++) {
            if (monster[i] != null) {
                if (monster[i].alive && !monster[i].dying) {
                    monster[i].update();
                }
                if (!monster[i].alive) {
                    monster[i].checkDrop();
                    monster[i] = null;
                }
            }
        }
        for (int i = 0; i < projectileList.size(); i++) {
            if (projectileList.get(i) != null) {
                if (projectileList.get(i).alive) {
                    projectileList.get(i).update();
                }
                if (!projectileList.get(i).alive) {
                    projectileList.remove(i);
                }
            }
        }

        for (int i = 0; i < particleList.size(); i++) {
            if (particleList.get(i) != null) {
                if (particleList.get(i).alive) {
                    particleList.get(i).update();
                }
                if (!particleList.get(i).alive) {
                    particleList.remove(i);
                }
            }
        }

        for (int i = 0; i<iTile.length;i++){
            if (iTile[i] != null){
                iTile[i].update();
            }
        }
    }
        if (gameState == pauseState){
            // do nothing for now
        }

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Debug
        long drawStart = 0;
        if (keyH.showDebugText){
            drawStart = System.nanoTime();
        }


        //Title screen
        if (gameState == titleState){

            ui.draw(g2);
        }
        //other
        else {
            //tile
            tileM.draw(g2);

            //interactive tiles
            for (int i = 0; i < iTile.length; i++){
                if (iTile[i] != null){
                    iTile[i].draw(g2);
                }
            }
            //add entities to list
            entityList.add(player);

            for (int i = 0; i < npc.length; i ++){
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            //sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });

            //draw entities
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //empty entity list
            entityList.clear();

            //ui
            ui.draw(g2);
        }

        //Debug
        if (keyH.showDebugText){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial",Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX " + player.worldX,x,y);
            y+= lineHeight;
            g2.drawString("WorldY " + player.worldY,x,y);
            y+= lineHeight;
            g2.drawString("Col " + (player.worldX + player.solidArea.x)/tileSize,x,y);
            y+= lineHeight;
            g2.drawString("Row " + (player.worldY + player.solidArea.y)/tileSize,x,y);

            //g2.drawString("Drawtime: " + passed, x, y);
            System.out.println("Draw Time: " + passed);
        }


        g2.dispose();
        
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }




}
