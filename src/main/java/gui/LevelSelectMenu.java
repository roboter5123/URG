package gui;

import javafx.scene.Parent;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LevelSelectMenu {

    Stage stage;
    Scene scene;
    Parent root;

    /**
     * Unloads the level select screen and load the main menu screen back.
     * @param event The event that triggered this method. usually the back button on the level select screen.
     */
    public void backToMainMenu(ActionEvent event) {

        try {

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts the game.
     * @param event The event that triggered this method. Usually one of the buttons on the level select screen.
     * @throws IOException
     */
    public void startGame(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        root = loader.load();
        GameController gamecontroller = loader.getController();
        Button source = (Button) event.getSource();

        System.out.println(source.getText());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setOnKeyPressed(event1 -> {

            KeyCode code = event1.getCode();
            if (KeyCode.W.equals(code) || KeyCode.A.equals(code) || KeyCode.S.equals(code) || KeyCode.D.equals(code)) {
                try {
                    gamecontroller.move(code);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        stage.setScene(scene);
        gamecontroller.startGame(source.getText());
        stage.show();
    }
}
