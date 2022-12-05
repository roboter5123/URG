package utilities;

import entities.OpponentType;
import entities.Opponent;
import entities.Player;
import items.Item;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private Player player;
    private List<Opponent> opponentList;
    private Map map;
    private Random random;
    final int ROOM_SIZE = 7;
    private Camera camera;
    private Minimap minimap;
    private int roundCounter = 0;
    private boolean gameOver = false;
    private boolean isPlayerAlive = true;

    /**
     * Used to make the first level of a run.
     *
     * @param mapSize mapSize o the first level in a run.
     */
    public Level(int mapSize) {

        int playerMaxHealth = mapSize * mapSize / 3 * 2;
        int opponentCount = mapSize * mapSize / 3 * 2;
        init(mapSize, playerMaxHealth, opponentCount);
    }

    /**
     *Used to every level after the first one.
     *
     * @param player The player from the previous level.
     * @param random The random from the previous level.
     * @param mapSize The mapSize from the previous level + map growth.
     */
    public Level(Player player, Random random, int mapSize) {

        this.player = player;
        this.random = random;
        int opponentCount = mapSize * mapSize / 3 * 2;
        init(mapSize,opponentCount);
    }

    /**
     * Initializes the game by setting all fields to their needed values. Used for subsequent levels.
     *
     * @param mapSize         Sets the length of the space array so that the final size = (mapSize * ROOM_SIZE)²
     * @param opponentCount   The amount of opponents on the map.
     */
    public void init(int mapSize, int opponentCount) {

        map = new Map(mapSize, ROOM_SIZE, random);
        map.placeEntity(player);
        camera = new Camera(this.player, map.getSpaces());
        minimap = new Minimap(map);
        generateOpponents(opponentCount);
    }

    /**
     * Initializes the game by setting all fields to their needed values. used for the first level only.
     *
     * @param mapSize         Sets the length of the space array so that the final size = (mapSize * ROOM_SIZE)²
     * @param playerMaxHealth Sets the maxHealth of the player entity.
     * @param opponentCount   The amount of opponents on the map.
     */
    public void init(int mapSize, int playerMaxHealth, int opponentCount) {

        random = new Random(1);
        map = new Map(mapSize, ROOM_SIZE, random);
        player = new Player(playerMaxHealth);
        map.placeEntity(player);
        camera = new Camera(this.player, map.getSpaces());
        minimap = new Minimap(map);
        generateOpponents(opponentCount);
    }

    /**
     * generates opponentCount opponnents and puts them into opponentsList
     *
     * @param opponentCount used to determine the amount of opponents to generate.
     */
    private void generateOpponents(int opponentCount) {

        opponentList = new ArrayList<>();

        for (int i = 0; i < opponentCount; i++) {

            int typeNumber = random.nextInt(OpponentType.values().length);
            OpponentType type = OpponentType.values()[typeNumber];
            Opponent opponent = new Opponent(player, type);

            map.placeEntity(opponent);
            opponentList.add(opponent);
        }
    }


    /**
     * Main method of the level object. Moves the player in the desired direction and then the opponents if it's their turn.
     *
     * @param code The keycode of the pressed key. used to decide the players action.
     * @return A statuscode about if the game is over and if the player is dead or alive.
     */
    public StatusCode play(KeyCode code) {

        player.move(getMap(), code.toString());
        isGameOver();
        addPlayerRound();

        if (getRoundCounter() % 2 == 0) {

            moveOpponents();
        }


        isGameOver();

        if (gameOver && isPlayerAlive) {

            return StatusCode.PLAYER_WON;

        } else if (gameOver) {

            return StatusCode.PLAYER_LOST;

        } else {

            return StatusCode.GAME_ON;
        }


    }

    /**
     * Sets Game over to true if either all opponents are dead or the player is dead.
     * Also sets isPlayerAlive to the current alive status of the player.
     */
    public void isGameOver() {

        if (player.getHealth() <= 0) {

            gameOver = true;
            isPlayerAlive = false;

        } else if (checkOpponentHealth()) {

            gameOver = true;
            isPlayerAlive = true;
        }
    }

    /**
     * Moves all opponents as many spaces as their reach allows.
     */
    public void moveOpponents() {

        for (Opponent opponent : opponentList) {

            for (int i = 0; i < opponent.getReach(); i++) {

                opponent.move(map, random);
            }
        }
    }

    /**
     * looks through the opponent list and if any opponents are dead removes them from the list.
     *
     * @return If there are any opponents still alive.
     */
    public boolean checkOpponentHealth() {

        for (int i = 0; i < opponentList.size(); i++) {
            Opponent opponent = opponentList.get(i);

            if (opponent.getHealth() == 0) {

                int xPos = opponent.getxPos();
                int yPos = opponent.getyPos();
                Space[][] spaces = map.getSpaces();
                Space curSpace = spaces[yPos][xPos];

                curSpace.setEntityOnField(null);
                Item drop = opponent.dropItem();
                curSpace.setItemOnField(drop);
                opponentList.remove(opponent);
            }
        }

        return opponentList.isEmpty();
    }

    /**
     * Adds a round to the round counter.
     */
    public void addPlayerRound() {

        this.roundCounter += 1;
    }

    /**
     * generates the next level of the run.
     *
     * @return The level to be played next.
     */
    public Level nextLevel(){

        return new Level(player,random,(map.getSpaces().length / ROOM_SIZE) + 1);
    }

    public Player getPlayer() {

        return player;
    }

    public Map getMap() {

        return map;
    }

    public void setMap(Map map) {

        this.map = map;
    }

    public Camera getCamera() {

        return camera;
    }

    public int getRoundCounter() {

        return roundCounter;
    }


}
