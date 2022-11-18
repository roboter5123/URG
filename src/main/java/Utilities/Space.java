package Utilities;

import entities.Entity;

public class Space {

    private Entity characteronField;

    public Space() {}

    public Entity getEntityOnField() {
        return characteronField;
    }

    public void setEntityOnField(Entity characteronField) {
        this.characteronField = characteronField;
    }

    @Override
    public String toString() {
        return "Utilities.Space{" +
                "characteronField=" + characteronField +
                '}';
    }
}
