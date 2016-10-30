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


public class ResizeAction implements CompositionAction {

    private ArrayList<NoteGroupable> resizedNotes;
    private double dx;


    public ResizeAction(ArrayList<NoteGroupable> resizedNotes, double dx) {
        this.resizedNotes = resizedNotes;
        this.dx = dx;
    }

    public void redo() {
        CompositionManager.getInstance().resizeNotes(resizedNotes, dx);
    }

    public void undo() {
        CompositionManager.getInstance().resizeNotes(resizedNotes, -dx);
    }

}
