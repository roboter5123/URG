package utilities;

import entities.Entity;
import entities.Wall;

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

    /**
     * @param spaces Used to update the viewport so that the viewport consists of an array of Spaces, which is viewPortSize in Size, that's centered around the target entity, usually the player.
     */
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
                    viewPort[y + (viewPortYMin * -1)][x + (viewPortXMin * -1)].setEntityOnField(new Wall());
                } else {

                    viewPort[y + (viewPortYMin * -1)][x + (viewPortXMin * -1)] = spaces[y][x];
                }
            }
        }
    }

    /**
     * @param spaces Used to update the viewport so that the viewport consists of an array of Spaces, which is viewPortSize in Size, that's centered around the target entity, usually the player.
     * @return The updated viewport.
     */
    public Space[][] getViewPort(Space[][] spaces){

        updateViewPort(spaces);

        return viewPort;
    }
}
