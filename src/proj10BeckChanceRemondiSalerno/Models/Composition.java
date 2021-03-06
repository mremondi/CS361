/*
 * File: Composition.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */

package proj10BeckChanceRemondiSalerno.Models;

import proj10BeckChanceRemondiSalerno.NoteGroupableComparator;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * This class models a composition containing notes
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
@XmlRootElement(name = "composition")
@XmlAccessorType(XmlAccessType.FIELD)
public class Composition {

    /**
     * The notes in the composition
     */
    @XmlElements({
            @XmlElement(name = "note", type = Note.class),
            @XmlElement(name = "group", type = NoteGroup.class)
    })
    private ArrayList<NoteGroupable> notes;

    /**
     * Constructor
     *
     * @param notes Notes to be in the composition
     */
    public Composition(ArrayList<NoteGroupable> notes) {
        this.notes = notes;
        this.notes.sort(new NoteGroupableComparator());
        for(NoteGroupable noteGroupable: notes) {
            noteGroupable.sortNotes();
        }
    }

    /**
     * Empty Constructor
     */
    public Composition() {
    }

    /**
     * Getter for the notes in the composition
     *
     * @return The notes in the composition
     */
    public ArrayList<NoteGroupable> getNotes() {
        return notes;
    }


}
