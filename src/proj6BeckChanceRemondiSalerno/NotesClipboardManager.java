package proj6BeckChanceRemondiSalerno;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import proj6BeckChanceRemondiSalerno.CompositionActions.PasteAction;
import proj6BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * Created by Graham on 11/5/16.
 */
public class NotesClipboardManager {

    final private CompositionManager compositionManager;

    final private DataFormat notesClipboardKey = new DataFormat("notes");

    NotesClipboardManager(CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
    }

    /**
     * Copies the given notes to the clipboard and removes them from the composition
     * @param notes notes to cut
     */
    public void cutNotes(ArrayList<NoteGroupable> notes) {
        copyNotes(notes);
        compositionManager.deleteGroupables(notes);
    }

    /**
     * Copies the given notes to the clipboard
     * @param notes notes to copy
     */
    public void copyNotes(ArrayList<NoteGroupable> notes) {
        ClipboardContent content = new ClipboardContent();
        content.put(notesClipboardKey, notes);
        Clipboard.getSystemClipboard().setContent(content);
    }

    /**
     * Adds the notes on the clipboard to to the composition.
     */
    public void pasteNotes() {
        Object content = Clipboard.getSystemClipboard().getContent(notesClipboardKey);
        if (content != null) {
            compositionManager.deselectAllNotes();
            ArrayList<NoteGroupable> notes = (ArrayList<NoteGroupable>)content;
            ArrayList<NoteGroupable> pastedNotes = new ArrayList<>();
            for (NoteGroupable noteGroupable : notes) {
                NoteGroupable noteClone = noteGroupable.clone();
                compositionManager.addGroupable(noteClone);
                pastedNotes.add(noteClone);
            }
            PasteAction pasteAction = new PasteAction(pastedNotes, compositionManager);
            compositionManager.getCompositionActionManager().actionCompleted(pasteAction);
        }
    }

    /**
     * Whether there are notes on the clipboard currently
     * @return Whether or not there are notes on the clipboard
     */
    public boolean isClipboardEmpty() {
        return Clipboard.getSystemClipboard().getContent(notesClipboardKey) == null;
    }


}
