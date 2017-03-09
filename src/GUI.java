import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;


public class GUI {

	private JFrame mainframe;
	private JTextArea output;
	private JButton start;
	private BufferedReader br;
	LogReader reader;
	
	
	
	public GUI() throws FileNotFoundException
	{
		buildMainFrame();
		br = new BufferedReader(new FileReader("output_log.txt"));
	}
	
	public void buildMainFrame()
	{
		mainframe = new JFrame();
		mainframe.setSize(600,600);
		mainframe.setLayout(new BorderLayout());
		output = new JTextArea();
		mainframe.add(output, BorderLayout.CENTER);
		start = new JButton("Start");
		start.addActionListener(new StartListener());
		mainframe.add(start, BorderLayout.SOUTH);
		mainframe.setVisible(true);
	}
	
	public void setTextArea(ArrayList<CombatInstance> in)
	{
		String msg = "";
		for(CombatInstance temp : in)
		{
			System.out.println(temp.toString());
			msg += temp.toString();
		}
	}
	
	private class StartListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			reader = new LogReader(br);
			try {
				reader.parseFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setTextArea(reader.getList());
			
		}
		
	}
}
