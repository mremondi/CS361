package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroup;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

public class DeleteGroupablesAction implements CompositionAction {


        private ArrayList<NoteGroupable> groupables;

        public DeleteGroupablesAction(ArrayList<NoteGroupable> groupables) {
            this.groupables = groupables;
        }

        public void redo() {
                CompositionManager.getInstance().deleteGroupables(groupables);
        }

        public void undo() {
                for (NoteGroupable groupable : groupables) {
                        CompositionManager.getInstance().addGroupable(groupable);
                }
        }

}
