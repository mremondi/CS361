package proj7BeckChanceRemondiSalerno.XMLAdapters;

import proj7BeckChanceRemondiSalerno.Models.NoteGroupable;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Graham on 11/10/16.
 */
public class NoteGroupableAdapter extends XmlAdapter<Object, NoteGroupable> {
    @Override
    public NoteGroupable unmarshal(Object v) throws Exception {
        System.out.println("USING \n\n\n");
        return (NoteGroupable)v;
    }

    @Override
    public Object marshal(NoteGroupable v) throws Exception {
        return v;
    }

}
