/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj5BeckChanceRemondiSalerno.Views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class models a single note view
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */

public class NoteRectangle extends Rectangle implements NoteView {

    public static final int MINIMUM_WIDTH = 5;

    /**
     * current selected state
     */
    private boolean selected = false;

    /**
     * Constructor
     * @param width width of the note
     * @param x x location
     * @param y y location
     */
    public NoteRectangle(double width, double x, double y) {
        super(width, 10);
        setX(x);
        setY(y);
    }

    /**
     * Setter for selected state
     * @param selected new selected state
     */
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

    /**
     * Changes the width of the pane
     * @param dx How much to change the width by
     */
    public void changeWidth(double dx) {
        if (this.getWidth() + dx < MINIMUM_WIDTH){
            setWidth(MINIMUM_WIDTH);
        }
        setWidth(getWidth() + dx);
    }

}