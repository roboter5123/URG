package items;

import entities.Entity;
import entities.Interactable;
import javafx.scene.image.Image;
import utilities.Map;

public abstract class Item implements Interactable {

    int yPos;
    int xPos;

    Image image;

    public void interact(Entity entity, Map map) {

    }

    public void selfDestruction(Map map) {

        map.getSpaces()[yPos][xPos].setItemOnField(null);
    }

    public void setPos(int yPos ,int xPos) {

        this.xPos = xPos;
        this.yPos = yPos;
    }
}
