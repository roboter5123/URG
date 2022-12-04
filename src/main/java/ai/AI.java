package ai;
import items.Item;
import utilities.Map;

import java.util.List;
import java.util.Random;

public interface AI {

    /**
     * Determins in which direction the parent opponent should move
     * @param map The map on which the opponent should move.
     * @param random used to randomify movement.
     * @return A string representing the axis to move on and the direction on that axis.Seperated by a single space. Example: + x
     */
    String calculateMovementDirection(Map map, Random random);
    int getMAX_HEALTH();
    int getDMG();
    int getReach();
    List<Item> getLootTable();
}
