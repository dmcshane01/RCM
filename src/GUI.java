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
	private JScrollPane sp;
	private BufferedReader br;
	private LogReader reader;
	private boolean active = false;
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
		start = new JButton("Start");
		start.addActionListener(new StartListener());
		sp = new JScrollPane(output);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainframe.add(sp, BorderLayout.CENTER);
		mainframe.add(start, BorderLayout.SOUTH);
		mainframe.setVisible(true);
	}

	// change to show last 20 occurences or so
	public void setTextArea(ArrayList<CombatInstance> in) {

		String msg = "";
		for (CombatInstance temp : in) {
			msg += temp.toString();
		}

		System.out.println("Add");
		output.setText(msg);

	}

	private class StartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//remove the start listener from the button
			for(ActionListener a : start.getActionListeners())
			{
				start.removeActionListener(a);
			}
			
			
			start.addActionListener(new StopListener());
			start.setText("Stop");
			
			reader = new LogReader(br);
			active = true;
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

	public void startLoop() throws IOException, InterruptedException {
		
		
		Thread t = new Thread() {

			public void run() {

				while (active) {

					try {
						reader.parseFile();
						setTextArea(reader.getList());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		
		t.start();
	}//end startLoop()
	
	
	public class StopListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			active = false;
			
			for(ActionListener a : start.getActionListeners())
			{
				start.removeActionListener(a);
			}
			
			
			start.addActionListener(new StartListener());
			start.setText("Start");
			
		}
		
	}
}
