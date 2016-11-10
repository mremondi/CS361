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
    private Stack<CompositionAction> undoActions = new Stack<>();

    /**
     * Observable property for binding to the undo menu item
     */
    private SimpleBooleanProperty undoEmptyProperty = new SimpleBooleanProperty();

    /**
     * Observable property for binding to the redo menu item
     */
    private SimpleBooleanProperty redoEmptyProperty = new SimpleBooleanProperty();

    /**
     * Constructor
     */
    public CompositionActionManager() {
        undoEmptyProperty.set(true);
        redoEmptyProperty.set(true);
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
    }

    /**
     * Undoes the CompositionAction at the top of the undo stack and pushes
     * it onto the redo stack.
     */
    public void undoLastAction() {
        CompositionAction action = undoActions.pop();
        action.undo();
        redoActions.push(action);
        updateEmptyProperties();
    }

    /**
     * Redoes the Composition at the top of the redo stack.
     */
    public void redoLastUndoneAction() {
        CompositionAction action = redoActions.pop();
        action.redo();
        undoActions.push(action);
        updateEmptyProperties();
    }

    /**
     * Updates the Observables if the stack is empty
     */
    private void updateEmptyProperties() {
        redoEmptyProperty.set(redoActions.isEmpty());
        undoEmptyProperty.set(undoActions.isEmpty());
    }

    /**
     * Gets the Undo Observable Property
     *
     * @return a SimpleBooleanProperty
     */
    public SimpleBooleanProperty getUndoEmptyProperty() {
        return undoEmptyProperty;
    }

    public SimpleBooleanProperty getRedoEmptyProperty() {
        return redoEmptyProperty;
    }
}