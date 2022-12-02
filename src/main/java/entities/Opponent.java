package entities;

import javafx.scene.image.Image;
import utilities.Map;
import ai.AI;
import ai.Orc;

import java.util.Random;

public class Opponent extends Entity {

    private AI ai;
    public String race;

    public Opponent(Player player, OponentType type) {

        if (type == OponentType.ORC) {

            this.ai = new Orc(player, this);
            this.setSprite("Orc");
            this.setImage(new Image("entities/Orc.png"));

        }

        this.setMaxHealth(ai.getMaxHealth());
        this.setHealth(this.getMaxHealth());
        this.setDmg(ai.getDmg());
        this.setReach(ai.getReach());
    }

    public void move(Map map, Random random) {
//      TODO
//        Fix them moving 2 fields when not in sight and rework pathfinding in general
        String move = this.ai.calculateMovementDirection(map, random);

        if (move == null) {

            return;
        }

        super.move(Integer.parseInt(move.split(" ")[0] + 1) * ai.getReach(), move.charAt(2), map);
    }

    public int getReach() {

        return reach;
    }

    public void setReach(int reach) {

        this.reach = reach;
    }
}