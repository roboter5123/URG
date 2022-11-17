public class Camera {

    private Space[][] viewPort;
    private String view;
    private Entity target;
    private int viewPortSize;

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

        String map = "";

        for (int i = 0; i < viewPort[0].length; i++) {

            for (int j = 0; j < viewPort.length; j++) {

                map += "-".repeat(4);

            }
            map += "\n";

            for (int j = 0; j < viewPort.length; j++) {

                Space curSpace = viewPort[j][i];
                map += "|";

                if (curSpace.getEntityOnField() != null) {

                    map += curSpace.getEntityOnField().getSprite();

                } else {

                    map += "   ";
                }
            }
            map += "|";
            map += "\n";
        }
        map += "====".repeat(viewPort[0].length);
        view = map;
        return map;

    }

    public void setViewPort(Space[][] viewPort) {
        this.viewPort = viewPort;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public int getViewPortSize() {
        return viewPortSize;
    }

    public void setViewPortSize(int viewPortSize) {
        this.viewPortSize = viewPortSize;
    }
}
