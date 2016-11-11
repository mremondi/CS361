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
 * Created by Graham on 11/10/16.
 */
public class CompositionFileManager {

    private JAXBContext jc;
    private final FileChooser fileChooser = new FileChooser();


    public CompositionFileManager() {
        try {
            jc = JAXBContext.newInstance(Note.class,NoteGroup.class, Composition.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void saveComposition(Composition composition) throws Exception {
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = fileChooser.showSaveDialog(null);
        marshaller.marshal(composition, file);
    }

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
