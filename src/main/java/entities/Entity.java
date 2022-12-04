package entities;

import javafx.scene.image.Image;
import utilities.Map;
import utilities.Space;

public class Entity {

    private int xPos;
    private int yPos;
    private int maxHealth;
    private int health;
    private int dmg;
    int reach;
    private String sprite;
    private Image image;

    public Entity() {

    }

    public void move(int moves, char direction, Map map) {

        Space[][] spaces = map.getSpaces();
        int newYPos = this.yPos;
        int newXPos = this.xPos;
        Space curSpace = spaces[yPos][xPos];
        Space newSpace = curSpace;
        Entity entity;
        Interactable item;

        if (direction == 'x' && xPos + moves != spaces.length && xPos + moves >= 0) {

            newXPos = xPos + moves;
            newSpace = spaces[newYPos][newXPos];
        } else if (direction == 'y' && yPos + moves != spaces.length && yPos + moves >= 0) {

            newYPos = yPos + moves;
            newSpace = spaces[newYPos][newXPos];
        }

        entity = newSpace.getEntityOnField();
        item = newSpace.getItemOnField();

        if (entity != null) {

            if (entity instanceof Interactable) {

                ((Interactable) entity).interact(this, map);
            }
            return;
        }

        if (item != null) {

            item.interact(this, map);
        }

        this.xPos = newXPos;
        this.yPos = newYPos;
        curSpace.setEntityOnField(null);
        newSpace.setEntityOnField(this);
    }

    /**
     * Makes this entity loose health equal to dmg.
     * @param dmg The amount of health the entity should lose.
     */
    public void loseHealth(int dmg) {

        health -= dmg;
    }

    /**
     * Increases This entities health by heal up to the amount maxHealth is set.
     * @param heal The amount of health the entity should gain.
     * @return Is true when the item was used. False when it was not.
     */
    public boolean heal(int heal) {

        if (this.health == this.maxHealth) {

            return false;
        }

        if (this.health + heal > this.maxHealth) {

            this.health = this.maxHealth;
        } else {

            this.health += heal;
        }

        return true;
    }

    public int getDmg() {

        return dmg;
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

    public Image getImage() {

        return image;
    }

    public void setImage(Image image) {

        this.image = image;
    }

    @Override
    public String toString() {

        return this.getClass() + "{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
