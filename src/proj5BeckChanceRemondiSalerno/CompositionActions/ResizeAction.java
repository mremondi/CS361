
package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;


public class ResizeAction implements CompositionAction {

    private ArrayList<NoteGroupable> resizedNotes;
    private double dx;


    public ResizeAction(ArrayList<NoteGroupable> resizedNotes, double dx) {
        this.resizedNotes = resizedNotes;
        this.dx = dx;
    }

    public void redo() {
        CompositionManager.getInstance().resizeNotes(resizedNotes, dx);
    }

    public void undo() {
        CompositionManager.getInstance().resizeNotes(resizedNotes, -dx);
    }

}
