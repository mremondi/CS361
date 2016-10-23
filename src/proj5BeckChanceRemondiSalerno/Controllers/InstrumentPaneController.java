package proj5BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
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
        CompositionManager.getInstance().changeInstrument(instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle()));
    }
}
