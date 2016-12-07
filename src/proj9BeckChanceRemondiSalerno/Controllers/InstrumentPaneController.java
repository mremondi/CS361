/*
 * File: InstrumentPaneController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */


package proj9BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

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
     * Getter method for the currently selected instrument
     *
     * @return an int index corresponding to a given instrument
     */
    public int getCurrentInstrumentIndex() {
        return instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle());
    }
}
