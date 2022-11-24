package ai;

import utilities.Board;
import utilities.Space;
import entities.Opponent;
import entities.Player;

import java.util.*;

public class Orc implements AI {

    private final Player player;
    private final Opponent opponent;
    private final int searchRadius = 6;
    private int maxHealth = 5;
    private final int dmg = 2;
    private final int reach = 1;

    public Orc(Player player, Opponent opponent) {

        this.player = player;
        this.opponent = opponent;
    }

    public void calculateMovementDirection(Board board) {
//    TODO FIX PATHFINDING SO ENEMIES DON'T KILL EACH OTHER WHILE CHASING PLAYER THIS IMPLEMENTATION CURRENTLY THROWS AN EXCEPTION
//        COMPLETE REDO NEEDED
        int playerXPos = player.getxPos();
        int playerYPos = player.getyPos();
        int opponentXPos = opponent.getxPos();
        int opponentYPos = opponent.getyPos();
        int xSteps = playerXPos - opponentXPos;
        int ySteps = playerYPos - opponentYPos;
        HashMap<String, Boolean> directions = new HashMap<>();
        Space[][] spaces = board.getSpaces();
        Random random = new Random();

        if (checkIfValidField(opponentXPos + reach, opponentYPos, spaces)) {

            directions.put("+ x", true);
        }

        if (checkIfValidField(opponentXPos - reach, opponentYPos, spaces)) {

            directions.put("- x", true);
        }

        if (checkIfValidField(opponentXPos, opponentYPos + reach, spaces)) {

            directions.put("+ y", true);
        }

        if (checkIfValidField(opponentXPos, opponentYPos - reach, spaces)) {

            directions.put("- y", true);
        }

        HashMap<String, Boolean> removeDirections = new HashMap<>();

        for (String direction : directions.keySet()) {

            String[] directionSplit = direction.split(" ");
            int movement = Integer.parseInt(directionSplit[0] + 1) * reach;

            if (directionSplit[1].equals("x")) {

                if (spaces[opponentXPos + movement][opponentYPos].getEntityOnField() != null && !(spaces[opponentXPos + movement][opponentYPos].getEntityOnField() instanceof Player) ) {

                    removeDirections.put(direction, true);
                }
            } else if (directionSplit[1].equals("y")) {

                if (spaces[opponentXPos][opponentYPos + movement].getEntityOnField() != null && !(spaces[opponentXPos][opponentYPos + movement].getEntityOnField() instanceof Player)) {

                    removeDirections.put(direction, true);
                }
            }
        }

        for (String removeDirection : removeDirections.keySet()) {

            directions.remove(removeDirection);
        }

        removeDirections.clear();

        if (directions.size() == 0){

            return;
        }

        if (xSteps > searchRadius || xSteps < -1 * searchRadius  || ySteps > searchRadius || ySteps < -1 * searchRadius){

            String move = findRandomValidDirectionToMoveIn(directions, random);
            opponent.move(Integer.parseInt(move.split(" ")[0] + 1) * reach,move.charAt(2),board);
            return;
        }

        HashMap<String, Boolean> playerDirections = new HashMap<>();
        if (xSteps > 0){

            playerDirections.put("+ x", true);

        } else if (xSteps < 0) {

            playerDirections.put("- x", true);
        }

        if (ySteps > 0) {

            playerDirections.put("+ y", true);

        } else if (ySteps < 0) {

            playerDirections.put("- y",true);

        }

        for (String direction : directions.keySet()) {

            if (!playerDirections.containsKey(direction)){

                directions.replace(direction, false);
            }
        }


        if (directions.containsValue(true)){

            for (String direction : directions.keySet()) {

                if (!directions.get(direction)){

                    removeDirections.put(direction, true);
                }
            }
            for (String removeDirection : removeDirections.keySet()) {

                directions.remove(removeDirection);
            }

        }
        String move = findRandomValidDirectionToMoveIn(directions, random);
        opponent.move(Integer.parseInt(move.split(" ")[0] + 1) * reach,move.charAt(2),board);
    }

    public String findRandomValidDirectionToMoveIn(HashMap<String, Boolean> directions, Random random) {
        int counter = 0;
        int index = random.nextInt(directions.size());
        String move = "";

        for (String direction : directions.keySet()) {

            if (counter == index){

                move = direction;
                break;

            }else {

                counter++;
            }
        }
        return move;
    }




    public boolean checkIfValidField(int xpos, int ypos, Space[][] spaces) {

        try {

            Space space = spaces[xpos][ypos];

        } catch (Exception e) {

            return false;
        }

        return true;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDmg() {
        return dmg;
    }

    public int getReach() {
        return reach;
    }

}
