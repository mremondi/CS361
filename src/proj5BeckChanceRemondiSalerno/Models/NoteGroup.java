package proj5BeckChanceRemondiSalerno.Models;

import java.util.ArrayList;

/**
 * Created by mremondi on 10/20/16.
 */
public class NoteGroup implements Groupable {

    ArrayList<Groupable> groups;
    private boolean isSelected = false;

    public NoteGroup(ArrayList<Groupable> groups) {
        this.groups = groups;
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

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public double getDuration() {
        return 0;
    }

    @Override
    public int getStartTick() {
        if (getNotes().isEmpty()) { return 0; }
        int minStartTick = getNotes().get(0).getStartTick();
        for (Note note : getNotes()) {
            if (note.getStartTick() < minStartTick) {
                minStartTick = note.getStartTick();
            }
        }
        return minStartTick;
    }

    @Override
    public int getEndTick() {
        if (getNotes().isEmpty()) { return 0; }
        int maxEndTick = getNotes().get(0).getEndTick();
        for (Note note : getNotes()) {
            if (note.getEndTick() > maxEndTick) {
                maxEndTick = note.getEndTick();
            }
        }
        return maxEndTick;
    }

    @Override
    public void changeNoteDurations(double dx) {

    }
}
