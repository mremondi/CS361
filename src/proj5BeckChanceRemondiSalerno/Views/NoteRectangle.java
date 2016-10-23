/*
 * File: NoteRectangle.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 5
 * Due Date: October 23, 2016
 */

package proj5BeckChanceRemondiSalerno.Views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by mremondi on 10/20/16.
 */
public class NoteRectangle extends Rectangle implements NoteView {


    private boolean selected = false;

    public NoteRectangle(double width, double x, double y) {
        super(width, 10);
        setX(x);
        setY(y);
    }


    public void setSelected(boolean selected) {

        if (selected) {
            setStroke(Color.RED);
            setStrokeWidth(3);
        } else {
            setStroke(Color.BLACK);
            setStrokeWidth(1);
        }
        this.selected = selected;
    }

    public void changeWidth(double dx) {
        setWidth(getWidth() + dx);
    }

}