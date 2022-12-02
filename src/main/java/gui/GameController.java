package gui;

import entities.Entity;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import utilities.Level;
import utilities.Space;

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

        playerMaxHealth = mapSize * 2 + 1;
        opponentCount = mapSize * 2;
        level = new Level(mapSize, playerMaxHealth, opponentCount);
        drawScreen();
    }

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

    public void drawGameAndBG(Space[][] view) {

        double layerWidth = gameLayer.getWidth();
        double layerHeight = gameLayer.getHeight();
        GraphicsContext gameGC = gameLayer.getGraphicsContext2D();
        gameGC.clearRect(0, 0, layerWidth, layerHeight);
        GraphicsContext bgGC = backgroundLayer.getGraphicsContext2D();
        bgGC.clearRect(0, 0, layerWidth, layerHeight);

        double cellWidth = layerWidth / view.length;
        double cellheight = layerHeight / view[0].length;

        for (int y = 0; y < view.length; y++) {

            for (int x = 0; x < view[y].length; x++) {

                Entity entity = view[y][x].getEntityOnField();
                new Sprite(x * cellWidth, y  , cellheight , cellWidth, new Image("entities/Floor.png")).draw(bgGC);

                if (null != entity) {

                    new Sprite(x * cellWidth, y * cellheight, cellheight, cellWidth, entity.getImage()).draw(gameGC);

                }
            }
        }
    }
    private void drawUI(Space[][] view) {

        double layerWidth = UILayer.getWidth();
        double layerHeight = UILayer.getHeight();
        double cellWidth = layerWidth / view.length/2;
        double cellHeight  = layerHeight / view.length/2;
        GraphicsContext UIGC = UILayer.getGraphicsContext2D();
        UIGC.clearRect(0,0,layerWidth,layerHeight);
        int playerHealth = level.getPlayer().getHealth();
        double lastHeartX = 0;
        int counter = 0;
        for (int i = 0; i < playerHealth; i+=2) {

            new Sprite(cellWidth * counter, 0,cellHeight,cellWidth,new Image("uielements/FullHeart.png")).draw(UIGC);
            lastHeartX = counter * cellWidth;
            counter++;
        }

        if (playerHealth% 2 != 0){

            new Sprite(lastHeartX, 0,cellHeight,cellWidth,new Image("uielements/HalfHeart.png")).draw(UIGC);

        }
    }

    public void drawScreen() {

        Space[][] view = level.getCamera().getMap(level.getMap().getSpaces());
        scaleCanvases();
        drawGameAndBG(view);
        drawUI(view);
    }



    public void move(KeyCode code) throws InterruptedException {

        level.getPlayer().move(level.getMap(), code.toString());
        level.checkOpponentHealth();
        level.addPlayerRound();

        if (level.getPlayerRound() % 2 == 0) {

            level.moveOpponents();
        }

        if (level.isGameOver() && !level.isPlayerAlive()) {

            drawScreen();
            System.exit(0);
        }

        if (level.checkOpponentHealth()) {

            drawScreen();
            System.exit(0);
        }

        drawScreen();
    }
}
