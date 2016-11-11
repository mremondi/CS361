package proj7BeckChanceRemondiSalerno;

import proj7BeckChanceRemondiSalerno.Models.Composition;
import proj7BeckChanceRemondiSalerno.Models.Note;
import proj7BeckChanceRemondiSalerno.Models.NoteGroup;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


/**
 * Created by Graham on 11/10/16.
 */
public class CompositionFileManager {

    public void saveComposition(Composition composition) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(Note.class,NoteGroup.class, Composition.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(composition, System.out);
//        StringWriter sw = new StringWriter();
//        JAXB.marshal(composition, sw);
//        System.out.println("--- start: output of marshalling ----");
//        System.out.println(sw.toString());
//        System.out.println("--- end: output of marshalling ----");
//        System.out.println();
//        System.out.println("--- start: output of unmarshalling ----");
//        Set<NoteGroupable> karlUnmarshalled = JAXB.unmarshal(new StringReader(sw.toString()), Set.class);
//        System.out.println(karlUnmarshalled.size());
    }


}
