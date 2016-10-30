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
 * Created by Graham on 10/27/16.
 */
public class SelectAction implements CompositionAction {


    private ArrayList<NoteGroupable> selected;

    public SelectAction(ArrayList<NoteGroupable> selected) {
        this.selected = selected;
    }

    public SelectAction(NoteGroupable note) {
        this.selected = new ArrayList<>();
        this.selected.add(note);
    }


    @Override
    public void redo() {
        for (NoteGroupable note : selected) {
            CompositionManager.getInstance().selectGroupable(note);
        }
    }

    @Override
    public void undo() {
        for (NoteGroupable note : selected) {
            CompositionManager.getInstance().deselectNote(note);
        }
    }
}
