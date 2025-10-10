package object;

import Main.GamePanel;
import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {


    public OBJ_Boots(GamePanel gp){
        super(gp);

        name = "Boots";
        down1 = setup("/objects/boots",gp.tileSize,gp.tileSize);
        description = "[" + name + "]\nAn old set of boots.";
    }
}
