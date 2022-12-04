package utilities;

import entities.Entity;
import items.Item;

public class Space {

    private Entity entityOnField;

    private Item itemOnField;

    private int xPos;

    private int yPos;

    public Space() {}

    public Entity getEntityOnField() {
        return entityOnField;
    }

    public void setEntityOnField(Entity entityOnField) {

        this.entityOnField = entityOnField;
    }

    public Item getItemOnField() {

        return itemOnField;
    }

    public void setItemOnField(Item itemOnField) {

        this.itemOnField = itemOnField;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxPos() {

        return xPos;
    }

    public int getyPos() {

        return yPos;
    }

    @Override
    public String toString() {
        return "Utilities.Space{" +
                "entityOnField=" + entityOnField +
                '}';
    }
}
