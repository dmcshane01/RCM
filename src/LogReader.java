import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class LogReader {
	
	ArrayList<CombatInstance> combatList;
	BufferedReader log;
	String[] logLine;
	
	public LogReader(BufferedReader log)
	{
		this.log = log;
	}
	
	
	public void nextInstance() throws IOException
	{
		String temp = log.readLine();
		logLine = temp.split("\\s+");
		
	}
	


}
