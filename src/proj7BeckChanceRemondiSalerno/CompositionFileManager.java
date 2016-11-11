/*
 * File: CompositionFileManager.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */

package proj7BeckChanceRemondiSalerno;

import javafx.stage.FileChooser;
import proj7BeckChanceRemondiSalerno.Models.Composition;
import proj7BeckChanceRemondiSalerno.Models.Note;
import proj7BeckChanceRemondiSalerno.Models.NoteGroup;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Optional;



/**
 * This class models an object that can save and load compositions from files
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class CompositionFileManager {

    private JAXBContext jc;
    private final FileChooser fileChooser = new FileChooser();

    /**
     * Constructor
     */
    public CompositionFileManager() {
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("xml files (*.xml)", "xml");
        fileChooser.getExtensionFilters().add(fileExtensions);
        try {
            jc = JAXBContext.newInstance(Note.class,NoteGroup.class, Composition.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Saves a composition to a file of the user's choice
     * @param composition The composition to save
     * @throws Exception Thrown if saving fails
     */
    public void saveComposition(Composition composition) throws Exception {
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = fileChooser.showSaveDialog(null);
        marshaller.marshal(composition, file);
    }

    /**
     * Loads a composition from a file of the user's choice
     * @return The loaded composition
     * @throws Exception Thrown if loading fails
     */
    public Optional<Composition> openComposition() throws Exception {
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile!=null) {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Composition composition = (Composition)unmarshaller.unmarshal(selectedFile);
            return Optional.of(composition);
        }
        return Optional.empty();
    }

}
