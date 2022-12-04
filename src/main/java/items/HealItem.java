package items;

import entities.Entity;
import entities.Player;
import javafx.scene.image.Image;
import utilities.Map;

public class HealItem extends Item {

    int heal;

    public HealItem(int heal) {

        this.heal = heal;
        this.image = new Image("uielements/heart"+heal+".png");
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

    @Override
    public Image getImage() {

        return this.image;
    }
}
