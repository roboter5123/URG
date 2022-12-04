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
    int playerMaxHealth;
    int opponentCount;

    /**
     * Only called during creation of this controller. Should not be used outside of creation.
     * @param url Not used
     * @param resourceBundle Not used
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

        playerMaxHealth = mapSize * 2;
        opponentCount = mapSize * 2;
        level = new Level(mapSize, playerMaxHealth, opponentCount);
        drawScreen();
    }

    /**
     * Scales the 3 canvases (backgroundLayer, gameLayer and UILayer) to the whole window.
     */
    public void scaleCanvases() {

        double windowHeight = root.getHeight();
        double windowWidth = root.getWidth();
        backgroundLayer.setHeight(windowHeight);
        backgroundLayer.setWidth(windowWidth);
        gameLayer.setWidth(windowWidth);
        gameLayer.setHeight(windowHeight);
        UILayer.setHeight(windowHeight);
        UILayer.setWidth(windowWidth);
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

        Image floor0 = new Image("entities/Floor0.png");
        Image floor1 = new Image("entities/Floor1.png");
        for (int y = 0; y < view.length; y++) {

            for (int x = 0; x < view[y].length; x++) {

                Entity entity = view[y][x].getEntityOnField();
                Item item = view[y][x].getItemOnField();

                if ((view[y][x].getxPos() + view[y][x].getyPos()) % 2 == 0) {

                    new Sprite(x * cellWidth, y * cellHeight, cellHeight, cellWidth, floor0).draw(bgGC);

                } else {

                    new Sprite(x * cellWidth, y * cellHeight, cellHeight, cellWidth, floor1).draw(bgGC);
                }

                if (item != null){

                    new Sprite(x * cellWidth + (cellWidth * 0.25), y * cellHeight , cellHeight * 0.5 , cellWidth * 0.5, item.getImage()).draw(gameGC);
                }

                if (entity!= null) {

                    if (entity instanceof Wall) {

                        new Sprite(x * cellWidth, y * cellHeight - (cellHeight * 0.75), cellHeight * 1.75, cellWidth, entity.getImage()).draw(gameGC);

                    } else {

                        new Sprite(x * cellWidth - (cellHeight * 0.05), y * cellHeight - (cellHeight * 0.375), cellHeight * 1.1, cellWidth * 1.1, entity.getImage()).draw(gameGC);
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

        double layerWidth = UILayer.getWidth();
        double layerHeight = UILayer.getHeight();
        double cellWidth = layerWidth / view.length / 2;
        double cellHeight = layerHeight / view.length / 2;
        GraphicsContext UIGC = UILayer.getGraphicsContext2D();
        UIGC.clearRect(0, 0, layerWidth, layerHeight);
        int playerHealth = level.getPlayer().getHealth();
        double lastHeartX = 0;
        int counter = 0;
        Sprite fullHeart = new Sprite(0, 0, cellHeight, cellWidth, new Image("uielements/heart2.png"));

        for (int i = 0; i < playerHealth; i += 2) {

            fullHeart.setX(cellWidth * counter);
            fullHeart.draw(UIGC);
            lastHeartX = counter * cellWidth;
            counter++;
        }

        if (playerHealth % 2 != 0) {

            new Sprite(lastHeartX, 0, cellHeight, cellWidth, new Image("uielements/heart1.png")).draw(UIGC);
        }
    }

    /**
     * Draws the whole screen. Including backgroundLayer, gameLayer and UILayer.
     */
    public void drawScreen() {

        Space[][] view = level.getCamera().getViewPort(level.getMap().getSpaces());
        long startTime = System.currentTimeMillis();
        scaleCanvases();
        long endTime = System.currentTimeMillis();
        System.out.println("Canvas scaling took " + (endTime - startTime) + " ms");
        startTime = endTime;
        drawGameAndBG(view);
        endTime = System.currentTimeMillis();
        System.out.println("Background and game drawing took " + (endTime - startTime) + " ms");
        startTime = endTime;
        drawUI(view);
        endTime = System.currentTimeMillis();
        System.out.println("UI drawing took " + (endTime - startTime) + " ms");
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
        drawScreen();

        if (statuscode == StatusCode.PLAYER_WON) {

            System.exit(0);
        } else if (statuscode == StatusCode.PLAYER_LOST) {

            System.exit(0);
        }
    }
}
