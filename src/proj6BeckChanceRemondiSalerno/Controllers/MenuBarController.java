/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */


package proj6BeckChanceRemondiSalerno.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import proj6BeckChanceRemondiSalerno.CompositionManager;
import proj6BeckChanceRemondiSalerno.Models.NoteGroup;
import proj6BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by mremondi on 10/21/16.
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
     * The play menu item
     */
    @FXML
    private MenuItem selectAllItem;

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
        this.compositionManager.getCompositionActionManager().setMenuBarController(this);
    }

    /**
     * Enables/disables the redo menu item.
     *
     * @param disabled a boolean indicator of whether it should be disabled
     */
    public void setRedoDisabled(boolean disabled) {
        redoItem.setDisable(disabled);
    }

    /**
     * Enables/disables the undo menu item.
     *
     * @param disabled a boolean indicator of whether it should be disabled
     */
    public void setUndoDisabled(boolean disabled) {
        undoItem.setDisable(disabled);
        updateEnabledMenuItems();
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
        compositionManager.getCompositionActionManager().redoLastUndoneAction();
        updateEnabledMenuItems();
    }

    /**
     * Unodes the last action
     * @param event
     */
    @FXML protected  void handleUndo(ActionEvent event) {
        compositionManager.getCompositionActionManager().undoLastAction();
        updateEnabledMenuItems();
    }

    /**
     * Updates the disabled state of relevant menu items
     */
    public void updateEnabledMenuItems() {
        ArrayList<NoteGroupable> selectedNotes = new ArrayList<>();
        Set<NoteGroupable> allNotes = compositionManager.getGroupables();
        for (NoteGroupable note : allNotes) {
            if (note.isSelected()) {
                selectedNotes.add(note);
            }
        }

        deleteItem.setDisable(selectedNotes.isEmpty());
        groupItem.setDisable(selectedNotes.size() < 2);
        ungroupItem.setDisable(!(selectedNotes.size() == 1  && selectedNotes.get(0) instanceof NoteGroup));
        selectAllItem.setDisable(allNotes.isEmpty());
        playItem.setDisable(allNotes.isEmpty());
    }

}
