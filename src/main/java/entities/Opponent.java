package entities;

import ai.AI;
import ai.Orc;
import gui.Sprite;
import items.Item;
import javafx.scene.image.Image;
import utilities.Map;

import java.util.List;
import java.util.Random;

public class Opponent extends Entity implements Interactable {

    private AI ai;

    private List <Item> lootTable;

    public Opponent(Player player, OpponentType type) {

        if (type == OpponentType.ORC) {

            this.ai = new Orc(player, this);
            this.setSprite(new Sprite(new Image("entities/Orc.png")));
            this.lootTable = this.ai.getLootTable();

        }

        assert ai != null;
        this.setMaxHealth(ai.getMAX_HEALTH());
        this.setHealth(this.getMaxHealth());
        this.setDmg(ai.getDMG());
        this.setReach(ai.getReach());
    }

    /**
     * Moves the opponent in the direction their AI tells them to move.
     * @param map The map on which the entity should move.
     * @param random used to determin the movement direction in case of the player being out of sight.
     */
    public void move(Map map, Random random) {

        String move = this.ai.calculateMovementDirection(map, random);

        if (move == null) {

            return;
        }

        super.move(Integer.parseInt(move.split(" ")[0] + 1) * ai.getReach(), move.charAt(2), map);
    }

    /**
     * @return The item this opponent drops. Picked randomly from their AIs lootTable. Already set on a field.
     */
    public Item dropItem(){

        Item drop;
        Random random = new Random();
        int dropNumber = random.nextInt(this.lootTable.size());
        int yPos = this.getyPos();
        int xPos = this.getxPos();
        drop = this.lootTable.get(dropNumber);

        if (drop == null){

            return null;
        }

        drop.setPos(yPos,xPos);
        return drop;
    }

    /**
     * This entity takes damage when interacted with.
     * @param entity The entity that interacts with this object.
     * @param map    The map on which the interaction happens.
     */
    @Override
    public void interact(Entity entity, Map map) {

        this.loseHealth(entity.getDmg());
    }

    public int getReach() {

        return reach;
    }

    public void setReach(int reach) {

        this.reach = reach;
    }



}