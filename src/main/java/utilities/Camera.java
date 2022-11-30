package utilities;

import entities.Entity;

public class Camera {

    private final Space[][] viewPort;
    private final Entity target;
    private final int viewPortSize;

    public Camera(Entity target, Space[][] spaces) {

        this.target = target;
        this.viewPortSize = 7;
        this.viewPort = new Space[viewPortSize][viewPortSize];
        updateViewPort(spaces);
    }

    public void updateViewPort(Space[][] spaces) {

        int targetXPos = target.getxPos();
        int targetYPos = target.getyPos();
        int viewPortXMax;
        int viewPortYMax;
        if (viewPortSize % 2 == 0) {

            viewPortXMax = targetXPos + viewPortSize / 2;
            viewPortYMax = targetYPos + viewPortSize / 2;
        } else {

            viewPortXMax = targetXPos + viewPortSize / 2 + 1;
            viewPortYMax = targetYPos + viewPortSize / 2 + 1;
        }

        int viewPortYMin = targetYPos - viewPortSize / 2;
        int viewPortXMin = targetXPos - viewPortSize / 2;

        for (int y = viewPortYMin; y < viewPortYMax; y++) {

            for (int x = viewPortXMin; x < viewPortXMax; x++) {

                if (y < 0 || x < 0 || y >= spaces.length || x >= spaces[y].length) {

                    viewPort[y + (viewPortYMin * -1)][x + (viewPortXMin * -1)] = new Space();
                } else {

                    viewPort[y + (viewPortYMin * -1)][x + (viewPortXMin * -1)] = spaces[y][x];
                }
            }
        }
    }

    public String getMapString(Space[][] spaces) {

        updateViewPort(spaces);

        StringBuilder map = new StringBuilder();

        for (int y = 0; y < viewPort[0].length; y++) {

            map.append("-".repeat(4).repeat(viewPort.length));
            map.append("\n");

            for (int x = 0; x < viewPort[y].length; x++) {

                Space curSpace = viewPort[y][x];
                map.append("|");

                if (curSpace.getEntityOnField() != null) {

                    map.append(curSpace.getEntityOnField().getSprite());
                } else {

                    map.append("   ");
                }
            }
            map.append("|");
            map.append("\n");
        }
        map.append("====".repeat(viewPort[0].length));
        return map.toString();
    }

    public Space[][] getMap(Space[][] spaces){

        updateViewPort(spaces);

        return viewPort;
    }
}
