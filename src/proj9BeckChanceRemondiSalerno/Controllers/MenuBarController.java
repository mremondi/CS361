/*
 * File: MenuBarController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */


package proj9BeckChanceRemondiSalerno.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import proj9BeckChanceRemondiSalerno.CompositionManager;

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
     * The save menu item
     */
    @FXML
    private MenuItem saveItem;

    /**
     * The save as menu item
     */
    @FXML
    private MenuItem saveAsItem;

    /**
     * The zoom in menu item
     */
    @FXML
    private MenuItem zoomInItem;

    /**
     * The zoom out menu item
     */
    @FXML
    private MenuItem zoomOutItem;


    /**
     * The composition manager for forwarded menu actions to
     */
    private CompositionManager compositionManager;

    /**
     * The compositon container controller
     */
    private CompositionContainerController compositionContainerController;

    /**
     * Setter for the composition manager
     *
     * @param compositionManager the new composition manager
     */
    public void setCompositionManager(CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.bindWithCompositionManager(compositionManager);
    }

    /**
     * Setter for the compositionContainerController
     *
     * @param compositionContainerController the new container controller
     */
    public void setCompositionContainerController(CompositionContainerController
                                                          compositionContainerController) {
        this.compositionContainerController = compositionContainerController;
        zoomInItem.disableProperty().bind(compositionContainerController.canZoomInProperty().not());
        zoomOutItem.disableProperty().bind(compositionContainerController.canZoomOutProperty().not());
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
     *
     * @param event
     */
    @FXML
    protected void handleGroup(ActionEvent event) {
        compositionManager.createNoteGroupWithSelectedNotes();
    }

    /**
     * Ungroups notes.
     *
     * @param event
     */
    @FXML
    protected void handleUngroup(ActionEvent event) {
        compositionManager.ungroupSelectedGroup();
    }

    /**
     * Redoes the last action
     *
     * @param event
     */
    @FXML
    protected void handleRedo(ActionEvent event) {
        compositionManager.redoLastUndoneAction();
    }

    /**
     * Undoes the last action
     *
     * @param event
     */
    @FXML
    protected void handleUndo(ActionEvent event) {
        compositionManager.undoLastAction();
    }

    /**
     * Cuts selected notes
     *
     * @param event
     */
    @FXML
    protected void handleCut(ActionEvent event) {
        compositionManager.cutSelectedNotes();
    }

    /**
     * Copies selected notes
     *
     * @param event
     */
    @FXML
    protected void handleCopy(ActionEvent event) {
        compositionManager.copySelectedNotes();
    }

    /**
     * Pastes notes from clipboard
     *
     * @param event
     */
    @FXML
    protected void handlePaste(ActionEvent event) {
        compositionManager.pasteNotes();
    }

    /**
     * Saves the current composition
     *
     * @param event
     */
    @FXML
    protected void handleSave(ActionEvent event) {
        compositionManager.saveComposition();
    }

    /**
     * Saves the current composition in a new file
     *
     * @param event
     */
    @FXML
    protected void handleSaveAs(ActionEvent event) {
        compositionManager.saveCompositionAsNew();
    }

    /**
     * Opens a composition from a file
     *
     * @param event
     */
    @FXML
    protected void handleOpen(ActionEvent event) {
        compositionManager.openComposition();
    }

    /**
     * creates a new composition
     *
     * @param event
     */
    @FXML
    protected void handleNew(ActionEvent event) {
        compositionManager.createNewComposition();
    }

    @FXML
    protected void handleLoadLSystem(ActionEvent event){
        String filename = "systemH.txt";
        compositionManager.loadLSystem(filename);
    }

    /**
     * Shows the about dialog
     *
     * @param event
     */
    @FXML
    protected void handleAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MIDI Composer");
        alert.setHeaderText("MIDI Composer");
        alert.setContentText("\tAuthors\n\n\tCharlie Beck\n\tGraham Chance\n\tMike " +
                "Remondi\n\tRyan Salerno");
        alert.showAndWait();
    }

    /**
     * Zooms the entire composition in
     *
     * @param event
     */
    @FXML
    protected void handleZoomIn(ActionEvent event) {
        compositionContainerController.zoomIn();
    }

    /**
     * Zooms the entire composition out
     *
     * @param event
     */
    @FXML
    protected void handleZoomOut(ActionEvent event) {
        compositionContainerController.zoomOut();
    }

    /**
     * Binds menu item disabled states with properties
     *
     * @param compositionManager The composition manager to use for binding
     */
    private void bindWithCompositionManager(CompositionManager compositionManager) {
        deleteItem.disableProperty().bind(compositionManager
                .getIsNothingSelectedProperty());
        groupItem.disableProperty().bind(compositionManager.getCannotGroupProperty());
        ungroupItem.disableProperty().bind(compositionManager.getCannotUngroupProperty());
        selectAllItem.disableProperty().bind(compositionManager.getNotesEmptyProperty());
        playItem.disableProperty().bind(compositionManager.getNotesEmptyProperty());
        copyItem.disableProperty().bind(compositionManager.
                getIsNothingSelectedProperty());
        cutItem.disableProperty().bind(compositionManager.getIsNothingSelectedProperty());
        pasteItem.disableProperty().bind(compositionManager.getClipboardEmptyProperty());
        undoItem.disableProperty().bind(compositionManager.getUndoEmptyProperty());
        redoItem.disableProperty().bind(compositionManager.getRedoEmptyProperty());
    }
}
