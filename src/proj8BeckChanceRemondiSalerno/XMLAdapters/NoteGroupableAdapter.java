/*
 * File: NoteGroupableAdapter.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */

package proj8BeckChanceRemondiSalerno.XMLAdapters;

import proj8BeckChanceRemondiSalerno.Models.NoteGroupable;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This class models an object that can marshal and unmarshal NoteGroupables
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class NoteGroupableAdapter extends XmlAdapter<Object, NoteGroupable> {

    /**
     * Converts our data object to something that is marshalable.
     *
     * @param v a data object
     * @return the data object casted to a NoteGroupable
     * @throws Exception
     */
    @Override
    public NoteGroupable unmarshal(Object v) throws Exception {
        return (NoteGroupable) v;
    }

    /**
     * Preparing the NoteGroupable to be saved to a file
     *
     * @param v the NoteGroupable
     * @return an Object representation of the NoteGroupable
     * @throws Exception
     */
    @Override
    public Object marshal(NoteGroupable v) throws Exception {
        return v;
    }
}
