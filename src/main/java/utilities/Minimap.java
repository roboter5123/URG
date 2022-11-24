package utilities;

import entities.Entity;
import entities.Player;
import entities.Wall;

import java.util.ArrayList;
import java.util.List;

public class Minimap {

    int ROOMSIZE = 7;
    int BOARDSIZE = 3*7;
    boolean[][] opponents;
    boolean[][] player;

    public Minimap(Board board) {
        Space[][] spaces = board.getSpaces();
        this.opponents = new boolean[BOARDSIZE/ROOMSIZE][BOARDSIZE/ROOMSIZE];
        this.player = new boolean[BOARDSIZE/ROOMSIZE][BOARDSIZE/ROOMSIZE];
        findMaps(spaces);
    }

    public void updateMinimap(Board board){

        Space[][] spaces = board.getSpaces();
        findMaps(spaces);
    }

    public void findMaps(Space[][] spaces) {

        List<Entity> opponents = new ArrayList<>();
        Player player = null;

        for (Space[] row : spaces) {

            for (Space space : row) {

                if (space.getEntityOnField() == null){

                    continue;
                }

                if (space.getEntityOnField() instanceof Player){

                    player = (Player) space.getEntityOnField();
                }

                if (space.getEntityOnField() instanceof Wall){

                    continue;
                }
                opponents.add(space.getEntityOnField());
            }
        }

        for (int i = 0; i < this.opponents.length; i++) {
            for (int j = 0; j < this.opponents[i].length; j++) {

                this.opponents[i][j] = false;
                this.player[i][j] = false;
            }
        }

        for (Entity opponent : opponents) {

            int x = opponent.getxPos() / ROOMSIZE;
            int y = opponent.getyPos() / ROOMSIZE;

            this.opponents[y][x] = true;
        }

        if (player != null){

            int x = player.getxPos() / ROOMSIZE;
            int y = player.getyPos()/ ROOMSIZE;
            this.player[y][x] = true;
        }

    }

    @Override
    public String toString() {

        StringBuilder minimap = new StringBuilder();

        minimap.append("=".repeat(2* (BOARDSIZE/ROOMSIZE)+1));
        minimap.append("\n");

        for (int i = 0; i < opponents.length; i++) {

            for (int j = 0; j < opponents[i].length; j++) {

                minimap.append("|");
                if (opponents[i][j]){

                    minimap.append("O");
                }else {

                    minimap.append(" ");
                }
                if (player[i][j]){

                    minimap.replace(minimap.length()-1,minimap.length(),"P");
                }
            }
            minimap.append("|\n");

        }

        minimap.append("=".repeat(2 * (BOARDSIZE/ROOMSIZE)+1));

        return minimap.toString();
    }
}
