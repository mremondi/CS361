package proj6BeckChanceRemondiSalerno;

import proj6BeckChanceRemondiSalerno.CompositionActions.CompositionAction;
import proj6BeckChanceRemondiSalerno.Controllers.MenuBarController;
import java.util.Stack;

/**
 * Created by Graham on 10/30/16.
 */
public class CompositionActionManager {

    /**
     * The stack for remembering each CompositionAction for redoing.
     */
    private Stack<CompositionAction> redoActions = new Stack<>();

    /**
     * The stack for remembering each CompositionAction for undoing.
     */
    private  Stack<CompositionAction> undoActions = new Stack<>();

    /**
     * The controller for the MenuBar
     */
    private MenuBarController menuBarController;

    /**
     * Setter for the menuBarController
     * @param menuBarController
     */
    public void setMenuBarController(MenuBarController menuBarController) {
        this.menuBarController = menuBarController;
    }

    /**
     * Adds the given CompositionAction to the undoActions stack and
     * empties the redo stack.
     *
     * @param action the CompositionAction that is completed.
     */
    public void actionCompleted(CompositionAction action) {
        System.out.println("Adding action: " + action.getClass());
        undoActions.push(action);
        redoActions.removeAllElements();
        updateRedoUndoDisabled();
    }

    /**
     * Undoes the CompositionAction at the top of the undo stack and pushes
     * it onto the redo stack.
     */
    public void undoLastAction() {
        CompositionAction action = undoActions.pop();
        action.undo();
        redoActions.push(action);
        updateRedoUndoDisabled();
    }

    /**
     * Redoes the Composition at the top of the redo stack.
     */
    public void redoLastUndoneAction() {
        CompositionAction action = redoActions.pop();
        action.redo();
        undoActions.push(action);
        updateRedoUndoDisabled();
    }

    /**
     * Sets the menu options for redo/undo to disabled if there is nothing
     * in the respective stack.
     */
    private void updateRedoUndoDisabled() {
        menuBarController.setRedoDisabled(redoActions.empty());
        menuBarController.setUndoDisabled(undoActions.empty());
    }
}
