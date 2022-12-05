package entities;

import gui.Sprite;
import javafx.scene.image.Image;

public class Wall extends Entity implements Drawable {

    public Wall() {

        this.setSprite(new Sprite(new Image("entities/Wall.png")));
    }

    /**
     * Sets the pixel dimensions the sprite will take once it's drawn on the canvas.
     *
     * @param height The height in pixels that the sprite will take up one the canvas once drawn on the canvas.
     * @param width  The width in pixels that the sprite will take up one the canvas once drawn on the canvas.
     */
    @Override
    public void setDimensions(double height, double width) {

        this.getSprite().setDimensions(height * 1.75, width);
    }
}
