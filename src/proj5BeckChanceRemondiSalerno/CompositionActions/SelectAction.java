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

import java.util.ArrayList;

/**
 * This class implements the CompositionAction interface and represents the action
 * of selecting a note in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class SelectAction implements CompositionAction {
    /**
     * The ArrayList of selected notes
     */
    private ArrayList<NoteGroupable> selected;

    /**
     * Constructor
     *
     * @param selected the selected notes
     */
    public SelectAction(ArrayList<NoteGroupable> selected) {
        this.selected = selected;
    }

    /**
     * Constructor
     *
     * @param note the selected note
     */
    public SelectAction(NoteGroupable note) {
        this.selected = new ArrayList<>();
        this.selected.add(note);
    }

    /**
     * Redoes the selection of notes
     */
    @Override
    public void redo() {
        for (NoteGroupable note : selected) {
            CompositionManager.getInstance().selectGroupable(note);
        }
    }

    /**
     * Undoes the selection of notes
     */
    @Override
    public void undo() {
        for (NoteGroupable note : selected) {
            CompositionManager.getInstance().deselectNote(note);
        }
    }
}
