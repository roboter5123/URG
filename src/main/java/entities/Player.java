package entities;

import javafx.scene.image.Image;
import utilities.Map;

public class Player extends Entity implements Interactable {

    private final int reach = 2;

    public Player(int maxHealth) {

        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setDmg(1);
        this.setSprite(" P ");
        this.setImage(new Image("entities/Player.png"));
    }

    public void move(Map map, String input){

        switch (input) {
            case "D", "+x", "d" -> super.move(+1, 'x', map);
            case "A", "-x", "a" -> super.move(-1, 'x', map);
            case "S", "+y", "s" -> super.move(1, 'y', map);
            case "W", "-y", "w" -> super.move(-1, 'y', map);
        }
    }

    @Override
    public void interact(Entity entity, Map map) {

        if (entity instanceof Opponent){

            this.looseHealth(entity.getDmg());
        }
    }
}


