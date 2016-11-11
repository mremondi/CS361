package proj7BeckChanceRemondiSalerno.Models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Created by Graham on 11/10/16.
 */

@XmlRootElement(name="composition")
@XmlAccessorType(XmlAccessType.FIELD)
public class Composition {

    @XmlElements({
            @XmlElement(name="note", type=Note.class),
            @XmlElement(name="group", type=NoteGroup.class)
    })
    ArrayList<NoteGroupable> notes;

    public Composition(ArrayList<NoteGroupable> notes) {
        this.notes = notes;
    }

    public Composition() {

    }

}
