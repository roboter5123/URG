public class Entity {

    private int xPos;
    private int yPos;
    private int maxHealth = 10;
    private int health;
    private int dmg;

    private String sprite;

    public Entity() {}

    public void move(int moves, char direction, Board board) {

        Space[][] spaces = board.getSpaces();
        Space curSpace = spaces[xPos][yPos];

        if (direction == 'x' && xPos + moves != spaces.length && xPos + moves >= 0) {

            if (spaces[xPos + moves][yPos].getEntityOnField() == null) {

                curSpace.setEntityOnField(null);
                xPos += moves;

            } else if (spaces[xPos + moves][yPos].getEntityOnField() != this) {

                attack(spaces[xPos+moves][yPos].getEntityOnField());
            }

        } else if (direction == 'y' && yPos + moves != spaces.length && yPos + moves >= 0) {

            if (spaces[xPos][yPos + moves].getEntityOnField() == null) {
                curSpace.setEntityOnField(null);
                yPos += moves;

            } else if (spaces[xPos][yPos + moves].getEntityOnField() != this){

                attack(spaces[xPos][yPos+moves].getEntityOnField());
            }
        }
        spaces[xPos][yPos].setEntityOnField(this);

    }

    public void attack(Entity entity) {

        entity.looseHealth(dmg);
    }

    public void looseHealth(int dmg) {

        health -= dmg;

    }

    public void heal(int heal){

        if (health + heal <= maxHealth){

        health += heal;

        }else{

            health = maxHealth;
        }

    }

    public int getxPos() {

        return xPos;
    }

    public void setxPos(int xPos) {

        this.xPos = xPos;
    }

    public int getyPos() {

        return yPos;
    }

    public void setyPos(int yPos) {

        this.yPos = yPos;
    }

    public int getHealth() {

        return health;
    }

    public void setHealth(int health) {

        this.health = health;
    }

    public int getMaxHealth() {

        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {

        this.maxHealth = maxHealth;
    }

    public int getDmg() {

        return dmg;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void setDmg(int dmg) {

        this.dmg = dmg;
    }

    @Override
    public String toString() {
        return this.getClass() +"{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
