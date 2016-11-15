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
import javafx.scene.control.Alert;
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

    @FXML
    private MenuItem saveItem;

    @FXML
    private MenuItem saveAsItem;

    @FXML
    private MenuItem newItem;


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
        this.bindWithCompositionManager(compositionManager);
    }

    /**
     * Sets all of the notes to be selected and adds them to the selected list.
     */
    public void handleSelectAll() {
        compositionManager.selectAllNotes();
    }

    /**
     * Deletes the selected notes from the composition panel
     */
    @FXML
    public void handleDelete() {
        compositionManager.deleteSelectedGroupables();
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
        if (compositionManager.canDiscardComposition()) {
            System.exit(0);
        }
    }

    /**
     * Creates a new note group
     * @param event
     */
    @FXML
    protected void handleGroup(ActionEvent event) {
        compositionManager.createNoteGroupWithSelectedNotes();
    }

    /**
     * Ungroups notes.
     * @param event
     */
    @FXML
    protected void handleUngroup(ActionEvent event) {
        compositionManager.ungroupSelectedGroup();
    }

    /**
     * Redoes the last action
     * @param event
     */
    @FXML protected  void handleRedo(ActionEvent event) {
        compositionManager.redoLastUndoneAction();
    }

    /**
     * Undoes the last action
     * @param event
     */
    @FXML protected  void handleUndo(ActionEvent event) {
        compositionManager.undoLastAction();
    }

    /**
     * Cuts selected notes
     * @param event
     */
    @FXML protected  void handleCut(ActionEvent event) {
        compositionManager.cutSelectedNotes();
    }

    /**
     * Copies selected notes
     * @param event
     */
    @FXML protected  void handleCopy(ActionEvent event) {
        compositionManager.copySelectedNotes();
    }

    /**
     * Pastes notes from clipboard
     * @param event
     */
    @FXML protected  void handlePaste(ActionEvent event) {
        compositionManager.pasteNotes();
    }

    /**
     * Saves the current composition
     * @param event
     */
    @FXML protected  void handleSave(ActionEvent event) {
        compositionManager.saveComposition();
    }

    /**
     * Saves the current composition in a new file
     * @param event
     */
    @FXML protected  void handleSaveAs(ActionEvent event) {
        compositionManager.saveCompositionAsNew();
    }

    /**
     * Opens a composition from a file
     * @param event
     */
    @FXML protected  void handleOpen(ActionEvent event) {
        compositionManager.openComposition();
    }

    /**
     * creates a new composition
     * @param event
     */
    @FXML protected  void handleNew(ActionEvent event) {
        compositionManager.createNewComposition();
    }

    @FXML protected void handleAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CS361 Project 8");
        alert.setHeaderText(null);
        alert.setContentText("Authors: Charlie Beck, Graham Chance, Mike Remondi, & Ryan Salerno");
        alert.showAndWait();
    }


    /**
     * Binds menu item disabled states with properties
     * @param compositionManager The composition manager to use for binding
     */
    private void bindWithCompositionManager(CompositionManager compositionManager) {
        deleteItem.disableProperty().bind(compositionManager.getIsNothingSelectedProperty());
        groupItem.disableProperty().bind(compositionManager.getCannotGroupProperty());
        ungroupItem.disableProperty().bind(compositionManager.getCannotUngroupProperty());
        selectAllItem.disableProperty().bind(compositionManager.getNotesEmptyProperty());
        playItem.disableProperty().bind(compositionManager.getNotesEmptyProperty());
        copyItem.disableProperty().bind(compositionManager.getIsNothingSelectedProperty());
        cutItem.disableProperty().bind(compositionManager.getIsNothingSelectedProperty());
        pasteItem.disableProperty().bind(compositionManager.getClipboardEmptyProperty());
        undoItem.disableProperty().bind(compositionManager.getUndoEmptyProperty());
        redoItem.disableProperty().bind(compositionManager.getRedoEmptyProperty());
        saveItem.disableProperty().bind(compositionManager.getNotesEmptyProperty());
        saveAsItem.disableProperty().bind(compositionManager.getNotesEmptyProperty());
    }
}
