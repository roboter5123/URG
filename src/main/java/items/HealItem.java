package items;

import entities.Entity;
import entities.Interactable;
import entities.Player;
import javafx.scene.image.Image;
import utilities.Map;

public class HealItem implements Interactable {

    int heal = 1;
    int yPos;
    int xPos;
    Image image = new Image("uielements/HalfHeart.png");

    public HealItem(int yPos, int xPos) {

        this.yPos = yPos;
        this.xPos = xPos;
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
