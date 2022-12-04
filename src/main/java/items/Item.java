package items;

import entities.Entity;
import entities.Interactable;
import javafx.scene.image.Image;
import utilities.Map;

public abstract class Item implements Interactable {

    int yPos;
    int xPos;
    Image image;

    /**
     * @param entity The entity that interacts with this object.
     * @param map The map on which the interaction happens.
     */
    public void interact(Entity entity, Map map) {}

    /**
     * Deletes the item of the map.
     * @param map The map on which the Item is located.
     */
    public void selfDestruction(Map map) {

        map.getSpaces()[yPos][xPos].setItemOnField(null);
    }

    public void setPos(int yPos ,int xPos) {

        this.xPos = xPos;
        this.yPos = yPos;
    }
}
