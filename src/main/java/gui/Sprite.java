package gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

    double x;
    double y;
    double height;
    double width;
    Image image;

    public Sprite(double x, double y, double height, double width, Image image) {

        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.image = image;
    }

    public void draw(GraphicsContext gc){


        gc.drawImage(image,x,y,width,height);
    }

    public double getX() {

        return x;
    }

    public void setX(double x) {

        this.x = x;
    }
}
