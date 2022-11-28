package utilities;

import entities.OponentType;
import entities.Opponent;
import entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {

    Player player;
    List<Opponent> opponentList;
    Map map;
    Random random;
    final int ROOMSIZE = 7;
    int mapSize;
    private Camera camera;
    private Minimap minimap;

    public Level(int mapSize, int playerMaxHealth, int opponentCount) {
        init(mapSize, playerMaxHealth,opponentCount);
    }

    public void init(int mapSize, int playerMaxHealth, int opponentCount) {

        random = new Random(1);
        this.mapSize = mapSize;
        map = new Map(mapSize, ROOMSIZE, random);
        player = new Player(playerMaxHealth);
        map.placeEntity(player);
        camera = new Camera(this.player, map.getSpaces());
        minimap = new Minimap(map);
        generateOpponents(opponentCount);
    }

    private void generateOpponents(int opponentCount) {

        opponentList = new ArrayList<>();

        for (int i = 0; i < opponentCount; i++) {

            int typeNumber = random.nextInt(OponentType.values().length);
            OponentType type = OponentType.values()[typeNumber];
            Opponent opponent = new Opponent(player, type);

            map.placeEntity(opponent);
            opponentList.add(opponent);
        }
    }

    public boolean gameLoop() {

        boolean gameOver = false;
        boolean isPlayerAlive = true;

        while (!gameOver && isPlayerAlive) {

            for (int i = 0; i < player.getReach(); i++) {

                minimap.updateMinimap(map);
                System.out.println(minimap.toString());
                System.out.println(camera.getMap(map.getSpaces()));
                System.out.println(player.getHealth());
                player.move(map);
                checkOpponentHealth();
            }

            for (Opponent opponent : opponentList) {

                for (int i = 0; i < opponent.getReach(); i++) {

                    opponent.move(map, random);
                }
            }

            if (player.getHealth() <= 0) {

                gameOver = true;
                isPlayerAlive = false;
            }

        }

        return isPlayerAlive;
    }

    private void checkOpponentHealth() {

        for (int i = 0; i < opponentList.size() ; i++) {
            Opponent opponent = opponentList.get(i);

            if (opponent.getHealth() == 0) {

                int xpos = opponent.getxPos();
                int ypos = opponent.getyPos();
                Space[][] spaces = map.getSpaces();

                spaces[ypos][xpos].setEntityOnField(null);
                opponentList.remove(opponent);
            }
        }
    }
}
