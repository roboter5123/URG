package ai;
import utilities.Map;

import java.util.Random;

public interface AI {
    String calculateMovementDirection(Map map, Random random);
    int getMaxHealth();
    int getDmg();
    int getReach();
}
