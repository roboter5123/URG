package entities;

import javafx.scene.image.Image;
import utilities.Map;

public interface Interactable {

    void interact(Entity entity, Map map);
    Image getImage();

}
