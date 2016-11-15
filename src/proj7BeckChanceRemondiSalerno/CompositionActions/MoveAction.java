/*
 * File: MoveAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj7BeckChanceRemondiSalerno.CompositionActions;

import proj7BeckChanceRemondiSalerno.CompositionManager;
import proj7BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * This class implements the CompositionAction interface and represents the action
 * of moving notes in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class MoveAction extends CompositionAction {
    /**
     * The moved notes
     */
    private ArrayList<NoteGroupable> movedNotes;

    /**
     * The distance in the x direction
     */
    private double dx;

    /**
     * The distance in the y direction
     */
    private double dy;

    /**
     * Constructor
     *
     * @param movedNotes an ArrayList of moved notes
     * @param dx the change in the x direction
     * @param dy the change in the y direction
     */
    public MoveAction(ArrayList<NoteGroupable> movedNotes, double dx, double dy, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.movedNotes = movedNotes;
        this.dx = dx;
        if (dy % 10 < 5) {
            this.dy = dy - (dy % 10);
        } else{
            this.dy = dy - 10 - (dy % 10);
        }
    }

    /**
     * Redoes the moving of notes.
     */
    public void redo() {
        compositionManager.moveNotes(movedNotes, dx, dy);
    }

    /**
     * Undoes the moving of notes
     */
    public void undo() {
        compositionManager.moveNotes(movedNotes, -dx, -dy);
    }

}
