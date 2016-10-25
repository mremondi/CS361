package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MoveAction implements CompositionAction {

    private ArrayList<NoteGroupable> movedNotes;
    private double dx;
    private double dy;

    public MoveAction(ArrayList<NoteGroupable> movedNotes, double dx, double dy) {
        this.movedNotes = movedNotes;
        this.dx = dx;
        this.dy = dy;
    }

    public void redo() {
        CompositionManager.getInstance().moveNotes(movedNotes, dx, dy);
    }

    public void undo() {
        CompositionManager.getInstance().moveNotes(movedNotes, -dx, -dy);
    }

}
