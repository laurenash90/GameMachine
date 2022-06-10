package MemoryGame;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGUI extends JPanel {
    private JToggleButton selectedCard;
    private JToggleButton b1;
    private JToggleButton b2;
    private Timer t;
    private JFrame frame;
    private JPanel cardPanel;
    private JLabel keepscore;
    private JButton gamelibrary;
    private ImageIcon cardback;
    private int matches;
    private int score;


    /*****************************************************
     *
     * Method that sets up the board for game play
     *
     ****************************************************/
    public MemoryGUI() {
            //Frame
            frame = new JFrame(); // creates a frame
            frame.setTitle("Memory Game"); // Title of frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits out of application
            frame.setSize(600, 600); // sets size
            frame.setResizable(false); // prevent frame from being resized
            frame.getContentPane().setBackground(Color.GRAY); // change color of background
            frame.setLayout(new FlowLayout());

            //Score Label
            keepscore = new JLabel("Score: " + score, JLabel.LEFT);
            keepscore.setFont(new Font("Bro", Font.PLAIN, 20));

            //Game Library Button
            gamelibrary = new JButton("Game Library");
            /*
             * ADD FUNCTIONALITY TO BRING BACK TO GAME LIBRARY HERE
             */

            //Card Panel
            cardPanel = new JPanel();
            cardPanel.setBackground(Color.GRAY);
            cardPanel.setPreferredSize(new Dimension(525, 525));
            cardPanel.setLayout(new GridLayout(4, 4));
            cardPanel.setVisible(true);

            //ImageIcon for cardback
            cardback = new ImageIcon("/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/jello2.png");
            String[] pics = {
                    "/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/angela.png",
                    "/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/dwight.png",
                    "/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/jim.png",
                    "/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/kevin.png",
                    "/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/michael.png",
                    "/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/pam.png",
                    "/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/stanley.png",
                    "/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/toby.png"
            };
            ArrayList<ImageIcon> cardface = new ArrayList<ImageIcon>();

            //Creates cardface ImageIcon array from string array pics
            for (int i = 0; i < pics.length; i++) {
                cardface.add(new ImageIcon(pics[i]));
                cardface.add(new ImageIcon(pics[i]));
            }

            //Shuffles array cardface
            Collections.shuffle(cardface);

            //set up the timer
            t = new Timer(750, new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    checkCards();
                    score++;
                    keepscore.setText("Score: " + score);

                }
            });
            t.setRepeats(false);

            //Creates JButtons and adds to cardPanel
            for (int i = 0; i < 16; i++) {
                JToggleButton button = new JToggleButton(cardback, false);
                button.setSelectedIcon(cardface.get(i));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        selectedCard = button;
                        doTurn();
                    }
                });
                cardPanel.add(button);
            }
            frame.add(keepscore);
            frame.add(gamelibrary);
            frame.add(cardPanel);
            frame.setVisible(true);
    }

    /************************************************
     *
     * Method for taking a turn
     *
     ***********************************************/
    public void doTurn() {
        if (b1 == null && b2 == null) {
            b1 = selectedCard;
            b1.setSelected(true);
        }

        if (b1 != null && b1 != selectedCard && b2 == null) {
            b2 = selectedCard;
            b2.setSelected(true);
            t.start();
        }
    }

    /****************************************************************
     *
     * Method for checking selected cards for match or mismatch
     *
     ****************************************************************/
    public void checkCards() {
        //Match
        if (b1.getSelectedIcon().toString().equals(b2.getSelectedIcon().toString())) {
            b1.setEnabled(false);
            b2.setEnabled(false);
            matches++;
            if (this.isGameWon()) {
                String[] responses = {"Play Again", "Game Library"};
                ImageIcon icon = new ImageIcon("/Users/laureninman/Downloads/GameMachine/src/MemoryGame/Images/img.png");
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "You Win!",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        icon,
                        responses,
                        0);
                //Play Again
                if (choice == JOptionPane.YES_OPTION) {
                    setPlayagain();
                }
                //Back to Game Library
                if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        }
        //Mismatch
        else {
            b1.setSelected(false);
            b2.setSelected(false);
        }
        b1 = null;
        b2 = null;
    }

    /****************************************************
     *
     * Method that checks if the game has been won
     * @return boolean
     *
     ***************************************************/
    public boolean isGameWon() {
        if (matches == 8) {
            return true;
        }
        return false;
    }

    /*******************************************************
     *
     * Method that sets the board up to play again
     *
     *******************************************************/
    public void setPlayagain() {
        main(null);
    }

    public static void main(String[] args) {
        new MemoryGUI();

    }

}
