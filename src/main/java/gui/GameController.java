package gui;

import entities.Entity;
import entities.Opponent;
import entities.Player;
import entities.Wall;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import utilities.Level;
import utilities.Space;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    GridPane root;

    Level level;
    int mapSize;
    int playerMaxHealth;
    int opponentCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        drawMap();
    }

    public void drawMap() {

        Space[][] view = level.getCamera().getMap(level.getMap().getSpaces());

        for (int y = 0; y < view.length; y++) {

            for (int x = 0; x < view[y].length; x++) {

                Entity entity = view[y][x].getEntityOnField();
                Rectangle floor = new Rectangle(20.0, 20.0, Paint.valueOf("#979797"));
                root.add(floor, x, y);
                if (entity instanceof Player) {

                    Circle player = new Circle(10.0, Paint.valueOf("green"));
                    root.add(player, x, y);
                } else if (entity instanceof Opponent) {

                    Circle opponent = new Circle(10.0, Paint.valueOf("red"));
                    root.add(opponent, x, y);
                } else if (entity instanceof Wall) {

                    Rectangle wall = new Rectangle(20.0, 20.0, Paint.valueOf("#717171"));
                    root.add(wall, x, y);
                }
            }
        }
    }

    public void move(KeyCode code) {

        level.getPlayer().move(level.getMap(), code.toString());
        level.checkOpponentHealth();
        level.addPlayerRound();

        if (level.getPlayerRound() % 2 == 0){

            level.moveOpponents();
        }

        drawMap();
    }
}
