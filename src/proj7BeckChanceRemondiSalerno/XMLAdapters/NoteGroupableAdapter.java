/*
 * File: NoteGroupableAdapter.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */

package proj7BeckChanceRemondiSalerno.XMLAdapters;

import proj7BeckChanceRemondiSalerno.Models.NoteGroupable;

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
    @Override
    public NoteGroupable unmarshal(Object v) throws Exception {
        return (NoteGroupable)v;
    }

    @Override
    public Object marshal(NoteGroupable v) throws Exception {
        return v;
    }

}
