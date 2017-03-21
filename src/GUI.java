import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class GUI {

	private JFrame mainframe;
	private JTextArea output;
	private JButton start;
	private BufferedReader br;
	LogReader reader;

	public GUI() throws FileNotFoundException {
		buildMainFrame();
		br = new BufferedReader(new FileReader("output_log.txt"));
	}

	public void buildMainFrame() {
		mainframe = new JFrame();
		mainframe.setSize(600, 600);
		mainframe.setLayout(new BorderLayout());
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		output = new JTextArea();
		mainframe.add(output, BorderLayout.CENTER);
		start = new JButton("Start");
		start.addActionListener(new StartListener());
		mainframe.add(start, BorderLayout.SOUTH);
		mainframe.setVisible(true);
	}

	// change to show last 20 occurences or so
	public void setTextArea(ArrayList<CombatInstance> in) {

		String msg = "";
		for (CombatInstance temp : in) {
			msg += temp.toString();
		}

		output.setText(msg);

	}

	private class StartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			reader = new LogReader(br);
			try {
				startLoop();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e2) {
				// donothingrightnow
			}

		}

	}

	public void startLoop() throws IOException, InterruptedException
	{
		Thread t = new Thread(){
			
			public void run(){
		
		
		while(true)
		{
			
		try {
			reader.parseFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTextArea(reader.getList());
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
			}
		};
		t.start();
	}}
	
	
	
	
	
	
