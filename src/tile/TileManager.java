package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][]; // LAYERS: map num ,
    boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();



    public TileManager(GamePanel gp){

        this.gp = gp;

        //READ TITLE DATA FILE
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //Getting tile names and collision info from file
        String line;
        try{
            while((line = br.readLine()) != null){
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        //Initialize tile array based on fileNames size
        tile = new Tile[fileNames.size()];
        getTileImage();
        //get maxworld col and row
        is = getClass().getResourceAsStream("/maps/dungeon01.txt");
        br = new BufferedReader(new InputStreamReader(is));
        try{
            String line2 = br.readLine();
            String[] maxTile = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

            br.close();

        }catch (IOException e){
            System.out.println("exception");
        }

        loadMap("/maps/worldmap.txt",0);
        loadMap("/maps/indoor01.txt",1);
        loadMap("/maps/dungeon01.txt",2);
        loadMap("/maps/dungeon02.txt",3);

    }

    public void getTileImage(){

        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            boolean collision = collisionStatus.get(i).equals("true");

            setup(i,fileName,collision);
        }

    }

    public void setup(int index, String imagePath, boolean collision) {

        UtilityTool utool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath));
            tile[index].image = utool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String filePath, int mapNum){

        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                while (col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[mapNum][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;


        while(worldCol< gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if (worldCol==gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
        if (drawPath){
            g2.setColor(new Color(255,0,0,70));

            for (int i = 0; i < gp.pFinder.pathList.size();i++){
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX,screenY,gp.tileSize, gp.tileSize);
            }
        }

    }


}
