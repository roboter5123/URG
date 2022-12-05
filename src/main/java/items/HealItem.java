package items;

import entities.Entity;
import entities.Player;
import gui.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utilities.Map;

public class HealItem extends Item {

    int heal;

    public HealItem(int heal) {

        this.heal = heal;
        this.sprite = new Sprite(new Image("uielements/heart"+heal+".png"));
    }

    /**
     * Heals the target entity by this items heal amount.
     * @param entity The entity that interacts with this object.
     * @param map    The map on which the interaction happens.
     */
    @Override
    public void interact(Entity entity, Map map) {

        if (entity instanceof Player){

            if (entity.heal(heal)){

                this.selfDestruction(map);
            }
        }
    }

    /**
     * Deletes the item of the map.
     * @param map The map on which the Item is located.
     */
    public void selfDestruction(Map map){

        map.getSpaces()[yPos][xPos].setItemOnField(null);
    }

    /**
     * Draws the drawable to the given graphics context
     *
     * @param yPos The y position for the top left pixel of the sprite.
     * @param xPos The x position for the top left pixel of the sprite.
     * @param gc   The graphics context of the canvas this sprite should be drawn on.
     */
    @Override
    public void draw(double yPos, double xPos, GraphicsContext gc) {
        this.sprite.draw(yPos,xPos,gc);
    }
}
