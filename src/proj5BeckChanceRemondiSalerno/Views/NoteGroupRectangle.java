package proj5BeckChanceRemondiSalerno.Views;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Created by Graham on 10/22/16.
 */
public class NoteGroupRectangle extends Rectangle {

    private boolean selected = false;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

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
     *
     * @return a boolean value indicating whether this note is within the rectangle.
     */
    public boolean getIsInRectangleBounds(double rectXMin, double rectYMin,
                                          double rectXMax, double rectYMax) {
        Bounds bounds = getBoundsInParent();
        boolean xInBounds = (bounds.getMinX() < rectXMax && bounds.getMinX() > rectXMin) ||
                (bounds.getMaxX() >rectXMin && bounds.getMaxX() < rectXMax);
        boolean yInBounds = (bounds.getMinY() > rectYMin && bounds.getMinY() < rectYMax ) ||
                (bounds.getMaxY() > rectYMin && bounds.getMaxY() < rectYMax);
        return xInBounds && yInBounds;
    }


    /**
     * Checks whether the coordinates of a mouse click are within 5 pixels
     * of the right side of the note's rectangle.
     *
     * @param x the mouse click's x coordinate
     * @param y the mouse click's y coordinate
     *
     * @return a boolean value indicating whether the mouse click is on the note's edge.
     */
    public boolean getIsOnEdge(double x, double y) {
        Bounds bounds = getBoundsInParent();
        return y < bounds.getMaxY() &&
                y > bounds.getMinY() &&
                ((x >= bounds.getMaxX() - 5) && x < bounds.getMaxX());
    }

}



