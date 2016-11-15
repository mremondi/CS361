/*
 * File: NotesClipboardManager.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 7
 * Due Date: November 10, 2016
 */


package proj7BeckChanceRemondiSalerno;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import proj7BeckChanceRemondiSalerno.CompositionActions.PasteAction;
import proj7BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 *
 * This class models a manager for cutting, copying, and pasting notes
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class NotesClipboardManager {

    /**
     * The composition manager
     */
    final private CompositionManager compositionManager;

    /**
     * The data format for notes on the clipboard
     */
    final private DataFormat notesClipboardKey = new DataFormat("notes");

    /**
     * The property for the clipboard's empty state
     */
    private SimpleBooleanProperty clipboardEmptyProperty = new SimpleBooleanProperty();

    /**
     * Constructor
     * @param compositionManager The composition manager
     */
    NotesClipboardManager(CompositionManager compositionManager) {
        clipboardEmptyProperty.set(getClipboardContent()==null);
        this.compositionManager = compositionManager;
    }

    /**
     * Copies the given notes to the clipboard and removes them from the composition
     * @param notes notes to cut
     */
    public void cutNotes(ArrayList<NoteGroupable> notes) {
        copyNotes(notes);
        compositionManager.deleteGroupables(notes);
        clipboardEmptyProperty.set(getClipboardContent()==null);
    }

    /**
     * Copies the given notes to the clipboard
     * @param notes notes to copy
     */
    public void copyNotes(ArrayList<NoteGroupable> notes) {
        ClipboardContent content = new ClipboardContent();
        content.put(notesClipboardKey, notes);
        Clipboard.getSystemClipboard().setContent(content);
        clipboardEmptyProperty.set(getClipboardContent()==null);
    }

    /**
     * Adds the notes on the clipboard to to the composition.
     */
    public void pasteNotes() {
        Object content = getClipboardContent();
        if (content != null) {
            compositionManager.deselectAllNotes();
            ArrayList<NoteGroupable> notes = (ArrayList<NoteGroupable>)content;
            ArrayList<NoteGroupable> pastedNotes = new ArrayList<>();
            for (NoteGroupable noteGroupable : notes) {
                compositionManager.addGroupable(noteGroupable);
                pastedNotes.add(noteGroupable);
                // why clone??
//                NoteGroupable noteClone = noteGroupable.clone();
//                compositionManager.addGroupable(noteClone);
//                pastedNotes.add(noteClone);
            }
            PasteAction pasteAction = new PasteAction(pastedNotes, compositionManager);
            compositionManager.actionCompleted(pasteAction);
        }
    }

    private Object getClipboardContent() {
        return Clipboard.getSystemClipboard().getContent(notesClipboardKey);
    }


    /**
     * Getter for clipboardEmptyProperty
     * @return clipboardEmptyProperty
     */
    public SimpleBooleanProperty getClipboardEmptyProperty() {
        return clipboardEmptyProperty;
    }


}
