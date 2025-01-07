import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JPanel {
    public static void main(String[] args) {
        int boardWidth = 750;
        int boardHeight = 350;

        JFrame frame = new JFrame("Fight Game");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of App and set its size
        Main mainPanel = new Main();
        mainPanel.setPreferredSize(new Dimension(boardWidth, boardHeight));

        // Add mainPanel (App) to the frame
        frame.add(mainPanel);
        frame.pack();
        mainPanel.requestFocusInWindow();
        frame.setVisible(true);
    }
}
