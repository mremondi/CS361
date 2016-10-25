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
