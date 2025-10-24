package Main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    final int ogTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = ogTileSize * scale; //48x48 tile

    //maintain 4:3 ratio
    public final int maxScreenCol = 20; // 16 horizontal tiles
    public final int maxScreenRow = 12; // 12 vertical tiles
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 1;

    //full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //FPS
    int FPS = 60;

    //System
    public KeyHandler keyH = new KeyHandler(this);
    public TileManager tileM = new TileManager(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);


    //Entity and object
    public Player player = new Player(this,keyH);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
    //public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAMESTATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;





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
        eManager.setup();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn){
        setFullScreen();
        }

    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * currently not resetting daytime when you press 'retry' not sure how i want to handle so will come back to it
     * @param restart
     */
    public void resetGame(boolean restart){

        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if (restart) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }


    public void setFullScreen() {

        //get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //get full screen width/height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
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
                drawToTempScreen();//draw to the buffered image
                drawToScreen();//draw buffered image to screen
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
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }


        for (int i = 0; i < monster[1].length; i++) {
            if (monster[currentMap][i] != null) {
                if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                    monster[currentMap][i].update();
                }
                if (!monster[currentMap][i].alive) {
                    monster[currentMap][i].checkDrop();
                    monster[currentMap][i] = null;
                }
            }
        }
        for (int i = 0; i < projectile[1].length; i++) {
            if (projectile[currentMap][i] != null) {
                if (projectile[currentMap][i].alive) {
                    projectile[currentMap][i].update();
                }
                if (!projectile[currentMap][i].alive) {
                    projectile[currentMap][i] = null;
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

        for (int i = 0; i < iTile[1].length;i++){
            if (iTile[currentMap][i] != null){
                iTile[currentMap][i].update();
            }
        }
        eManager.update();
    }
        if (gameState == pauseState){
            // do nothing for now
        }

    }

    public void drawToTempScreen() {

        // Debug
        long drawStart = 0;
        if (keyH.showDebugText){
            drawStart = System.nanoTime();
        }


        //Title screen
        if (gameState == titleState){

            ui.draw(g2);
        } else if (gameState == mapState) {
            map.drawFullMapScreen(g2);
        }
        //other
        else {
            //tile
            tileM.draw(g2);

            //interactive tiles
            for (int i = 0; i < iTile[1].length; i++){
                if (iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }
            //add entities to list
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i ++){
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
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

            eManager.draw(g2);

            //miniMap
            map.drawMiniMap(g2);

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
    }

    public void drawToScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0,0,screenWidth2,screenHeight2,null);
        g.dispose();
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
