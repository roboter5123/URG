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

    @Override
    public void interact(Entity entity, Map map) {

        if (entity instanceof Player){

            if (entity.heal(heal)){

                this.selfDestruction(map);
            }
        }
    }

    public void selfDestruction(Map map){

        map.getSpaces()[yPos][xPos].setItemOnField(null);
    }

    @Override
    public Image getImage() {

        return this.image;
    }
}
