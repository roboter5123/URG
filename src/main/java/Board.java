import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private Space[][] spaces;

    private Player player;

    private List<Entity> entities = new ArrayList<>();

    public Board(int size) {

        spaces = new Space[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {


            for (int j = 0; j < size; j++) {

                    spaces[i][j] = new Space();
            }
        }
    }

    public Space[][] getSpaces() {

        return spaces;
    }

    public void setSpaces(Space[][] spaces) {

        this.spaces = spaces;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Opponent> getOpponents(){

        List<Opponent> opponents = new ArrayList<>();

        for (int i = 0; i < this.getEntities().size(); i++) {

            if (this.entities.get(i) instanceof Opponent){

                opponents.add((Opponent) this.entities.get(i));
            }
        }

        return opponents;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {

        String map = "";

        for (int i = 0; i < spaces[0].length; i++) {

            for (int j = 0; j < spaces.length; j++) {

                map += "=".repeat(6);

            }
            map += "\n";

            for (int j = 0; j < spaces.length; j++) {

                Space curSpace = spaces[j][i];
                map += "|";

                if (curSpace.getEntityOnField() instanceof Entity) {

                    map += curSpace.getEntityOnField().getSprite();

                } else {

                    map += "     ";
                }
            }
            map += "|";
            map += "\n";
        }
        map += "======".repeat(spaces[0].length);

        return map;
    }
}
