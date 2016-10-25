package proj5BeckChanceRemondiSalerno.CompositionActions;


import proj5BeckChanceRemondiSalerno.Models.Note;

public class AddNoteAction implements CompositionAction {

    private Note note;

    public AddNoteAction(Note note) {
        this.note = note;
    }

    public void redo() {

    }

    public void undo() {

    }

}
