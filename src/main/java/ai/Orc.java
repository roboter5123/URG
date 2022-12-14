package ai;

import items.HealItem;
import items.Item;
import utilities.Map;
import utilities.Space;
import entities.Opponent;
import entities.Player;

import java.util.*;

public class Orc implements AI {

    private final Player player;
    private final Opponent opponent;
    private final int reach = 1;
    private final int SIGHT_RADIUS = 4;
    private final int MAX_HEALTH = 5;
    private final int DMG = 1;
    private List<Item> lootTable;

    public Orc(Player player, Opponent opponent) {

        this.player = player;
        this.opponent = opponent;
        this.setLootTable();
    }

    /**
     * Uses A* to find the best direction to move in.
     * @param map    The map on which the opponent should move.
     * @param random used to randomify movement.
     * @return A string representing the axis to move on and the direction on that axis.Seperated by a single space. Example: + x
     */
    public String calculateMovementDirection(Map map, Random random) {

        int xSteps = opponent.getxPos() - player.getxPos();
        int ySteps = opponent.getyPos() - player.getyPos();

        if (xSteps <= SIGHT_RADIUS && xSteps >= (SIGHT_RADIUS * -1) && ySteps <= SIGHT_RADIUS && ySteps >= (SIGHT_RADIUS * -1)) {

            Space[][] spaces = map.getSpaces();
            NavigationNode[][] nodes = generateNodes(spaces);
            connectNodes(nodes);
            NavigationNode startNode = getStartNode(nodes);
            NavigationNode endNode = getEndNode(nodes);
            return solveAStar(startNode, endNode, map,random);
        } else {

            return randomDirection(map, random);
        }
    }

    /**
     * Chooses a random direction to walk in. Used when the player is out of sight.
     * @param map The map on which the opponent should move.
     * @param random Used to randomify movement.
     * @return A string representing the axis to move on and the direction on that axis. Seperated by a single space. Example: "+ x"
     */
    private String randomDirection(Map map, Random random) {

        List<String> directions = new ArrayList<>();
        Space[][] spaces = map.getSpaces();
        int xPos = opponent.getxPos();
        int yPos = opponent.getyPos();

        if (xPos == spaces[yPos].length - 1) {

            if (spaces[yPos][xPos - reach].getEntityOnField() == null) {

                directions.add("- x");
            }
        } else if (xPos == 0) {

            if (spaces[yPos][xPos + reach].getEntityOnField() == null) {

                directions.add("+ x");
            }
        } else {

            if (spaces[yPos][xPos + reach].getEntityOnField() == null) {

                directions.add("+ x");
            }

            if (spaces[yPos][xPos - reach].getEntityOnField() == null) {

                directions.add("- x");
            }
        }

        if (yPos == spaces.length - 1) {

            if (spaces[yPos - reach][xPos].getEntityOnField() == null) {

                directions.add("- y");
            }
        } else if (yPos == 0) {

            if (spaces[yPos + reach][xPos].getEntityOnField() == null) {

                directions.add("+ y");
            }
        } else {

            if (spaces[yPos + reach][xPos].getEntityOnField() == null) {

                directions.add("+ y");
            }

            if (spaces[yPos - reach][xPos].getEntityOnField() == null) {

                directions.add("- y");
            }
        }

        if (directions.size() == 0) {

            return null;
        }

        int randomIndex = random.nextInt(directions.size());
        return directions.get(randomIndex);
    }

    /**
     * solves A* for this opponent.
     * @param startNode The node on which the Opponent is located currently.
     * @param endNode The node the opponents target is located on. usually the player.
     * @param map The map on which the opponent should move.
     * TODO: I don't know why anymore this is used here anymore. Should try and remember that soon.
     * @param random Used to randomify movement.
     * @return A string representing the axis to move on and the direction on that axis. Seperated by a single space. Example: "+ x"
     */
    private String solveAStar(NavigationNode startNode, NavigationNode endNode, Map map, Random random) {

        NavigationNode curNode = startNode;
        curNode.localGoal = 0;
        curNode.globalGoal = calculateDistance(curNode, endNode);

        List<NavigationNode> nodesToBeTested = new ArrayList<>();
        nodesToBeTested.add(curNode);

        while (!nodesToBeTested.isEmpty()) {

            Collections.sort(nodesToBeTested);

            if (nodesToBeTested.get(0).wasVisited) {

                nodesToBeTested.remove(0);
            }

            if (nodesToBeTested.isEmpty()) {

                break;
            }

            curNode = nodesToBeTested.get(0);
            curNode.wasVisited = true;

            for (NavigationNode neighbor : curNode.neighbors) {

                if (!neighbor.isObstacle && !neighbor.wasVisited && !nodesToBeTested.contains(neighbor)) {

                    nodesToBeTested.add(neighbor);
                }

                double distanceToNeighbor = calculateDistance(curNode, neighbor);
                if (distanceToNeighbor < 0) {

                    distanceToNeighbor *= -1;
                }

                if (curNode.localGoal + distanceToNeighbor < neighbor.localGoal) {

                    neighbor.parent = curNode;
                    neighbor.localGoal = curNode.localGoal + distanceToNeighbor;
                    neighbor.globalGoal = neighbor.localGoal + calculateDistance(neighbor, endNode);
                }
            }
        }
        curNode = endNode;

        while (true) {

            if (curNode.parent == null) {

                return randomDirection(map,random);

            } else if (curNode.parent.equals(startNode)) {

                int moveX = startNode.x - curNode.x;
                int moveY = startNode.y - curNode.y;

                if (moveX < 0) {

                    return "+ x";
                } else if (moveX > 0) {

                    return "- x";
                } else if (moveY < 0) {

                    return "+ y";
                } else if (moveY > 0) {

                    return "- y";
                }
            }

            curNode = curNode.parent;
        }
    }

