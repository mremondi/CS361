/*
 * File: CompositionContextMenuController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */


package proj10BeckChanceRemondiSalerno.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import proj10BeckChanceRemondiSalerno.CompositionActions.ChangeInstrumentAction;
import proj10BeckChanceRemondiSalerno.CompositionActions.ChangeVolumeAction;
import proj10BeckChanceRemondiSalerno.CompositionManager;
import proj10BeckChanceRemondiSalerno.Models.Note;
import proj10BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


/**
 * This class models a controller for a CompositionContextMenu
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class CompositionContextMenuController {


    /**
     * The composition manager for performing actions
     */
    private CompositionManager compositionManager;

    /**
     * The context menu
     */
    private ContextMenu contextMenu;


    /**
     * Setter for composition managaer
     *
     * @param compositionManager The new composition manager
     */
    public void setCompositionManager(CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
    }

    /**
     * Handles delete clicked
     * Deletes selected notes
     *
     * @param event
     */
    @FXML
    public void handleDelete(ActionEvent event) {
        compositionManager.deleteSelectedGroupables();
    }

    /**
     * Handles play clicked
     * Plays selected notes
     *
     * @param event
     */
    @FXML
    public void handlePlay(ActionEvent event) {
        compositionManager.play(compositionManager.getSelectedNotes(), true);
    }

    /**
     * Handles set instrument clicked
     *
     * @param event
     */
    @FXML
    public void handleSetInstrument(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("../Views/InstrumentPane.fxml"));
            Parent instrumentsRoot = fxmlLoader.load();
            Alert alert = new Alert(Alert.AlertType.NONE);
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(instrumentsRoot);
            alert.setDialogPane(dialogPane);
            ButtonType confirmButton = new ButtonType("Ok", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData
                    .CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == confirmButton) {
                int selectedIndex = ((InstrumentPaneController) fxmlLoader
                        .getController()).getCurrentInstrumentIndex();
                ArrayList<NoteGroupable> notes = compositionManager
                        .getSelectedNotes();
                // action must be created before changing channels so it can save original channels
                ChangeInstrumentAction changeInstrumentAction = new ChangeInstrumentAction(notes, selectedIndex, compositionManager);
                compositionManager.actionCompleted(changeInstrumentAction);
                compositionManager.setChannelForNotes(notes, selectedIndex);

            }
        } catch (IOException e) {
            // Do nothing
        }
    }

    /**
     * Handles set volume clicked
     *
     * @param event
     */
    @FXML
    public void handleSetVolume(ActionEvent event) {
        ArrayList<NoteGroupable> noteGroupables = compositionManager.getSelectedNotes();
        int currentVolume = noteGroupables.size() == 1 && noteGroupables.get(0)
                instanceof Note ?
                ((Note) noteGroupables.get(0)).getVolume() : 100;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("../Views/Volume.fxml"));
            Parent instrumentsRoot = fxmlLoader.load();
            Alert alert = new Alert(Alert.AlertType.NONE);
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(instrumentsRoot);
            alert.setDialogPane(dialogPane);
            ButtonType confirmButton = new ButtonType("Ok", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData
                    .CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, buttonTypeCancel);
            VolumeController volumeController = fxmlLoader.getController();
            volumeController.setCurrentVolume(currentVolume);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == confirmButton) {
                int newVolume = volumeController.getVolume();
                // action must be made before actually changing volume so original volumes can be saved
                ChangeVolumeAction volumeAction = new ChangeVolumeAction(noteGroupables, newVolume, compositionManager);
                compositionManager.actionCompleted(volumeAction);
                for (NoteGroupable noteGroupable : noteGroupables) {
                    noteGroupable.setVolume(newVolume);
                }
            }
        } catch (IOException e) {
            // Do nothing
        }
    }

}
