package entities;

import utilities.Map;

public class Player extends Entity {

    private final int reach = 2;

    public Player(int maxHealth) {

        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setDmg(1);
        this.setSprite(" P ");
    }

    public void move(Map map, String input){

        switch (input) {
            case "D", "+x", "d" -> super.move(+1, 'x', map);
            case "A", "-x", "a" -> super.move(-1, 'x', map);
            case "S", "+y", "s" -> super.move(1, 'y', map);
            case "W", "-y", "w" -> super.move(-1, 'y', map);
        }
    }

    public int getReach() {

        return reach;
    }
}


