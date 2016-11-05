package proj6BeckChanceRemondiSalerno.CompositionActions;

import proj6BeckChanceRemondiSalerno.CompositionManager;
import proj6BeckChanceRemondiSalerno.Models.NoteGroupable;
import proj6BeckChanceRemondiSalerno.NotesClipboardManager;

import java.util.ArrayList;

/**
 * Created by Graham on 11/5/16.
 */
public class CutAction extends CompositionAction {

    private ArrayList<NoteGroupable> cutNotes;
    private NotesClipboardManager notesClipboardManager;

    public CutAction(ArrayList<NoteGroupable> cutNotes, CompositionManager compositionManager, NotesClipboardManager notesClipboardManager) {
        this.compositionManager = compositionManager;
        this.cutNotes = cutNotes;
        this.notesClipboardManager = notesClipboardManager;
    }

    @Override
    public void undo() {
        for (NoteGroupable noteGroupable: cutNotes) {
            compositionManager.addGroupable(noteGroupable);
        }
    }

    @Override
    public void redo() {
        notesClipboardManager.cutNotes(cutNotes);
    }
}
