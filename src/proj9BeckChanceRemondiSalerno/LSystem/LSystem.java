package proj9BeckChanceRemondiSalerno.LSystem;

import java.io.*;
import java.util.HashMap;

public class LSystem{
    private String filename;
    private String base;
    private HashMap<String, String> rules;

    public LSystem(String filename){
        this.filename = filename;
        this.base = "";
        this.rules = new HashMap<>();
    }

    public void setBase(String newBase){
        this.base = newBase;
    }

    public void addRule(String newRule){
        this.rules.put(newRule.substring(0,1), newRule.substring(1));
    }

    public void read(){
        File file = new File(this.filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = reader.readLine()) != null) {
                String[] sArray = text.split(" ");
                if (sArray[0].equals("base")){
                    setBase(sArray[1]);
                }
                else if(sArray[0].equals("rule")){
                    addRule(sArray[2]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("here");
        } catch (IOException e) {
            System.out.println("here1");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("here2");
            }
        }
    }

    public String applyRules(String input){
        String resultString = "";
        for (int i=0; i<input.length(); i++){
            String c = input.substring(i,i+1);
            if (this.rules.containsKey(c)){
                resultString += this.rules.get(c);
            }
            else{
                resultString += c;
            }
        }
        return resultString;
    }

    public String buildString(int iterations){
        String tempString = this.base;
        for (int i=0; i<iterations; i++){
            tempString = this.applyRules(tempString);
        }
        return tempString;
    }

    public static void main(String args[]){
        String filename = "systemH.txt";
        int iterations = 3;
        LSystem lsys = new LSystem(filename);
        lsys.read();
        String test = lsys.buildString(iterations);
        System.out.println(test);
    }
}