package ai;

import utilities.Map;
import utilities.Space;
import entities.Opponent;
import entities.Player;

import java.util.*;

public class Orc implements AI {

    private final Player player;
    private final Opponent opponent;
    private final int reach = 1;
    private final int sightRadius = 4;

    public Orc(Player player, Opponent opponent) {

        this.player = player;
        this.opponent = opponent;
    }

    public String calculateMovementDirection(Map map, Random random) {

        int xSteps = opponent.getxPos() - player.getxPos();
        int ySteps = opponent.getyPos() - player.getyPos();

        if (xSteps <= sightRadius && xSteps >= (sightRadius * -1) && ySteps <= sightRadius && ySteps >= (sightRadius * -1)) {

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

    private double calculateDistance(NavigationNode node1, NavigationNode node2) {

        int a = node1.x - node2.x;
        int b = node1.y - node2.y;
        return Math.sqrt((a * a) + (b * b));
    }

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

    public int getMaxHealth() {

        int maxHealth = 5;
        return maxHealth;
    }

    public int getDmg() {

        int dmg = 2;
        return dmg;
    }

    public int getReach() {

        return reach;
    }
}
