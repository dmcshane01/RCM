import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class main {

    public static void main(String[] args) throws IOException {
        //BufferedReader br = new BufferedReader(new FileReader("output_log.txt"));
        //new LogReader(br);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new GUI();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }  // Let the constructor do the job
            }
        });
    }

}
