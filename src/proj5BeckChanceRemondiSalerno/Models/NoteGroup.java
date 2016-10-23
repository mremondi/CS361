package proj5BeckChanceRemondiSalerno.Models;

import java.util.ArrayList;

/**
 * Created by mremondi on 10/20/16.
 */
public class NoteGroup implements NoteGroupable {

    ArrayList<NoteGroupable> noteGroupables;
    private boolean isSelected = false;

    public NoteGroup(ArrayList<NoteGroupable> noteGroupables) {
        this.noteGroupables = noteGroupables;
    }

    @Override
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for (NoteGroupable group : noteGroupables) {
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
        return getEndTick() - getStartTick();
    }

    @Override
    public int getStartTick() {
        if (getNotes().isEmpty()) { return 0; }
        int minStartTick = noteGroupables.get(0).getStartTick();
        for (NoteGroupable noteGroupable : noteGroupables) {
            if (noteGroupable.getStartTick() < minStartTick) {
                minStartTick = noteGroupable.getStartTick();
            }
        }
        return minStartTick;
    }

    @Override
    public int getEndTick() {
        if (getNotes().isEmpty()) { return 0; }
        int maxEndTick = noteGroupables.get(0).getEndTick();
        for (NoteGroupable noteGroupable : noteGroupables) {
            if (noteGroupable.getEndTick() > maxEndTick) {
                maxEndTick = noteGroupable.getEndTick();
            }
        }
        return maxEndTick;
    }

    @Override
    public void changeNoteDurations(double dx) {
        for (NoteGroupable noteGroupable : noteGroupables) {
            noteGroupable.changeNoteDurations(dx);
        }
    }

    @Override
    public int getMaxPitch() {
        if (getNotes().isEmpty()) { return 0; }
        int maxPitch = noteGroupables.get(0).getMaxPitch();
        for (NoteGroupable noteGroupable : noteGroupables) {
            if (noteGroupable.getMaxPitch() > maxPitch) {
                maxPitch = noteGroupable.getMaxPitch();
            }
        }
        return maxPitch;
    }

    @Override
    public int getMinPitch() {
        if (getNotes().isEmpty()) { return 0; }
        int minPitch = noteGroupables.get(0).getMinPitch();
        for (NoteGroupable noteGroupable : noteGroupables) {
            if (noteGroupable.getMinPitch() < minPitch) {
                minPitch = noteGroupable.getMinPitch();
            }
        }
        return minPitch;
    }

    public ArrayList<NoteGroupable> getNoteGroupables() {
        return noteGroupables;
    }

    @Override
    public void changeStartTick(double dx) {
        for (NoteGroupable noteGroupable : noteGroupables) {
            noteGroupable.changeStartTick(dx);
        }
    }

    @Override
    public void changePitch(double dy) {
        for (NoteGroupable noteGroupable : noteGroupables) {
            noteGroupable.changePitch(dy);
        }
    }
}
