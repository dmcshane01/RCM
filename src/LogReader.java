import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


//toadd: when killed give 

//parses console output for combat information
//stores valid combat instances in an arraylist
public class LogReader  {
	
	ArrayList<CombatInstance> combatList;
	BufferedReader log;
	CombatInstance exchange;
	String[] logLine;
	
	//Constructor
	public LogReader(BufferedReader log)
	{
		this.log = log;
		combatList = new ArrayList<CombatInstance>();
	}
	
	//TimeUnit.SECONDS.sleep(1);
	//returns false if file is empty
	public boolean nextInstance() throws IOException
	{
		//reads next line from console log
		String temp = log.readLine();
		if(temp == null)
		{
			return false;
		}
		//parse strings by whitespace and store in array
		logLine = temp.split("\\s+");
	
		
		//if a valid combat instance, store in array
		if(CombatInstance.isValid(logLine))
		{
			exchange = new CombatInstance(logLine);
			combatList.add(exchange);
			return true;
		}
		if(logLine == null)
		{
			return false;
		}
		
		//if not a valid combat instance, but not null, return true to show there is still lines in console
		return true;
		
		
	}
	//calls the nextInstance method until it returns false, meaning there is nothing left to parse
	public boolean parseFile() throws IOException 
	{
		int count = 0;
		while(nextInstance())
		{
			count++;
		}
		
		if(count > 20)
			return true;
		else
			return false;
	}
	
	public ArrayList<CombatInstance> getList()
	{
		
		ArrayList<CombatInstance> out = new ArrayList<CombatInstance>();
		
		if(combatList.size() > 40)
		{
			for(int i = combatList.size() - 30; i <= combatList.size() - 1; i++ )
			{
				out.add(combatList.get(i));
			}
			
			//System.out.println(out.get(0).toString());
			combatList = new ArrayList<CombatInstance>();
			return out;
		}
		else
		{
			return combatList;
		}
		
		//return combatList;
		
		
	}
	
	


}
