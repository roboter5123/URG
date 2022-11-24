package ai;
import utilities.Board;

public interface AI {
    void calculateMovementDirection(Board board);
    int getMaxHealth();
    int getDmg();
    int getReach();
}
