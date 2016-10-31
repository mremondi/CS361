/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj6BeckChanceRemondiSalerno.CompositionActions;

import proj6BeckChanceRemondiSalerno.CompositionManager;

/**
 * This interface is implemented to allow for the undoing/redoing of actions.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public abstract class CompositionAction {

    CompositionManager compositionManager;

    /**
     * The undo method to be implemented by each inheriting class. The undo
     * implementation should completely undo the most recent change to the
     * state of the Composition.
     */
    public abstract void undo();

    /**
     * The redo method to be implemented by each inheriting class. The redo
     * implementation should completely redo the most recent change to the
     * state of the Composition.
     */
    public abstract void redo();

}
