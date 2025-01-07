import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel implements KeyListener, ActionListener {
    int boardWidth = 750;
    int boardHeight = 350;

    // Sprite Sheets as BufferedImages
    BufferedImage backgroundImg;
    BufferedImage fighter1SpriteSheet;
    BufferedImage fighter2SpriteSheet;

    // Health
    int fighter1Health = 100;
    int fighter2Health = 100;

    // Fighter1 properties
    int Fighter1Width = 88;
    int Fighter1Height = 94;
    int Fighter1X = 70;
    int Fighter1Y = boardHeight - Fighter1Height - 50;
    int fighter1VelocityX = 0;
    int fighter1VelocityY = 0;
    boolean fighter1Jumping = false;
    boolean fighter1Hitting = false;
    int fighter1Frame = 0;

    // Fighter2 properties
    int Fighter2Width = 88;
    int Fighter2Height = 100;
    int Fighter2X = 600;
    int Fighter2Y = boardHeight - Fighter2Height - 50;
    int fighter2VelocityX = 0;
    int fighter2VelocityY = 0;
    boolean fighter2Jumping = false;
    boolean fighter2Hitting = false;
    int fighter2Frame = 0;

    // Physics and animation
    int gravity = 2;
    Timer gameLoop;
    int animationDelay = 2;
    int animationCounter = 0;

    public Main() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        try {
            // Load images as BufferedImages
            backgroundImg = ImageIO.read(getClass().getResource("/FightGame/BG.jpg"));
            fighter1SpriteSheet = ImageIO.read(getClass().getResource("/FightGame/GokuB.png"));
            fighter2SpriteSheet = ImageIO.read(getClass().getResource("/FightGame/VegitaSprits.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize game loop
        gameLoop = new Timer(15, this);  //  FPS
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // Draw fighter 1 (keeping it simple with one sprite sheet selection)
        g.drawImage(getFighterFrame(fighter1SpriteSheet, fighter1Frame), Fighter1X, Fighter1Y, Fighter1Width, Fighter1Height, null);

        // Draw fighter 2 using the selected frame
        g.drawImage(getFighter2Frame(fighter2Frame), Fighter2X, Fighter2Y, Fighter2Width, Fighter2Height, null);

        // Draw health bars
        g.setColor(java.awt.Color.RED);
        g.fillRect(20, 20, fighter1Health, 10);  // Fighter 1 health bar
        g.fillRect(boardWidth - 120, 20, fighter2Health, 10);  // Fighter 2 health bar
    }

    private BufferedImage getFighterFrame(BufferedImage spriteSheet, int frame) {
        int frameWidth = 88;
        int frameHeight = 94;
        int columns = spriteSheet.getWidth() / frameWidth;
        
        int x = (frame % columns) * frameWidth;
        int y = (frame / columns) * frameHeight;
        
        return spriteSheet.getSubimage(x, y, frameWidth, frameHeight);
    }

    // Game loop
    @Override
public void actionPerformed(ActionEvent e) {
    
    // Update positions and apply gravity
    applyGravityAndMove();
    
    // Update animation frames
    updateAnimationFrames();
    
    // Check collision for hit detection
    checkCollisionAndDamage();
    
    // Check for game-over and restart if any fighter's health is zero
    checkGameOverAndRestart();

    // Repaint
    repaint();
}

private void checkGameOverAndRestart() {
    if (fighter1Health <= 0 || fighter2Health <= 0) {
        // Reset health
        fighter1Health = 100;
        fighter2Health = 100;
        
        // Reset positions
        Fighter1X = 70;
        Fighter1Y = boardHeight - Fighter1Height - 50;
        Fighter2X = 600;
        Fighter2Y = boardHeight - Fighter2Height - 50;
    }
}


    private void applyGravityAndMove() {
        // Fighter1 movement
        Fighter1X += fighter1VelocityX;
        Fighter1Y += fighter1VelocityY;

        if (fighter1Jumping) {
            fighter1VelocityY += gravity;
            if (Fighter1Y >= boardHeight - Fighter1Height - 50) {
                Fighter1Y = boardHeight - Fighter1Height - 50;
                fighter1Jumping = false;
                fighter1VelocityY = 0;
            }
        }

        // Fighter2 movement
        Fighter2X += fighter2VelocityX;
        Fighter2Y += fighter2VelocityY;

        if (fighter2Jumping) {
            fighter2VelocityY += gravity;
            if (Fighter2Y >= boardHeight - Fighter2Height - 50) {
                Fighter2Y = boardHeight - Fighter2Height - 50;
                fighter2Jumping = false;
                fighter2VelocityY = 0;
            }
        }
    }

    //IndexFighter2
    private BufferedImage getFighter2Frame(int frameIndex) {
        int x = 0, y = 0, width = 0, height = 0;
    
        // Define frame regions for Fighter 2
        switch (frameIndex) {
            case 0: // Standing
                x = 108; y = 58; width = 180 - 108; height = 211 - 58;
                break;
            case 1: // MovingForward
                x = 331; y = 88; width = 407 - 331; height = 195 - 88;
                break;
            case 2: // Hitting
                x = 407; y = 104; width = 492 - 407; height = 209 - 104;
                break;
            case 3: // Dead
                x = 0; y = 589; width = 136; height = 649 - 589;
                break;
            case 4: // Backward
                x = 610; y = 86; width = 704 - 610; height = 206 - 86;
                break;
        }
    
        return fighter2SpriteSheet.getSubimage(x, y, width, height);
    }
    
    // IndexFighter1
    private BufferedImage getFighter1Frame(int frameIndex) {
        int x = 0, y = 0, width = 0, height = 0;
        switch (frameIndex) {
            case 5: // Standing
                x = 8; y = 136; width = 83 - 8; height = 245 - 136;
                break;
            case 6: // MovingForward
                x = 322; y = 147; width = 385 - 322; height = 251 - 147;
                break;
            case 7: // Hitting
                x = 608; y = 276; width = 706 - 608; height = 389 - 276;
                break;
            case 8: // Dead
                x = 900; y = 41; width = 992 - 900; height = 77 - 41;
                break;
            case 9: // Backward
                x = 319; y = 22; width = 380 - 319; height = 118 - 22;
                break;
            case 10: // Backward
                x = 7; y = 18; width = 94 - 7; height = 116 - 18;
                break;
        }
        return fighter1SpriteSheet.getSubimage(x, y, width, height);
    }
    
    
    private void updateAnimationFrames() {
        // Update fighter2 frame index based on state
        int fighter2FrameIndex = 0;
        if (fighter2Health == 0) {
            fighter2FrameIndex = 3;  // Dead
        } else if (fighter2Hitting) {
            fighter2FrameIndex = 2;  // Hitting
        } else if (fighter2VelocityX < 0) {
            fighter2FrameIndex = 1;  // MovingForward
        } else if (fighter2VelocityX > 0) {
            fighter2FrameIndex = 4;  // MovingBackward
        } else {
            fighter2FrameIndex = 0;  // Standing
        }
        fighter2Frame = fighter2FrameIndex;
    
        // Update fighter1 frame index based on state
        int fighter1FrameIndex = 0;
        if (fighter1Health == 0) {
            fighter1FrameIndex = 8;  // Dead
        } else if (fighter1Hitting) {
            fighter1FrameIndex = 7;  // Hitting
        } else if (fighter1VelocityX < 0) {
            fighter1FrameIndex = 6;  // MovingForward
        } else if (fighter1VelocityX > 0) {
            fighter1FrameIndex = 9;  // MovingBackward
        } else {
            fighter1FrameIndex = 5;  // Standing
        }
        fighter1Frame = fighter1FrameIndex;
    }
    
    
    
    
    private void checkCollisionAndDamage() {
        if (fighter1Hitting && Fighter1X + Fighter1Width >= Fighter2X) {
            fighter2Health -= 2;  // Fighter1 hits Fighter2
        }
    
        if (fighter2Hitting && Fighter2X <= Fighter1X + Fighter1Width) {
            fighter1Health -= 2;  // Fighter2 hits Fighter1
        }
    
        // Limit health to 0
        fighter1Health = Math.max(0, fighter1Health);
        fighter2Health = Math.max(0, fighter2Health);
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Fighter1 controls (WASD for movement, E to hit)
            case KeyEvent.VK_A -> fighter1VelocityX = -10;
            case KeyEvent.VK_D -> fighter1VelocityX = 10;
            case KeyEvent.VK_W -> {
                if (!fighter1Jumping) {
                    fighter1Jumping = true;
                    fighter1VelocityY = -15;
                }
            }
            case KeyEvent.VK_E -> fighter1Hitting = true;

            // Fighter2 controls (Arrow keys for movement, L to hit)
            case KeyEvent.VK_LEFT -> fighter2VelocityX = -10;
            case KeyEvent.VK_RIGHT -> fighter2VelocityX = 10;
            case KeyEvent.VK_UP -> {
                if (!fighter2Jumping) {
                    fighter2Jumping = true;
                    fighter2VelocityY = -15;
                }
            }
            case KeyEvent.VK_L -> fighter2Hitting = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Stop Fighter1 movement
            case KeyEvent.VK_A, KeyEvent.VK_D -> fighter1VelocityX = 0;
            case KeyEvent.VK_E -> fighter1Hitting = false;

            // Stop Fighter2 movement
            case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> fighter2VelocityX = 0;
            case KeyEvent.VK_L -> fighter2Hitting = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}