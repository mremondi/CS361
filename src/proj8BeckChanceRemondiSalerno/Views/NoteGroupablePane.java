/*
 * File: NoteGroupablePane.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */

package proj8BeckChanceRemondiSalerno.Views;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 * This class models a pane containing notes
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class NoteGroupablePane extends Pane implements NoteView {

    /**
     * current selected state
     */
    private boolean selected = false;

    /**
     * Whether the pane represents a single note.
     */
    private boolean containsSingleNote = false;

    /**
     * Constructor. Initializes the CSS class
     */
    public NoteGroupablePane() {
        this.getStyleClass().add("singleNoteGroupablePane");
    }

    /**
     * Changes the width of the pane
     *
     * @param dx How much to change the width by
     */
    public void changeWidth(double dx) {
        for (Node node : getChildren()) {
            if (node instanceof NoteView) {
                ((NoteView) node).changeWidth(dx);
            }
        }
    }

    /**
     * Sets the note color
     * @param color
     */
    public void setNoteFill(Paint color) {
        for(Node child: getChildren()) {
            ((NoteView)child).setNoteFill(color);
        }
    }

    /**
     * Setter for selected state
     *
     * @param selected new selected state
     */
    public void setSelected(boolean selected) {
        if (containsSingleNote) {
            this.getStyleClass().setAll("singleNoteGroupablePane");
        } else if (selected) {
            this.getStyleClass().setAll("selectedGroupablePane");
        } else {
            this.getStyleClass().setAll("unselectedGroupablePane");
        }
        this.selected = selected;
        for (Node node : getChildren()) {
            if (node instanceof NoteView) {
                ((NoteView) node).setSelected(selected);
            }
        }
    }

    /**
     * Determines whether a location is in the bounds
     *
     * @param x x location to test
     * @param y y location to test
     * @return Wether the location is inside the bounds of the pane
     */
    public boolean getIsInBounds(double x, double y) {
        Bounds bounds = getBoundsInParent();
        return (x <= bounds.getMaxX() &&
                y <= bounds.getMaxY() &&
                x >= bounds.getMinX() &&
                y >= bounds.getMinY());
    }

    /**
     * Checks to see if the note is within the given bounds of a rectangle.
     *
     * @param rectXMin the smallest x coordinate of the rectangle
     * @param rectYMin the smallest y coordinate of the rectangle
     * @param rectXMax the biggest x coordinate of the rectangle
     * @param rectYMax the bigget y coordinate of the rectangle
     * @return a boolean value indicating whether this note is within the rectangle.
     */
    public boolean getIsInRectangleBounds(double rectXMin, double rectYMin,
                                          double rectXMax, double rectYMax) {
        Bounds bounds = getBoundsInParent();
        boolean xInBounds = (bounds.getMinX() < rectXMax && bounds.getMinX() >
                rectXMin) ||
                (bounds.getMaxX() > rectXMin && bounds.getMaxX() < rectXMax);
        boolean yInBounds = (bounds.getMinY() > rectYMin && bounds.getMinY() <
                rectYMax) ||
                (bounds.getMaxY() > rectYMin && bounds.getMaxY() < rectYMax);
        return xInBounds && yInBounds;
    }

    /**
     * Checks whether the coordinates of a mouse click are within 5 pixels
     * of the right side of the note's rectangle.
     *
     * @param x the mouse click's x coordinate
     * @param y the mouse click's y coordinate
     * @return a boolean value indicating whether the mouse click is on the note's edge.
     */
    public boolean getIsOnEdge(double x, double y) {
        Bounds bounds = getBoundsInParent();

        return y < bounds.getMaxY() &&
                y > bounds.getMinY() &&
                ((x >= bounds.getMaxX() - 5) && x < bounds.getMaxX());
    }

    /**
     * Setter for containsSingleNote
     *
     * @param containsSingleNote New containsSingleNote value
     */
    public void setContainsSingleNote(boolean containsSingleNote) {
        this.containsSingleNote = containsSingleNote;
    }

    /**
     * Rounds the y coordinate of the note's rectangle in order to snap to a
     * space between two horizontal bars.
     */
    public void roundUpYLocation() {
        if (getLayoutY() % 10 < 5) {
            setLayoutY(getLayoutY() - (getLayoutY() % 10));
        } else {
            setLayoutY(getLayoutY() + 10 - (getLayoutY() % 10));
        }
    }
}