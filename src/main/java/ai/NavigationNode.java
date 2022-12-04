package ai;

import java.util.ArrayList;
import java.util.List;

public class NavigationNode implements Comparable {

    boolean isObstacle;
    boolean wasVisited;
    double globalGoal;
    double localGoal;
    int y;
    int x;
    List<NavigationNode> neighbors;
    NavigationNode parent;
    boolean isGoal;
    boolean isStart;

    public NavigationNode(int y, int x) {

        this.y = y;
        this.x = x;
        this.isObstacle = false;
        this.wasVisited = false;
        this.parent = null;
        this.neighbors = new ArrayList<>();
        this.isStart = false;
        this.isGoal = false;
        this.globalGoal = Double.POSITIVE_INFINITY;
        this.localGoal = Double.POSITIVE_INFINITY;
    }

    public NavigationNode(int y, int x, boolean isObstacle) {

        this.y = y;
        this.x = x;
        this.isObstacle = isObstacle;
        this.wasVisited = false;
        this.parent = null;
        this.neighbors = new ArrayList<>();
        this.isStart = false;
        this.isGoal = false;
        this.globalGoal = Double.POSITIVE_INFINITY;
        this.localGoal = Double.POSITIVE_INFINITY;
    }

    public NavigationNode(int y, int x, boolean isStart, boolean isGoal) {

        this.y = y;
        this.x = x;
        this.isObstacle = false;
        this.wasVisited = false;
        this.parent = null;
        this.neighbors = new ArrayList<>();
        this.isStart = isStart;
        this.isGoal = isGoal;
        this.globalGoal = Double.POSITIVE_INFINITY;
        this.localGoal = Double.POSITIVE_INFINITY;
    }

    @Override
    public int compareTo(Object o) {

        NavigationNode node = (NavigationNode) o;

        return Double.compare(this.globalGoal, node.globalGoal);
    }

    public boolean equals(Object o){

        if (o == null){

            return false;
        }

        NavigationNode node = (NavigationNode) o;

        return this.x == node.x && this.y == node.y;
    }

    @Override
    public String toString() {

        return "NavigationNode{" +
                "isObstacle=" + isObstacle +
                ", y=" + y+
                ", x=" + x +
                " startNode=" + isStart+
                '}';
    }
}