    /**
     * Used to calculate the airline between two codes.
     * @param node1 The first node.
     * @param node2 The second node.
     * @return The distance between the two nodes.
     */
    private double calculateDistance(NavigationNode node1, NavigationNode node2) {

        int a = node1.x - node2.x;
        int b = node1.y - node2.y;
        return Math.sqrt((a * a) + (b * b));
    }

    /**
     * @param nodes  A list of all nodes.
     * @return The node on which the opponent is located.
     */
    private NavigationNode getStartNode(NavigationNode[][] nodes) {

        for (NavigationNode[] row : nodes) {

            for (NavigationNode node : row) {

                if (node.isStart) {

                    return node;
                }
            }
        }
        return null;
    }

    /**
     * @param nodes  A list of all nodes.
     * @return The node on which the opponents target is located. usually the player.
     */
    private NavigationNode getEndNode(NavigationNode[][] nodes) {

        for (NavigationNode[] row : nodes) {

            for (NavigationNode node : row) {

                if (node.isGoal) {

                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Sets all the neighbors of the nodes correctly.
     * @param nodes A list of all nodes.
     */
    private void connectNodes(NavigationNode[][] nodes) {

        for (int y = 0; y < nodes.length; y++) {

            for (int x = 0; x < nodes[y].length; x++) {

                NavigationNode node = nodes[y][x];

                if (y == 0) {

                    node.neighbors.add(nodes[y + 1][x]);
                } else if (y == (nodes.length - 1)) {

                    node.neighbors.add(nodes[y - 1][x]);
                } else {

                    node.neighbors.add(nodes[y - 1][x]);
                    node.neighbors.add(nodes[y + 1][x]);
                }

                if (x == 0) {

                    node.neighbors.add(nodes[y][x + 1]);
                } else if (x == (nodes[y].length - 1)) {

                    node.neighbors.add(nodes[y][x - 1]);
                } else {

                    node.neighbors.add(nodes[y][x - 1]);
                    node.neighbors.add(nodes[y][x + 1]);
                }
            }
        }
    }

    /**
     * Generates the nodes for the whole map.
     * @param spaces The Spaces for which to generate the nodes.
     * @return An array of navigation nodes.
     */
    private NavigationNode[][] generateNodes(Space[][] spaces) {

        NavigationNode[][] nodes = new NavigationNode[spaces.length][spaces[0].length];

        for (int y = 0; y < spaces.length; y++) {

            for (int x = 0; x < spaces[y].length; x++) {

                if (y == opponent.getyPos() && x == opponent.getxPos()) {

                    nodes[y][x] = new NavigationNode(y, x, true, false);
                } else if (y == player.getyPos() && x == player.getxPos()) {

                    nodes[y][x] = new NavigationNode(y, x, false, true);
                } else if (spaces[y][x].getEntityOnField() != null) {

                    nodes[y][x] = new NavigationNode(y, x, true);
                } else {

                    nodes[y][x] = new NavigationNode(y, x);
                }
            }
        }
        return nodes;
    }

    public int getMAX_HEALTH() {


        return MAX_HEALTH;
    }

    public int getDMG() {

        return DMG;
    }

    public int getReach() {

        return reach;
    }

    @Override
    public List<Item> getLootTable() {

        return this.lootTable;
    }

    /**
     * Sets the loottable for this type of opponent.
     */
    private void setLootTable() {
        this.lootTable = new ArrayList<>();
        this.lootTable.add(new HealItem(1));
        this.lootTable.add(new HealItem(2));
        this.lootTable.add(null);

    }
}
