package proj5BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import proj5BeckChanceRemondiSalerno.CompositionManager;

/**
 * Created by mremondi on 10/21/16.
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
