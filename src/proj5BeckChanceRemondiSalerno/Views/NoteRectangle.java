package proj5BeckChanceRemondiSalerno.Views;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Created by mremondi on 10/20/16.
 */
public class NoteRectangle extends Rectangle {


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