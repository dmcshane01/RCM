

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogReader {

    ArrayList<CombatInstance> combatList;
    BufferedReader log;
    CombatInstance exchange;
    String[] logLine;

    /**
     * Load BufferedReader and initialize list
     */
    public LogReader(BufferedReader log) {
        this.log = log;
        combatList = new ArrayList<>();
    }

    /**
     * Read next line of buffer and initialize a combatinstance
     * @return true  if a valid instance was read or if more lines in log left to read.
     */
    public boolean nextInstance() throws IOException {
        /**reads next line from console log*/
        String temp = log.readLine();
        if (temp == null){ return false; }

        /**parse strings by whitespace and store in list*/
        logLine = temp.split("\\s+");


        /**if a valid combat instance, store in array*/
        if (CombatInstance.isValid(logLine)) {
            exchange = new CombatInstance(logLine);
            combatList.add(exchange);
            return true;
        }
        if (logLine == null) {
            return false;
        }
        return true;
    }

    /**
     * calls nextInstance to load instances into list until log is empty
     * @return true if more than 20 instances found false if less than 20
     */
    public boolean parseFile() throws IOException {
        int count = 0;
        while (nextInstance()) {
            count++;
        }

        if (count > 20)
            return true;
        else
            return false;
    }

    /**
     * Gets list to use for output
     * @return if list size is greater than 20 return last 20
     */
    public ArrayList<CombatInstance> getList() {
        if(combatList.size() > 20){
            return new ArrayList<>(combatList.subList(combatList.size() - 21, combatList.size() - 1 ));
        }
        else{
            return combatList;
        }


    }


}
