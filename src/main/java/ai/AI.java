package ai;
import items.Item;
import utilities.Map;

import java.util.List;
import java.util.Random;

public interface AI {
    String calculateMovementDirection(Map map, Random random);
    int getMAX_HEALTH();
    int getDMG();
    int getReach();
    List<Item> getLootTable();
}
