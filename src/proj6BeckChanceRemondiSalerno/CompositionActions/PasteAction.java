package proj6BeckChanceRemondiSalerno.CompositionActions;

import proj6BeckChanceRemondiSalerno.CompositionManager;
import proj6BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * Created by Graham on 11/5/16.
 */
public class PasteAction extends CompositionAction {

    private ArrayList<NoteGroupable> pastedNotes;

    public PasteAction(ArrayList<NoteGroupable> pastedNotes, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.pastedNotes = pastedNotes;
    }

    @Override
    public void undo() {
        for (NoteGroupable noteGroupable: pastedNotes) {
            compositionManager.deleteGroupable(noteGroupable);
        }
    }

    @Override
    public void redo() {
        for (NoteGroupable noteGroupable : pastedNotes) {
            compositionManager.addGroupable(noteGroupable.clone());
        }
    }
}
