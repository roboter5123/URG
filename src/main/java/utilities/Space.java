package utilities;

import entities.Entity;

public class Space {

    private Entity characteronField;
    private int xPos;
    private int yPos;

    public Space() {}

    public Entity getEntityOnField() {
        return characteronField;
    }

    public void setEntityOnField(Entity characteronField) {

        this.characteronField = characteronField;

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
                "characteronField=" + characteronField +
                '}';
    }
}
