package proj9BeckChanceRemondiSalerno.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LSystemOptionsController {

    @FXML
    TextField iterations;

    @FXML
    TextField duration;

    @FXML
    TextField startPitch;

    /**
     * The default initializer method called by the FXML loader
     */
    public void initialize() {

    }

    public int getIterations() {
        return Integer.valueOf(iterations.getText());
    }

    public int getDuration() {
        return Integer.valueOf(duration.getText());
    }

    public int getStartPitch() {
        return Integer.valueOf(startPitch.getText());
    }
}
