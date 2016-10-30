/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj5BeckChanceRemondiSalerno.CompositionActions;


import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.Note;

public class AddNoteAction implements CompositionAction {

    private Note note;

    public AddNoteAction(Note note) {
        this.note = note;
    }

    public void redo() {
        CompositionManager.getInstance().addGroupable(note);
    }

    public void undo() {
        CompositionManager.getInstance().deleteGroupable(note);
    }

}
