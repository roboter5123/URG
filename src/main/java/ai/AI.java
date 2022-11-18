package ai;
import Utilities.Board;

public interface AI {
    public void calculateMovementDirection(Board board);
    public int getSearchRadius();
    public void setSearchRadius(int searchRadius);
    public int getMaxHealth();
    public void setMaxHealth(int maxHealth);
    public int getDmg();
    public void setDmg(int dmg);
    public int getReach();
}
