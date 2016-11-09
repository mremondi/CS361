/*
 * File: CompositionActionManager.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj7BeckChanceRemondiSalerno;

import javafx.beans.property.SimpleBooleanProperty;
import proj7BeckChanceRemondiSalerno.CompositionActions.CompositionAction;

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
     * Adds the given CompositionAction to the undoActions stack and
     * empties the redo stack.
     *
     * @param action the CompositionAction that is completed.
     */
    public void actionCompleted(CompositionAction action) {
        undoActions.push(action);
        redoActions.removeAllElements();
    }

    /**
     * Undoes the CompositionAction at the top of the undo stack and pushes
     * it onto the redo stack.
     */
    public void undoLastAction() {
        CompositionAction action = undoActions.pop();
        action.undo();
        redoActions.push(action);
    }

    /**
     * Tells whether there are any actions in the Undo Stack.
     *
     * @return a boolean indicator
     */
    public SimpleBooleanProperty isUndoEmpty(){
        return new SimpleBooleanProperty(this.undoActions.empty());
    }

    /**
     * Tells whether there are any actions in the Redo Stack.
     *
     * @return a boolean indicator
     */
    public SimpleBooleanProperty isRedoEmpty(){
        return new SimpleBooleanProperty(this.redoActions.empty());
    }

    /**
     * Redoes the Composition at the top of the redo stack.
     */
    public void redoLastUndoneAction() {
        CompositionAction action = redoActions.pop();
        action.redo();
        undoActions.push(action);
    }
}
