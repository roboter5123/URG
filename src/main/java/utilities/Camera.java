package utilities;

import entities.Entity;

public class Camera {

    private Space[][] viewPort;
    private final Entity target;
    private final int viewPortSize;

    public Camera(Entity target, int viewPortSize, Space[][] spaces) {
        this.target = target;
        this.viewPortSize = viewPortSize;
        this.viewPort = new Space[viewPortSize][viewPortSize];
        updateViewPort(spaces);
    }

    public void updateViewPort(Space[][] spaces) {

        int targetYPos = target.getxPos();
        int targetXPos = target.getyPos();
        int viewPortXMax;
        int viewPortYMax;
        if (viewPortSize % 2 == 0){

            viewPortXMax = targetXPos + viewPortSize / 2;
            viewPortYMax = targetYPos + viewPortSize / 2;
        }else {

            viewPortXMax = targetXPos + viewPortSize / 2 + 1;
            viewPortYMax = targetYPos + viewPortSize / 2 + 1;

        }
        int viewPortYMin = targetYPos - viewPortSize / 2;

        int viewPortXMin = targetXPos - viewPortSize / 2;


        for (int i = viewPortYMin; i < viewPortYMax; i++) {

            for (int j = viewPortXMin; j < viewPortXMax; j++) {

                if (i < 0 || j < 0 || i >= spaces.length || j >= spaces[i].length) {

                    viewPort[i + (viewPortYMin * -1)][j + (viewPortXMin * -1)] = new Space();

                } else {

                    viewPort[i + (viewPortYMin * -1)][j + (viewPortXMin * -1)] = spaces[i][j];
                }
            }
        }
    }

    public String getMap(Space[][] spaces) {

        updateViewPort(spaces);

        StringBuilder map = new StringBuilder();

        for (int i = 0; i < viewPort[0].length; i++) {

            map.append("-".repeat(4).repeat(viewPort.length));
            map.append("\n");

            for (Space[] value : viewPort) {

                Space curSpace = value[i];
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
}
