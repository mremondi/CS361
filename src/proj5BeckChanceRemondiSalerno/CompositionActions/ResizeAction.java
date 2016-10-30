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
 * of resizing notes in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class ResizeAction implements CompositionAction {
    /**
     * The ArrayList of resized notes.
     */
    private ArrayList<NoteGroupable> resizedNotes;

    /**
     * The change in the x direction.
     */
    private double dx;

    /**
     * Constructor
     *
     * @param resizedNotes the notes that were resized
     * @param dx the change in the x direction
     */
    public ResizeAction(ArrayList<NoteGroupable> resizedNotes, double dx) {
        this.resizedNotes = resizedNotes;
        this.dx = dx;
    }

    /**
     * Redoes the resizing of notes.
     */
    public void redo() {
        CompositionManager.getInstance().resizeNotes(resizedNotes, dx);
    }

    /**
     * Undoes the resizing of notes.
     */
    public void undo() {
        CompositionManager.getInstance().resizeNotes(resizedNotes, -dx);
    }

}
