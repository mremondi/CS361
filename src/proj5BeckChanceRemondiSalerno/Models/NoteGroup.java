package proj5BeckChanceRemondiSalerno.Models;

import java.util.ArrayList;

/**
 * Created by mremondi on 10/20/16.
 */
public class NoteGroup implements Groupable {

    ArrayList<Groupable> groups;

    public NoteGroup(){

    }

    @Override
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for (Groupable group : groups) {
            for (Note note : group.getNotes()) {
                notes.add(note);
            }
        }
        return notes;
    }
}
