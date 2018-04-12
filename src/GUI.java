import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class GUI {

    private JFrame mainframe;
    private JTextArea output;
    private JButton start;
    private JScrollPane sp;
    private String logFile;
    private BufferedReader br;
    private LogReader reader;
    private boolean active = false;

    public GUI() throws FileNotFoundException {
        //C:\\Program Files (x86)\\SteamLibrary\\steamapps\\common\\Rust\\RustClient_Data\\
        //br = new BufferedReader(new FileReader("C:\\Program Files (x86)\\SteamLibrary\\steamapps\\common\\Rust\\RustClient_Data\\output_log.txt"));
        openFileChooser();
        br = new BufferedReader(new FileReader(new File(logFile)));

        buildMainFrame();

    }

    /*
     * Construct main JFrame
     */
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

    /*
     * Receives an ArrayList as input and displays
     */
    public void setTextArea(ArrayList<CombatInstance> in) {
        String msg = "";
        int counter;

        if (in.size() < 20 && in.size() > 0) {
            counter = 20 - in.size();
            while (counter < in.size()) {
                counter++;
            }
            for (CombatInstance temp : in) {
                msg += temp.toString();
            }
            output.setText(msg);
        } else {
            for (int i = in.size() - 20; i <= in.size() - 1; i++) {
                msg += in.get(i);
            }
            output.setText(msg);


        }


    }

    private class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //remove the start listener from the button
            for (ActionListener a : start.getActionListeners()) {
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

    public void openFileChooser() {
        File file = new File("log.dat");

        //check to see if their is already a file path saved
        if (file.exists() && file.isFile()) {
            try {
                FileInputStream fis = new FileInputStream("log.dat");
                ObjectInputStream ois = new ObjectInputStream((fis));
                logFile = (String) ois.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            final JFileChooser fileChooser = new JFileChooser();
            int rVal = fileChooser.showOpenDialog(mainframe);
            //mainframe.setVisible(true);
            if (rVal == fileChooser.APPROVE_OPTION) {
                logFile = fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                //todo
                System.exit(0);
            }

            try {
                FileOutputStream fos = new FileOutputStream("log.dat");
                ObjectOutputStream oos = new ObjectOutputStream((fos));
                oos.writeObject(logFile);
                oos.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void startLoop() throws IOException, InterruptedException {

        Thread t = new Thread() {
            int count = 0;

            public void run() {

                while (active) {
                    count++;
                    //System.out.println(count);
                    try {
                        //System.out.println("here");
                        if (reader.parseFile()) {
                            System.out.println("here");
                            setTextArea((ArrayList)reader.getList());
                            output.setCaretPosition(output.getDocument().getLength());
                        }
                        sleep(1000);

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();
    }//end startLoop()


    public class StopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            active = false;

            for (ActionListener a : start.getActionListeners()) {
                start.removeActionListener(a);
            }


            start.addActionListener(new StartListener());
            start.setText("Start");

        }

    }
}
