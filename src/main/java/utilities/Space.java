package utilities;

import entities.Entity;
import items.Item;

public class Space {

    private Entity characterOnField;
    private Item itemOnField;
    private int xPos;
    private int yPos;

    public Space() {}

    public Entity getEntityOnField() {
        return characterOnField;
    }

    public void setEntityOnField(Entity characteronField) {

        this.characterOnField = characteronField;
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
                "characteronField=" + characterOnField +
                '}';
    }
}
