/*
 * File: NoteView.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno.Views;

import javafx.scene.paint.Paint;

/**
 * This interface models a view that represents a note view on a composition
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public interface NoteView {

    /**
     * Sets the note rectangle to selected
     *
     * @param selected a boolean indicator of whether the note is selected
     */
    void setSelected(boolean selected);

    /**
     * Changes the width of the note rectangle
     *
     * @param dx the change in the x direction
     */
    void changeWidth(double dx);

    /**
     * Sets the note color
     *
     * @param color
     */
    void setNoteFill(Paint color);
}
