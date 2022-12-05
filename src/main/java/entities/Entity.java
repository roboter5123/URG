package entities;

import gui.Sprite;
import javafx.scene.canvas.GraphicsContext;
import utilities.Map;
import utilities.Space;

public class Entity implements Drawable, Interactable {

    private int xPos;
    private int yPos;
    private int maxHealth;
    private int health;
    private int dmg;
    int reach;
    private Sprite sprite;

    public Entity() {

    }

    /**
     * Moves this entity in the direction chosen.
     * @param moves The amount of spaces to move in the direction. Positive integers only.
     * @param direction The direction to move the entity in. Made up of the axis and the direction on that axis seperated by a space. Example: "+ X"
     * @param map The map on which to move this entity
     */
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

    /**
     * Draws the drawable to the given graphics context.
     *
     * @param yPos The y position for the top left pixel of the sprite.
     * @param xPos The x position for the top left pixel of the sprite.
     * @param gc   The graphics context of the canvas this sprite should be drawn on.
     */
    @Override
    public void draw(double yPos, double xPos, GraphicsContext gc) {
        this.sprite.draw(yPos,xPos,gc);
    }

    /**
     * Sets the pixel dimensions the sprite will take once it's drawn on the canvas.
     *
     * @param height The height in pixels that the sprite will take up one the canvas once drawn on the canvas.
     * @param width  The width in pixels that the sprite will take up one the canvas once drawn on the canvas.
     */
    @Override
    public void setDimensions(double height, double width) {
        this.sprite.setDimensions(height * 1.1,width * 1.1);
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

    public void setDmg(int dmg) {

        this.dmg = dmg;
    }

    public Sprite getSprite() {

        return sprite;
    }

    public void setSprite(Sprite sprite) {

        this.sprite = sprite;
    }

    @Override
    public String toString() {

        return this.getClass() + "{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }

    @Override
    public void interact(Entity entity, Map map) {

    }
}
