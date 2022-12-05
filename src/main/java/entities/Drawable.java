package entities;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    /**
     * Draws the drawable to the given graphics context.
     *
     * @param yPos The y position for the top left pixel of the sprite.
     * @param xPos The x position for the top left pixel of the sprite.
     * @param gc The graphics context of the canvas this sprite should be drawn on.
     */
    void draw(double yPos, double xPos, GraphicsContext gc);

    /**
     * Sets the pixel dimensions the sprite will take once it's drawn on the canvas.
     *
     * @param height The height in pixels that the sprite will take up one the canvas once drawn on the canvas.
     * @param width The width in pixels that the sprite will take up one the canvas once drawn on the canvas.
     */
    void setDimensions(double height, double width);

}
