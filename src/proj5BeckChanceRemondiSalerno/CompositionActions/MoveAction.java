package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.awt.*;
import java.awt.geom.Point2D;

public class MoveAction implements CompositionAction {

    private NoteGroupable note;
    private Point2D.Double startPoint;
    private Point2D.Double endPoint;

    public MoveAction(NoteGroupable note, Point2D.Double startPoint, Point2D.Double endPoint) {
        this.note = note;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public void redo() {

    }

    public void undo() {

    }

}
