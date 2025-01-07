import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MemoryCardGame {
    private final JFrame frame;
    private final JPanel panel;
    private final JButton[] buttons;
    private final ImageIcon[] cardImages;  // Holds the images for the cards
    private final String[] cardNames;      // Holds the image filenames for comparison
    private final boolean[] cardFlipped;   // Tracks which cards are flipped
    private int flippedCardCount;    // Count of flipped cards in a turn
    private int firstCardIndex;      // Index of the first flipped card
    private int secondCardIndex;     // Index of the second flipped card
    private int currentPlayer = 1;   // Player 1 starts
    private final int[] moves;             // Tracks moves for each player
    private JLabel turnLabel;        // Shows whose turn it is
    private int matchedPairs;        // Tracks the number of matched pairs
    private boolean isPlayerTurn = true; // Flag to control player turn timing

    public MemoryCardGame() {
        // Initialize the game
        frame = new JFrame("Memory Card Game");
        panel = new JPanel();
        buttons = new JButton[16];
        cardImages = new ImageIcon[16];
        cardNames = new String[16];
        cardFlipped = new boolean[16];
        moves = new int[2]; // Player 1 and Player 2's move counts
        matchedPairs = 0;
        flippedCardCount = 0;

        // Load card images and shuffle them
        loadImages();

        // Setup the JFrame
        setupGameBoard();

        // Start the game
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 1100);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void loadImages() {
        // Load images and shuffle the cards
        String[] imageFiles = {
            "Ayaka.gif", "Ayaka.gif",
            "Gojo.gif", "Gojo.gif",
            "Kurumi.gif", "Kurumi.gif",
            "ShinobiSisters.gif", "ShinobiSisters.gif",
            "Yuuta.gif", "Yuuta.gif",
            "Zero2.gif", "Zero2.gif",
            "Shoob.gif", "Shoob.gif",
            "Bannett.gif", "Bannett.gif"
        };

        // Shuffle the card names
        List<String> imageList = Arrays.asList(imageFiles);
        Collections.shuffle(imageList);
        imageList.toArray(cardNames);

        // Load images
        for (int i = 0; i < 16; i++) {
            cardImages[i] = new ImageIcon(getClass().getResource(imageList.get(i)));
        }
    }

    private void setupGameBoard() {
        panel.setLayout(new GridLayout(4, 4)); // 4x4 grid

        // Create buttons for each card
        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(80, 80));
            buttons[i].setBackground(Color.GRAY);
            buttons[i].setIcon(null); // Initially show no image
            buttons[i].addActionListener(new CardButtonListener(i));
            panel.add(buttons[i]);
            cardFlipped[i] = false;
        }

        // Add a label at the top to indicate whose turn it is
        turnLabel = new JLabel("Player 1's Turn", JLabel.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(turnLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
    }
    // Help From Someone else!!
    private class CardButtonListener implements ActionListener {
        private final int index;

        public CardButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // If the card is already flipped or it's not this player's turn, ignore the click
            if (cardFlipped[index] || !isPlayerTurn) {
                return;
            }

            flipCard(index);
            if (flippedCardCount == 2) {
                // After flipping two cards, check for match
                checkMatch();
            }
        }
    }

    private void flipCard(int index) {
        cardFlipped[index] = true;
        SwingUtilities.invokeLater(() -> {
            buttons[index].setIcon(cardImages[index]); // Show the image
            buttons[index].repaint(); // Force a repaint to ensure the icon is updated
        });
        flippedCardCount++;
        if (flippedCardCount == 1) {
            firstCardIndex = index; // First card flipped
        } else if (flippedCardCount == 2) {
            secondCardIndex = index; // Second card flipped
        }
    }
    // ChatGPT!
    private void checkMatch() {
        isPlayerTurn = false; // Disable further actions while checking match

        if (cardNames[firstCardIndex].equals(cardNames[secondCardIndex])) {
            matchedPairs++;
            if (matchedPairs == 8) {
                // Check for a draw if all pairs have been matched
                checkForDraw();
                return; // End the game after checking for draw or winner
            }
        } else {
            // Cards don't match, flip them back after a brief delay
            Timer timer = new Timer(1000, e -> {
                buttons[firstCardIndex].setIcon(null);
                buttons[secondCardIndex].setIcon(null);
                cardFlipped[firstCardIndex] = false;
                cardFlipped[secondCardIndex] = false;
                flippedCardCount = 0;  // Reset the flipped card count
                switchTurn();          // Switch to the next player
                isPlayerTurn = true;   // Enable the next player's turn
            });
            timer.setRepeats(false);
            timer.start();
            return; // Early exit for mismatch
        }

        // If it's a match, switch turn after resetting flippedCardCount
        flippedCardCount = 0;
        switchTurn();          // Switch to the next player
        isPlayerTurn = true;   // Enable the next player's turn

        // Update the move counter
        moves[currentPlayer - 1]++;
        updateTurnLabel();
    }
    //ChatGpt!!
    private void checkForDraw() {
        // Check if the game is a draw by comparing the number of pairs matched by both players
        if (matchedPairs == 8) {
            if (moves[0] == moves[1]) {
                JOptionPane.showMessageDialog(frame, "The game is a draw!");
            } else {
                // Determine the winner based on the number of pairs matched
                int winner = (moves[0] > moves[1]) ? 1 : 2;
                JOptionPane.showMessageDialog(frame, "Player " + winner + " wins!");
            }
        }
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;  // Switch between Player 1 and Player 2
        updateTurnLabel();  // Update the turn label to reflect whose turn it is
    }

    private void updateTurnLabel() {
        turnLabel.setText("Player " + currentPlayer + "'s Turn");
    }

    public static void main(String[] args) {
        new MemoryCardGame();
    }

    public void requestFocus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestFocus'");
    }
}

