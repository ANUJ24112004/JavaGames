import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Memory game!");
        // int boardWidth = 800;
        // int boardHeight = 800;

        JFrame frame = new JFrame("Chrome Dinosaur");
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MemoryCardGame memoryCardGame = new MemoryCardGame();
        // frame.add(memoryCardGame);
        frame.pack();
        memoryCardGame.requestFocus();
        frame.setVisible(true);
    }
}
