package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;

public class DeleteGroupableAction implements CompositionAction {


        private NoteGroupable groupable;

        public DeleteGroupableAction(NoteGroupable groupable) {
            this.groupable = groupable;
        }

        public void redo() {

        }

        public void undo() {

        }

}
