/*
 * File: InstrumentPaneController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 5
 * Due Date: October 23, 2016
 */

package proj5BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import proj5BeckChanceRemondiSalerno.CompositionManager;


/**
 * This class models a controller for the instrument pane.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class InstrumentPaneController {

    /**
     * The toggle group for the intruments.
     */
    @FXML
    private ToggleGroup instrumentGroup;

    public void initialize(){
        handleInstrumentChange();
    }

    /**
     * Changes the instrument that the future notes swill be played with
     */
    @FXML
    public void handleInstrumentChange() {
        CompositionManager.getInstance().setInstrumentIndex(instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle()));
    }
}
