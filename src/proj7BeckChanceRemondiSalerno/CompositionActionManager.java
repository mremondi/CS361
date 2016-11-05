/*
 * File: CompositionActionManager.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj7BeckChanceRemondiSalerno;

import proj7BeckChanceRemondiSalerno.CompositionActions.CompositionAction;
import proj7BeckChanceRemondiSalerno.Controllers.MenuBarController;
import java.util.Stack;

/**
 * This models an object that can manage redo and undo actions
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
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
        undoActions.push(action);
        redoActions.removeAllElements();
        updateRedoUndoDisabled();
        //System.out.println("completed " + action.getClass());
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
        //System.out.println("undoing " + action.getClass());
    }

    /**
     * Redoes the Composition at the top of the redo stack.
     */
    public void redoLastUndoneAction() {
        CompositionAction action = redoActions.pop();
        action.redo();
        undoActions.push(action);
        updateRedoUndoDisabled();
        //System.out.println("redoing " + action.getClass());
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
