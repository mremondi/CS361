/**
 * LSystemOptionsController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj9BeckChanceRemondiSalerno.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * This class models a controller for the LSystemOptions view
 */
public class LSystemOptionsController {

    /**
     * Input text field for the number of iterations
     */
    @FXML
    TextField iterations;

    /**
     * Input text field for the default duration
     */
    @FXML
    TextField duration;

    /**
     * Input text field for the starting pitch
     */
    @FXML
    TextField startPitch;

    /**
     * The default initializer method called by the FXML loader
     */
    public void initialize() {

    }

    /**
     * Getter method for the number of iterations
     *
     * @return the number of iterations
     */
    public int getIterations() {
        return Integer.valueOf(iterations.getText());
    }

    /**
     * Getter method for the default duration
     *
     * @return the default duration
     */
    public int getDuration() {
        return Integer.valueOf(duration.getText());
    }

    /**
     * Getter method for the starting pitch
     *
     * @return the starting pitch
     */
    public int getStartPitch() {
        return Integer.valueOf(startPitch.getText());
    }
}
