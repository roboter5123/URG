package gui;

import entities.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite implements Drawable {
    double height;
    double width;
    Image image;

    public Sprite(Image image) {

        this.image = image;
    }

    /**
     * Draws the sprite to a graphics context
     * @param gc The graphics context to which the sprite should be drawn.
     */
    public void draw(double yPos, double xPos, GraphicsContext gc){

        gc.drawImage(image,xPos,yPos,width,height);
    }

    /**
     * Sets the pixel dimensions the sprite will take once it's drawn on the canvas.
     *
     * @param height The height in pixels that the sprite will take up one the canvas once drawn on the canvas.
     * @param width  The width in pixels that the sprite will take up one the canvas once drawn on the canvas.
     */
    public void setDimensions(double height, double width){

        this.height = height;
        this.width = width;
    }

}
