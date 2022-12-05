package gui;

import entities.Entity;
import entities.Wall;
import items.Item;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import utilities.Camera;
import utilities.Level;
import utilities.Space;
import utilities.StatusCode;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public AnchorPane root;
    public Canvas backgroundLayer;
    public Canvas gameLayer;
    public Canvas UILayer;
    Level level;
    int mapSize;
    Sprite fullHeart = new Sprite(new Image("uielements/heart2.png"));
    Sprite halfHeart = new Sprite(new Image("uielements/heart1.png"));

    /**
     * Only called during creation of this controller. Should not be used outside of creation.
     * @param url Not used.
     * @param resourceBundle Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (this.level != null) {
                drawScreen();
            }
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (this.level != null) {
                drawScreen();
            }
        });
    }

    /**
     * Called when the game starts. Creates a level object with the appropriate values.
     * @param text The text of the button that was pressed to start the game. used to set the mapSize, playerMaxHealth and opponentCount.
     */
    public void startGame(String text) {

        if ("Small".equals(text)) {

            mapSize = 2;
        } else if ("Medium".equals(text)) {

            mapSize = 3;
        } else if ("Large".equals(text)) {

            mapSize = 5;
        } else {

            mapSize = 3;
        }

        level = new Level(mapSize);
        drawScreen();
    }

    /**
     * Scales the 3 canvases (backgroundLayer, gameLayer and UILayer) to the whole window.
     */
    public void scaleCanvases(Space[][] view) {

        double windowHeight = root.getHeight();
        double windowWidth = root.getWidth();
        backgroundLayer.setHeight(windowHeight);
        backgroundLayer.setWidth(windowWidth);
        gameLayer.setWidth(windowWidth);
        gameLayer.setHeight(windowHeight);
        UILayer.setHeight(windowHeight);
        UILayer.setWidth(windowWidth);
        double layerWidth = gameLayer.getWidth();
        double layerHeight = gameLayer.getHeight();
        double cellWidth = layerWidth / view.length;
        double cellHeight = layerHeight / view[0].length;

        for (Space[] spaces : view) {

            for (Space curSpace : spaces) {

                Item item = curSpace.getItemOnField();
                Entity entity = curSpace.getEntityOnField();
                curSpace.setDimensions(cellHeight, cellWidth);

                if (item != null) {

                    item.setDimensions(cellHeight, cellWidth);
                }

                if (entity != null) {

                    entity.setDimensions(cellHeight, cellWidth);
                }
            }
        }
    }

    /**
     * Draws the view of the map. Including background tiles and foreground entities / items
     * @param view The viewPort from the camera centered around a target entity, usually the player.
     */
    public void drawGameAndBG(Space[][] view) {

        double layerWidth = gameLayer.getWidth();
        double layerHeight = gameLayer.getHeight();
        GraphicsContext gameGC = gameLayer.getGraphicsContext2D();
        gameGC.clearRect(0, 0, layerWidth, layerHeight);
        GraphicsContext bgGC = backgroundLayer.getGraphicsContext2D();
        bgGC.clearRect(0, 0, layerWidth, layerHeight);
        double cellWidth = layerWidth / view.length;
        double cellHeight = layerHeight / view[0].length;

        for (int y = 0; y < view.length; y++) {

            for (int x = 0; x < view[y].length; x++) {

                Space curSpace = view[y][x];
                Entity entity = curSpace.getEntityOnField();
                Item item = curSpace.getItemOnField();

                curSpace.draw(y * cellHeight,x * cellWidth,bgGC);


                if (item != null){

                    item.draw(y * cellHeight+ (cellHeight * 0.25) , x * cellWidth + (cellWidth * 0.40), gameGC);
                }

                if (entity!= null) {

                    if (entity instanceof Wall) {
                        entity.draw(y * cellHeight - (cellHeight * 0.75), x * cellWidth,gameGC);

                    } else {
                        entity.draw(y * cellHeight - (cellHeight * 0.375), x * cellWidth - (cellHeight * 0.05),gameGC);
                    }
                }
            }
        }
    }

    /**
     * Draws the UI onto the UILayer Canvas.
     * @param view The viewPort from the camera centered around a target entity, usually the player.
     */
    private void drawUI(Space[][] view) {

        long startTime = System.currentTimeMillis();

        double layerWidth = UILayer.getWidth();
        double layerHeight = UILayer.getHeight();
        double cellWidth = layerWidth / view.length / 2;
        double cellHeight = layerHeight / view.length / 2;
        GraphicsContext UIGC = UILayer.getGraphicsContext2D();
        UIGC.clearRect(0, 0, layerWidth, layerHeight);
        int playerHealth = level.getPlayer().getHealth();
        double lastHeartX = 0;
        int counter = 0;
        fullHeart.setDimensions(cellHeight,cellWidth);

        long endtime = System.currentTimeMillis();
        System.out.println("Preparing to draw the ui took " + (endtime - startTime) + "ms");
        startTime = endtime;

        for (int i = 0; i < playerHealth; i += 2) {

            fullHeart.draw(0,cellWidth * counter, UIGC);
            lastHeartX = counter * cellWidth;
            counter++;
        }

        endtime = System.currentTimeMillis();
        System.out.println("drawing the full hearts took " + (endtime - startTime) + "ms");
        startTime = endtime;

        if (playerHealth % 2 != 0) {

            halfHeart.setDimensions(cellHeight,cellWidth);
            halfHeart.draw(0, lastHeartX, UIGC);

        }

        endtime = System.currentTimeMillis();
        System.out.println("drawing the half heart took " + (endtime - startTime) + "ms");
    }

    /**
     * Draws the whole screen. Including backgroundLayer, gameLayer and UILayer.
     */
    public void drawScreen() {

        long starttime = System.currentTimeMillis();

        Space[][] spaces = level.getMap().getSpaces();

        long endtime = System.currentTimeMillis();
        System.out.println("Getting Spaces from the map took " +(endtime - starttime) + "ms");
        starttime = endtime;

        Camera camera = level.getCamera();

        endtime = System.currentTimeMillis();
        System.out.println("Getting camera from the level took " +(endtime - starttime) + "ms");
        starttime = endtime;

        Space[][] view = camera.getViewPort(spaces);

        endtime = System.currentTimeMillis();
        System.out.println("Getting views from the camera took " +(endtime - starttime) + "ms");
        starttime = endtime;

        scaleCanvases(view);

        endtime = System.currentTimeMillis();
        System.out.println("Scaling the canvases took " + (endtime - starttime) + "ms");
        starttime = endtime;

        drawGameAndBG(view);

        endtime = System.currentTimeMillis();
        System.out.println("Drawing the baclground and game layer took " + (endtime - starttime) + "ms");
        starttime = endtime;

        drawUI(view);

        endtime = System.currentTimeMillis();
        System.out.println("Drawing the UI took " + (endtime - starttime) + "ms");

    }

    /**
     * Main Method of the game. Plays one cycle of the game loop.
     * @param code The keycode of the key the player clicked. Used for determining the action the player wants to take.
     * @throws InterruptedException
     */
    public void move(KeyCode code) throws InterruptedException {

        System.out.println("=".repeat(50));
        long startTime = System.currentTimeMillis();

        StatusCode statuscode = level.play(code);

        long endTime = System.currentTimeMillis();
        System.out.println("level calculation took " + (endTime - startTime) + " ms");
        endTime = startTime;

        drawScreen();

        endTime = System.currentTimeMillis();
        System.out.println("Whole screen draw took " + (endTime - startTime) + " ms");

        if (statuscode == StatusCode.PLAYER_WON) {

            level = level.nextLevel();
            drawScreen();
        } else if (statuscode == StatusCode.PLAYER_LOST) {

            System.exit(0);
        }
    }
}
