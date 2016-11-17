/*
 * File: SelectAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */

package proj8BeckChanceRemondiSalerno.CompositionActions;

import proj8BeckChanceRemondiSalerno.CompositionManager;
import proj8BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class extends the CompositionAction abstract class and represents the action
 * of selecting a note in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class SelectAction extends CompositionAction {
    /**
     * The ArrayList of selected notes
     */
    private Collection<NoteGroupable> selected;

    /**
     * Constructor
     *
     * @param selected the selected notes
     */
    public SelectAction(Collection<NoteGroupable> selected, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.selected = selected;
    }

    /**
     * Constructor
     *
     * @param note the selected note
     */
    public SelectAction(NoteGroupable note, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.selected = new ArrayList<>();
        this.selected.add(note);
    }

    /**
     * Redoes the selection of notes
     */
    @Override
    public void redo() {
        for (NoteGroupable note : selected) {
            compositionManager.selectGroupable(note);
        }
    }

    /**
     * Undoes the selection of notes
     */
    @Override
    public void undo() {
        for (NoteGroupable note : selected) {
            compositionManager.deselectNote(note);
        }
    }
}
