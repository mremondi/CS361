package proj6BeckChanceRemondiSalerno.CompositionActions;

import proj6BeckChanceRemondiSalerno.CompositionManager;
import proj6BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * Created by Graham on 11/5/16.
 */
public class CutAction extends CompositionAction {

    private ArrayList<NoteGroupable> cutNotes;

    public CutAction(ArrayList<NoteGroupable> cutNotes, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.cutNotes = cutNotes;
    }

    @Override
    public void undo() {
        for (NoteGroupable noteGroupable: cutNotes) {
            compositionManager.addGroupable(noteGroupable);
        }
    }

    @Override
    public void redo() {
        compositionManager.cutNotes(cutNotes);
    }
}
