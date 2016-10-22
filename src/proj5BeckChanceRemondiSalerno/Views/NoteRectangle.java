package proj5BeckChanceRemondiSalerno.Views;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Created by mremondi on 10/20/16.
 */
public class NoteRectangle {
    /**
     * The rectangle representing the note visually.
     */
    private Rectangle noteBox;

    private boolean selected;

    public NoteRectangle(Rectangle rectangle){
        this.noteBox = rectangle;
    }

    /**
     * Accessor method for the bounds of the note in the compositon
     *
     * @return bounds of the note
     */
    public Bounds getBounds() {
        return this.noteBox.getBoundsInParent();
    }


    /**
     * Checks to see if the mouse click is inside of this note's rectangle.
     *
     * @param x MouseEvent x coordinate
     * @param y MouseEvent y coordinate
     * @return boolean value for whether the click is inside of the rectangle.
     */
    public boolean getIsInBounds(double x, double y) {
        Bounds bounds = getBounds();
        return (x <= bounds.getMaxX() &&
                y <= bounds.getMaxY() &&
                x >= bounds.getMinX() &&
                y >= bounds.getMinY());
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
        Bounds bounds = getBounds();
        return y < bounds.getMaxY() &&
                y > bounds.getMinY() &&
                ((x >= bounds.getMaxX() - 5) && x < bounds.getMaxX());
    }

    /**
     * Rounds the y coordinate of the note's rectangle in order to snap to a
     * space between two horizontal bars.
     */
    public void roundToNearestYLocation() {
        if (this.noteBox.getY() % 10 < 5){
            setPosition(this.noteBox.getX(), this.noteBox.getY() - (this.noteBox.getY() % 10));
        }
        else{
            setPosition(this.noteBox.getX(), this.noteBox.getY() + (10 - (this.noteBox.getY() % 10)));
        }
    }

    public double getX(){
        return this.noteBox.getX();
    }

    public double getY(){
        return this.noteBox.getY();
    }

    public void setWidth(double width){
        noteBox.setWidth(width);
    }

    /**
     * Accessor method for the graphical note box
     *
     * @return Graphical note box
     */
    public Rectangle getNoteBox() {
        return this.noteBox;
    }

    public void select() {
        this.noteBox.setStroke(Color.RED);
        this.noteBox.setStrokeWidth(3);
    }

    public void unselect() {
        this.noteBox.setStroke(Color.BLACK);
        this.noteBox.setStrokeWidth(1);
    }

    public Paint getFill(){
        return this.noteBox.getFill();
    }

    public void setPosition(double x, double y) {
        this.noteBox.setX(x);
        this.noteBox.setY(y);
    }

}
