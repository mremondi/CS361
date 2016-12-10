package proj9BeckChanceRemondiSalerno.LSystem;

import javafx.stage.FileChooser;
import proj9BeckChanceRemondiSalerno.Main;

import java.io.File;

/**
 * Created by mremondi on 12/10/16.
 */
public class LSystemFileManager {

    private final FileChooser fileChooser = new FileChooser();
    public LSystemFileManager(){

    }

    public String selectFile(){
        File selectedFile = this.fileChooser.showOpenDialog(Main.getPrimaryStage());
        return selectedFile.getAbsolutePath();
    }
}
