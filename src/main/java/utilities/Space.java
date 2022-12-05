package utilities;

import entities.Drawable;
import entities.Entity;
import gui.Sprite;
import items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Space implements Drawable {

    private Entity entityOnField;

    private Item itemOnField;

    private int xPos;

    private int yPos;
    private final Sprite sprite;

    public Space(int yPos, int xPos) {

        this.yPos = yPos;
        this.xPos = xPos;
        this.sprite = new Sprite(new Image("entities/Floor"+((xPos+yPos)%2)+".png"));

    }

    public Space() {
        this.sprite = new Sprite(new Image("entities/Floor"+0+".png"));
    }

    /**
     * Draws the drawable to the given graphics context.
     *
     * @param yPos The y position for the top left pixel of the sprite.
     * @param xPos The x position for the top left pixel of the sprite.
     * @param gc   The graphics context of the canvas this sprite should be drawn on.
     */
    @Override
    public void draw(double yPos, double xPos, GraphicsContext gc) {

        this.sprite.draw(yPos,xPos,gc);
    }

    /**
     * Sets the pixel dimensions the sprite will take once it's drawn on the canvas.
     *
     * @param height The height in pixels that the sprite will take up one the canvas once drawn on the canvas.
     * @param width  The width in pixels that the sprite will take up one the canvas once drawn on the canvas.
     */
    @Override
    public void setDimensions(double height, double width) {
        this.sprite.setDimensions(height, width);
    }

    public Entity getEntityOnField() {
        return entityOnField;
    }

    public void setEntityOnField(Entity entityOnField) {

        this.entityOnField = entityOnField;
    }

    public Item getItemOnField() {

        return itemOnField;
    }

    public void setItemOnField(Item itemOnField) {

        this.itemOnField = itemOnField;
    }

    @Override
    public String toString() {
        return "Utilities.Space{" +
                "entityOnField=" + entityOnField +
                '}';
    }


}
