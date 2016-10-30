/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
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
     * The toggle group for the instruments.
     */
    @FXML
    private ToggleGroup instrumentGroup;

    /**
     * The standard method invoked when loading the controller. Calls
     * handleInstrumentChange to set an initial instrument to selected.
     */
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
