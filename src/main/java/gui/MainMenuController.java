package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {

    /**
     * Ends the program.
     */
    public void quitGame() {

        System.exit(0);
    }

    /**
     * Unloads the main menu screen and load the level select screen.
     * @param event The event that triggered this method. Usually the start game button on the main screen.
     * @throws IOException
     */
    public void play(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LevelSelectMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}