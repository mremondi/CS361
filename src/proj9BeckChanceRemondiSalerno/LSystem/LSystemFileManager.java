package proj9BeckChanceRemondiSalerno.LSystem;

import javafx.stage.FileChooser;
import proj9BeckChanceRemondiSalerno.Main;

import java.io.File;

/**
 * Class to allow the user to select a file
 */
public class LSystemFileManager {

    /**
     * A file chooser object for allowing the user to open a file
     */
    private final FileChooser fileChooser = new FileChooser();

    /**
     * Constructor
     */
    public LSystemFileManager(){}

    /**
     * Allows the user to open a file
     * @return the path to the selected file
     */
    public String selectFile(){
        File selectedFile = this.fileChooser.showOpenDialog(Main.getPrimaryStage());
        return selectedFile.getAbsolutePath();
    }
}
