import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


//toadd: when killed give 
public class LogReader  {
	
	ArrayList<CombatInstance> combatList;
	BufferedReader log;
	CombatInstance exchange;
	String[] logLine;
	
	public LogReader(BufferedReader log)
	{
		this.log = log;
		combatList = new ArrayList<CombatInstance>();
	}
	
	//TimeUnit.SECONDS.sleep(1);
	//returns false if file is empty
	public boolean nextInstance() throws IOException
	{
		String temp = log.readLine();
		if(temp == null)
		{
			return false;
		}
		logLine = temp.split("\\s+");
	
		
		if(CombatInstance.isValid(logLine))
		{
			System.out.println("1");
			exchange = new CombatInstance(logLine);
			combatList.add(exchange);
			return true;
		}
		if(logLine == null)
		{
			return false;
		}
		
		return true;
		
		
	}
	
	public void parseFile() throws IOException 
	{
		while(nextInstance())
		{
		}
	}
	
	
	
	public ArrayList getList()
	{
		return combatList;
	}
	
	


}
