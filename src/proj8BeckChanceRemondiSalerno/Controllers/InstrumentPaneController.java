/*
 * File: InstrumentPaneController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */


package proj8BeckChanceRemondiSalerno.Controllers;

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


    public int getCurrentInstrumentIndex() {
        return instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle());
    }
}
