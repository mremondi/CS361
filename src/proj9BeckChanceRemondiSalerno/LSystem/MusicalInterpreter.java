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

    public void stringToNotes(String dstring, int distance, int startingPitch){
        String modString = "";
        int modVal = -1;
        boolean modGrab = false;
        // stateStack holds an x pos and angle?
        Stack<Point> stateStack = new Stack<>();
        Point currentPosition = new Point(0, startingPitch);

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
                    this.compositionManager.addGroupable(new Note(currentPosition.x,
                            currentPosition.y, distance, currentInstrument));
                    currentPosition.x += distance;
                    if (currentPosition.x > 1900){
                        currentPosition.x = 1900;
                    }
                }
                else{
                    this.compositionManager.addGroupable(new Note(currentPosition.x,
                            currentPosition.y,
                            modVal,
                            currentInstrument));
                }
            }
            else if (c == '['){
                stateStack.push((Point)currentPosition.clone());
                System.out.println(stateStack.peek().x);
            }
            else if (c == ']'){
                currentPosition = stateStack.pop();
                System.out.println(currentPosition.x);

            }
            else if (c == '-'){
                if (modVal == -1) {
                    currentPosition.y += 10;
                    if (currentPosition.y > 1260) {
                        currentPosition.y = 1260;
                    }
                }
                else{
                    currentPosition.y += modVal;
                    if (currentPosition.y > 1260) {
                        currentPosition.y = 1260;
                    }
                }
            }
            else if (c == '+'){
                if (modVal == -1) {
                    currentPosition.y -= 10;
                    if (currentPosition.y < 0) {
                        currentPosition.y = 0;
                    }
                }
                else{
                    currentPosition.y -= modVal;
                    if (currentPosition.y > 0) {
                        currentPosition.y = 0;
                    }
                }
            }
            else if (c == '_'){
                if (modVal == -1) {
                    currentPosition.y += distance;
                    if (currentPosition.y > 1900) {
                        currentPosition.y = 1900;
                    }
                }
                else{
                    currentPosition.y += modVal;
                    if (currentPosition.y > 1900) {
                        currentPosition.y = 1900;
                    }
                }
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
            modVal = -1;
        }
    }
}
