package proj5BeckChanceRemondiSalerno.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.Groupable;
import proj5BeckChanceRemondiSalerno.Models.Note;
import proj5BeckChanceRemondiSalerno.Views.NoteRectangle;

import java.util.ArrayList;

/**
 * Created by mremondi on 10/21/16.
 */
public class MenuBarController {

    /**
     * Sets all of the notes to be selected and adds them to the selected list.
     */
    public void handleSelectAll() {
        selectAllNotes();
    }

    /**
     * Selects all of the notes and adds them to the selected arraylist.
     */
    public void selectAllNotes() {
        CompositionManager.getInstance().clearSelectedNotes();
        for (Groupable groupable : CompositionManager.getInstance().getNotes()) {
            CompositionManager.getInstance().selectGroupable(groupable);
        }
    }

    /**
     * Deletes the selected notes from the composition panel
     */
    @FXML
    public void handleDelete() {
        this.deleteNotes();
    }


    /**
     * Deletes all the selected notes from the composition pane
     */
    public void deleteNotes() {
        CompositionManager.getInstance().deleteSelectedGroups();
    }


    /**
     * Plays the sounds displayed in the composition.
     */
    @FXML
    protected void handlePlayMidi() {
        CompositionManager.getInstance().play();
    }

    /**
     * Stops the reproduction of the composition
     */
    @FXML
    protected void handleStopMusic() {
        CompositionManager.getInstance().stop();
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

    @FXML
    protected void handleGroup(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    protected void handleUngroup(ActionEvent event) {
        System.exit(0);
    }

}
