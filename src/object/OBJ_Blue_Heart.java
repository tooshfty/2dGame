package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Blue_Heart extends Entity {

    GamePanel gp;
    public static final String objName = "Blue Heart";

    public OBJ_Blue_Heart(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        down1 = setup("/objects/blueheart",gp.tileSize,gp.tileSize);

        setDialogue();
    }

    public void setDialogue(){

        dialogues[0][0] = "You pick up a beautiful blue gem.";
        dialogues[0][1] = "The legendary blue heart!";

    }

    public boolean use(Entity entity){

        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }
}
