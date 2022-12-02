package entities;

import javafx.scene.image.Image;
import utilities.Map;
import utilities.Space;

public class Entity {

    private int xPos;
    private int yPos;
    private int maxHealth = 10;
    private int health;
    private int dmg;
    int reach;
    private String sprite;

    private Image image;

    public Entity() {}

    public void move(int moves, char direction, Map map) {

        Space[][] spaces = map.getSpaces();
        Space curSpace = spaces[yPos][xPos];

        if (direction == 'x' && xPos + moves != spaces.length && xPos + moves >= 0) {

            if (spaces[yPos ][xPos+ moves].getEntityOnField() == null) {

                curSpace.setEntityOnField(null);
                xPos += moves;
            } else if (spaces[xPos + moves][yPos].getEntityOnField() != this) {

                attack(spaces[yPos][xPos + moves].getEntityOnField());
            }
        } else if (direction == 'y' && yPos + moves != spaces.length && yPos + moves >= 0) {

            if (spaces[yPos + moves][xPos].getEntityOnField() == null) {
                curSpace.setEntityOnField(null);
                yPos += moves;
            } else if (spaces[yPos + moves][xPos].getEntityOnField() != this) {

                attack(spaces[yPos + moves][xPos].getEntityOnField());
            }
        }
        spaces[yPos][xPos].setEntityOnField(this);
    }

    public void attack(Entity entity) {

        entity.looseHealth(dmg);
    }

    public void looseHealth(int dmg) {

        health -= dmg;
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

        return this.getClass() + "{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }

    public Image getImage() {

        return image;
    }

    public void setImage(Image image) {

        this.image = image;
    }
}
