/*
 * File: CompositionContextMenuController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */


package proj8BeckChanceRemondiSalerno.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import proj8BeckChanceRemondiSalerno.CompositionManager;

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
     * @param compositionManager The new composition manager
     */
    public void setCompositionManager(CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
    }


    /**
     * Handles delete clicked
     * Deletes selected notes
     * @param event
     */
    @FXML
    public void handleDelete(ActionEvent event) {
        compositionManager.deleteSelectedGroupables();
    }

    /**
     * Handles play clicked
     * Plays selected notes
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

    }

    /**
     * Handles set volume clicked
     * @param event
     */
    @FXML
    public void handleSetVolume(ActionEvent event) {

    }

}
