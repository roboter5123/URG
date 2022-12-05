package entities;

import gui.Sprite;
import javafx.scene.image.Image;
import utilities.Map;

public class Player extends Entity implements Interactable, Drawable {

    private final int reach = 2;

    public Player(int maxHealth) {

        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setDmg(1);
        this.setSprite(new Sprite(new Image("entities/Player.png")));
    }

    /**
     * Moves the player in the direction they chose.
     * @param map The map on which the player should move.
     * @param input Can be WASD and is used to pick the direction to move the player in.
     */
    public void move(Map map, String input){

        switch (input) {
            case "D", "+x", "d" -> super.move(+1, 'x', map);
            case "A", "-x", "a" -> super.move(-1, 'x', map);
            case "S", "+y", "s" -> super.move(1, 'y', map);
            case "W", "-y", "w" -> super.move(-1, 'y', map);
        }
    }

    /**
     * If the interacting entity is an opponent the player looses health determined by the opponents dmg value.
     * @param entity The entity that interacts with this object.
     * @param map    The map on which the interaction happens.
     */
    @Override
    public void interact(Entity entity, Map map) {

        if (entity instanceof Opponent){

            this.loseHealth(entity.getDmg());
        }
    }
}


