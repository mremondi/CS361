/*
 * File: DeselectAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj8BeckChanceRemondiSalerno.CompositionActions;

import proj8BeckChanceRemondiSalerno.CompositionManager;
import proj8BeckChanceRemondiSalerno.Models.NoteGroupable;

/**
 * This class implements the CompositionAction interface and represents the action
 * of deselecting a note in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class DeselectAction extends CompositionAction {
    /**
     * The note to deselect.
     */
    private NoteGroupable note;

    /**
     * Constructor
     *
     * @param note the note to deselect
     */
    public DeselectAction(NoteGroupable note, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.note = note;
    }

    /**
     * Redoes the deselection of a note.
     */
    public void redo() {
        compositionManager.deselectNote(note);
    }

    /**
     * Undoes the deselection of a note by reselecting it.
     */
    public void undo() {
        compositionManager.selectGroupable(note);
    }
}
