/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;

/**
 * This class implements the CompositionAction interface and represents the action
 * of deselecting a note in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class DeselectAction implements CompositionAction {
    /**
     * The note to deselect.
     */
    private NoteGroupable note;

    /**
     * Constructor
     *
     * @param note the note to deselect
     */
    public DeselectAction(NoteGroupable note) {
        this.note = note;
    }

    /**
     * Redoes the deselection of a note.
     */
    @Override
    public void redo() {
        CompositionManager.getInstance().deselectNote(note);
    }

    /**
     * Undoes the deselection of a note by reselecting it.
     */
    @Override
    public void undo() {
        CompositionManager.getInstance().selectGroupable(note);
    }
}
