package entities;

import utilities.Map;

public interface Interactable {

    /**
     * @param entity The entity that interacts with this object.
     * @param map The map on which the interaction happens.
     */
    void interact(Entity entity, Map map);

}
