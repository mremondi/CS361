/*
 * File: Composition.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */

package proj8BeckChanceRemondiSalerno.Models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;


/**
 * This class models a composition containing notes
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
@XmlRootElement(name="composition")
@XmlAccessorType(XmlAccessType.FIELD)
public class Composition {

    /**
     * The notes in the composition
     */
    @XmlElements({
            @XmlElement(name="note", type=Note.class),
            @XmlElement(name="group", type=NoteGroup.class)
    })
    private ArrayList<NoteGroupable> notes;

    /**
     * Constructor
     * @param notes Notes to be in the composition
     */
    public Composition(ArrayList<NoteGroupable> notes) {
        this.notes = notes;
    }

    /**
     * Empty Constructor
     */
    public Composition() {}

    /**
     * Getter for the notes in the composition
     * @return The notes in the composition
     */
    public ArrayList<NoteGroupable> getNotes() {
        return notes;
    }
}
