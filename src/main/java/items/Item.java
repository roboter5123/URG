package items;

import entities.Drawable;
import entities.Entity;
import entities.Interactable;
import gui.Sprite;
import utilities.Map;

public abstract class Item implements Interactable, Drawable {

    int yPos;
    int xPos;
    Sprite sprite;

    /**
     * @param entity The entity that interacts with this object.
     * @param map    The map on which the interaction happens.
     */
    public void interact(Entity entity, Map map) {

    }

    /**
     * Deletes the item of the map.
     *
     * @param map The map on which the Item is located.
     */
    public void selfDestruction(Map map) {

        map.getSpaces()[yPos][xPos].setItemOnField(null);
    }

    /**
     * Sets the pixel dimensions the sprite will take once it's drawn on the canvas.
     *
     * @param height The height in pixels that the sprite will take up one the canvas once drawn on the canvas.
     * @param width  The width in pixels that the sprite will take up one the canvas once drawn on the canvas.
     */
    public void setDimensions(double height, double width) {

        this.sprite.setDimensions(height * 0.25, width * 0.25);
    }

    public void setPos(int yPos, int xPos) {

        this.xPos = xPos;
        this.yPos = yPos;
    }


}
