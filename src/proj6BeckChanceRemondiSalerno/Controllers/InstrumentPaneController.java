/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */


package proj6BeckChanceRemondiSalerno.Controllers;

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
public class InstrumentPaneController implements InstrumentSource {

    /**
     * The toggle group for the instruments.
     */
    @FXML
    private ToggleGroup instrumentGroup;


    public int getCurrentInstrumentIndex() {
        return instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle());
    }
}
