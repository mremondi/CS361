/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 7
 * Due Date: November 10, 2016
 */


package proj7BeckChanceRemondiSalerno.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import proj7BeckChanceRemondiSalerno.CompositionManager;

/**
 * This class models a controller for the menu bar.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class MenuBarController {
    /**
     * The redo menu item
     */
    @FXML
    private MenuItem redoItem;

    /**
     * The undo menu item
     */
    @FXML
    private MenuItem undoItem;

    /**
     * The group menu item
     */
    @FXML
    private MenuItem groupItem;

    /**
     * The ungroup menu item
     */
    @FXML
    private MenuItem ungroupItem;

    /**
     * The delete menu item
     */
    @FXML
    private MenuItem deleteItem;

    /**
     * The play menu item
     */
    @FXML
    private MenuItem playItem;

    /**
     * The select all menu item
     */
    @FXML
    private MenuItem selectAllItem;

    /**
     * The copy menu item
     */
    @FXML
    private MenuItem copyItem;

    /**
     * The cut menu item
     */
    @FXML
    private MenuItem cutItem;

    /**
     * The paste menu item
     */
    @FXML
    private MenuItem pasteItem;

    /**
     * The composition manager for forwarded menu actions to
     */
    CompositionManager compositionManager;

    /**
     * Setter for the composition manager
     * @param compositionManager the new composition manager
     */
    public void setCompositionManager(CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
    }

    /**
     * Sets all of the notes to be selected and adds them to the selected list.
     */
    public void handleSelectAll() {
        compositionManager.selectAllNotes();
        updateEnabledMenuItems();
    }

    /**
     * Deletes the selected notes from the composition panel
     */
    @FXML
    public void handleDelete() {
        compositionManager.deleteSelectedGroupables();
        updateEnabledMenuItems();
    }


    /**
     * Plays the sounds displayed in the composition.
     */
    @FXML
    protected void handlePlayMidi() {
        compositionManager.play();
    }

    /**
     * Stops the reproduction of the composition
     */
    @FXML
    protected void handleStopMusic() {
        compositionManager.stop();
    }

    /**
     * Safely exits the program without throwing an error
     *
     * @param event the event to trigger the exit.
     */
    @FXML
    protected void handleExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Creates a new note group
     * @param event
     */
    @FXML
    protected void handleGroup(ActionEvent event) {
        compositionManager.createNoteGroupWithSelectedNotes();
        updateEnabledMenuItems();
    }

    /**
     * Ungroups notes.
     * @param event
     */
    @FXML
    protected void handleUngroup(ActionEvent event) {
        compositionManager.ungroupSelectedGroup();
        updateEnabledMenuItems();
    }

    /**
     * Redoes the last action
     * @param event
     */
    @FXML protected  void handleRedo(ActionEvent event) {
        compositionManager.redoLastUndoneAction();
        updateEnabledMenuItems();
    }

    /**
     * Unodes the last action
     * @param event
     */
    @FXML protected  void handleUndo(ActionEvent event) {
        compositionManager.undoLastAction();
        updateEnabledMenuItems();
    }


    @FXML protected  void handleCut(ActionEvent event) {
        compositionManager.cutSelectedNotes();
        updateEnabledMenuItems();
    }


    @FXML protected  void handleCopy(ActionEvent event) {
        compositionManager.copySelectedNotes();
        updateEnabledMenuItems();
    }


    @FXML protected  void handlePaste(ActionEvent event) {
        compositionManager.pasteNotes();
        updateEnabledMenuItems();
    }

    /**
     * Updates the disabled state of relevant menu items
     */
    public void updateEnabledMenuItems() {
        deleteItem.disableProperty().bind(compositionManager.isSelectedNotesEmpty());
        groupItem.disableProperty().bind(compositionManager.isSelectedNotesGroupable());
        ungroupItem.disableProperty().bind(compositionManager.isSelectedNotesUngroupable());
        selectAllItem.disableProperty().bind(compositionManager.areThereNotes());
        playItem.disableProperty().bind(compositionManager.areThereNotes());
        copyItem.disableProperty().bind(compositionManager.isSelectedNotesEmpty());
        cutItem.disableProperty().bind(compositionManager.isSelectedNotesEmpty());
        pasteItem.disableProperty().bind(compositionManager.bindIsClipboardEmpty());
        undoItem.disableProperty().bind(compositionManager.isUndoEmpty());
        redoItem.disableProperty().bind(compositionManager.isRedoEmpty());
    }
}
