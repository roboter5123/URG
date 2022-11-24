package utilities;

import entities.Entity;
import entities.Opponent;
import entities.Player;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Space[][] spaces;

    private Player player;

    private List<Entity> entities = new ArrayList<>();

    private Camera camera;

    public Board(int size) {

        spaces = new Space[size][size];

        for (int i = 0; i < size; i++) {


            for (int j = 0; j < size; j++) {

                spaces[i][j] = new Space();
                spaces[i][j].setyPos(i);
                spaces[i][j].setxPos(j);
            }
        }

    }

    public Space[][] getSpaces() {

        return spaces;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        camera = new Camera(player,9,spaces);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Opponent> getOpponents() {

        List<Opponent> opponents = new ArrayList<>();

        for (int i = 0; i < this.getEntities().size(); i++) {

            if (this.entities.get(i) instanceof Opponent) {

                opponents.add((Opponent) this.entities.get(i));
            }
        }

        return opponents;
    }

    public void removeopponents(List<Opponent> opponentsToRemove) {

        for (Opponent opponent : opponentsToRemove) {

            this.entities.remove(opponent);

            for (Space[] row : spaces) {

                for (Space space : row) {

                    if (space.getEntityOnField() == opponent) {

                        space.setEntityOnField(null);
                        break;
                    }
                }
            }
        }
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {

        return camera.getMap(spaces);
    }
}
