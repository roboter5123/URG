public class Player extends Entity {


    public Player() {}

    public Player( int health, int maxHealth) {

        this.setMaxHealth(maxHealth);
        this.setHealth(health);
        this.setDmg(1);
        this.setSprite("  P  ");
    }
}


