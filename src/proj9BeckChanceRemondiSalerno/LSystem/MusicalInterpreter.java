package proj9BeckChanceRemondiSalerno.LSystem;

import proj9BeckChanceRemondiSalerno.CompositionManager;
import proj9BeckChanceRemondiSalerno.Models.Note;

import java.awt.*;
import java.util.Stack;

public class MusicalInterpreter {

    private CompositionManager compositionManager;
    public MusicalInterpreter(CompositionManager compositionManager){
        this.compositionManager = compositionManager;
    }

    public void stringToNotes(String dstring, int distance, int pitch){
        String modString = "";
        int modVal = -1;
        boolean modGrab = false;
        // stateStack holds an x pos and angle?
        Stack<Point> stateStack = new Stack<>();
        Point currentPosition = new Point(0,pitch);

        Stack<Integer> instrumentStack = new Stack<>();
        Integer currentInstrument = 0;

        for (char c: dstring.toCharArray()) {
            if (c == '(') {
                modString = "";
                modGrab = true;
                continue;
            } else if (c == ')') {
                modVal = Integer.valueOf(modString);
                modGrab = false;
                continue;
            } else if (modGrab) {
                modString += c;
                continue;
            }

            if (c == 'F'){
                if (modVal == -1){
                    // NEW NOTE
                    this.compositionManager.addGroupable(new Note(currentPosition.x,
                            currentPosition.y, distance, currentInstrument));
                }
                else{
                    // NEW NOTE
                    // duration = modVal
                }
            }
            else if (c == '['){
                stateStack.push(currentPosition);
            }
            else if (c == ']'){
                currentPosition = stateStack.pop();
            }
            else if (c == '+'){
                currentPosition.y += pitch;
                if (currentPosition.y > 127){
                    currentPosition.y = 127;
                }
            }
            else if (c == '-'){
                currentPosition.y -= pitch;
                if (currentPosition.y < 0){
                    currentPosition.y = 0;
                }
            }
            else if (c == '_'){
                // basically a rest... just create distance between notes
                currentPosition.x += distance;
            }
            else if (c == '<'){
                instrumentStack.push(currentInstrument);
            }
            else if (c == '>'){
                currentInstrument = instrumentStack.pop();
            }
            else if (c == 'p'){
                currentInstrument = 0;
            }
            else if (c == 'h'){
                currentInstrument = 1;
            }
            else if (c == 'm'){
                currentInstrument = 2;
            }
            else if (c == 'o'){
                currentInstrument = 3;
            }
            else if (c == 'a'){
                currentInstrument = 4;
            }
            else if (c == 'g'){
                currentInstrument = 5;
            }
            else if (c == 'v'){
                currentInstrument = 6;
            }
            else if (c == 'f'){
                currentInstrument = 7;
            }
        }
    }
}
