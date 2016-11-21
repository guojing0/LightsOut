package a10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Implementation of Lights Out game for CS 1410
 *
 * @author Jing Guo
 */
public class LightsOut extends JFrame implements ActionListener {

    private LightsOutButton[][] buttons;
    private JButton restartButton;
    private JButton solverButton;
    private JLabel clicksLabel;
    private int num = 0;

    /**
     * Constructor method for the class
     */
    public LightsOut() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();
        JPanel controlPanel = new JPanel();
        panel.setLayout(new GridLayout(5, 5));
        controlPanel.setLayout(new FlowLayout());

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 500;
        gbc.ipady = 500;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0/6;
        mainPanel.add(panel, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 500;
        gbc.ipady = 100;
        gbc.weightx = 0.5;
        gbc.weighty = 5.0/6;
        mainPanel.add(controlPanel, gbc);

        restartButton = new JButton("Restart");
        controlPanel.add(restartButton);
        restartButton.addActionListener(this);

        solverButton = new JButton("Launch Solver");
        controlPanel.add(solverButton);
        solverButton.addActionListener(this);

        clicksLabel = new JLabel("Number of clicks: " + num, SwingConstants.CENTER);
        clicksLabel.setFont(new Font("Monospaced", 0, 12));
        controlPanel.add(clicksLabel);

        buttons = new LightsOutButton[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j] = new LightsOutButton(i, j);
                buttons[i][j].changeColor();
                panel.add(buttons[i][j]);
                buttons[i][j].addActionListener(this);
            }
        }

        setup();

        this.setTitle("Jing's Amazing Lights Out");
        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(500, 600));
        this.pack();
    }

    /**
     * Randomly fills with 25 buttons with lights on and off
     */
    private void setup() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (random.nextBoolean()) {
                    toggleButtons(buttons[i][j]);
                }
            }
        }
    }

    /**
     * Switches the current status of the light and change its color
     *
     * @param button the button that will be changed
     */
    private void switchAndChangeClr(LightsOutButton button) {
        button.switchStatus();
        button.changeColor();
    }

    /**
     * Toggles the button and its neighborhoods to the opposite color
     *
     * @param button the button that will be changed
     */
    private void toggleButtons(LightsOutButton button) {
        int buttonRow = button.getRow();
        int buttonCol = button.getCol();

        switchAndChangeClr(button);

        if (buttonRow != 4) { // row from 0 to 3
            switchAndChangeClr(buttons[buttonRow + 1][buttonCol]);
        }
        if (buttonRow != 0) { // row from 1 to 4
            switchAndChangeClr(buttons[buttonRow - 1][buttonCol]);
        }

        if (buttonCol != 4) { // col from 0 to 3
            switchAndChangeClr(buttons[buttonRow][buttonCol + 1]);
        }
        if (buttonCol != 0) { // col from 1 to 4
            switchAndChangeClr(buttons[buttonRow][buttonCol - 1]);
        }
    }

    /**
     * Number of dark lights, which will be
     * no smaller than 0 and no larger than 25
     *
     * @return number of dark lights
     */
    private int numOfOff() {
        int num = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!buttons[i][j].getStatus()) {
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * Prints a certain format of a button, i.e, (row, column)
     *
     * @param row row of the button
     * @param col column of the button
     */
    private void helpPrinter(int row, int col) {
        System.out.println("(" + row + ", " + col + ")");
    }

    /**
     * Automatically solves the puzzle using light chasing strategy
     */
    private void launchSolver() { // TODO automatically calculate second part loop
        System.out.println("Solve:");

        LightsOutButton[] topButtons = buttons[0];
        LightsOutButton[] bottomButtons = buttons[4];

        while (numOfOff() != 25) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (buttons[i][j].getStatus()) {
                        toggleButtons(buttons[i + 1][j]);
                        helpPrinter(buttons[i + 1][j].getRow(), buttons[i + 1][j].getCol());
                    }
                }
            }

            if (bottomButtons[0].getStatus() && bottomButtons[1].getStatus()
                    && bottomButtons[3].getStatus() && bottomButtons[4].getStatus()) {
                toggleButtons(topButtons[2]);
                helpPrinter(topButtons[2].getRow(), topButtons[2].getCol());
            } else if (bottomButtons[0].getStatus() && bottomButtons[4].getStatus()) {
                toggleButtons(topButtons[0]);
                toggleButtons(topButtons[1]);
                helpPrinter(topButtons[0].getRow(), topButtons[0].getCol());
                helpPrinter(topButtons[1].getRow(), topButtons[1].getCol());
            } else if (bottomButtons[1].getStatus() && bottomButtons[3].getStatus()) {
                toggleButtons(topButtons[0]);
                toggleButtons(topButtons[3]);
                helpPrinter(topButtons[0].getRow(), topButtons[0].getCol());
                helpPrinter(topButtons[3].getRow(), topButtons[3].getCol());
            } else if (bottomButtons[0].getStatus() && bottomButtons[1].getStatus() && bottomButtons[2].getStatus()) {
                toggleButtons(topButtons[1]);
                helpPrinter(topButtons[1].getRow(), topButtons[1].getCol());
            } else if (bottomButtons[2].getStatus() && bottomButtons[3].getStatus() && bottomButtons[4].getStatus()) {
                toggleButtons(topButtons[3]);
                helpPrinter(topButtons[3].getRow(), topButtons[3].getCol());
            } else if (bottomButtons[0].getStatus() && bottomButtons[2].getStatus() && bottomButtons[3].getStatus()) {
                toggleButtons(topButtons[4]);
                helpPrinter(topButtons[4].getRow(), topButtons[4].getCol());
            } else if (bottomButtons[1].getStatus() && bottomButtons[2].getStatus() && bottomButtons[4].getStatus()) {
                toggleButtons(topButtons[0]);
                helpPrinter(topButtons[0].getRow(), topButtons[0].getCol());
            }
        }
    }

    /**
     * If resetButton is clicked, the puzzle will be reset;
     * if solverButton is clicked, it will automatically solve it;
     * if one of the light buttons is clicked, number of clicks will increase
     * by one and it and its neighborhoods buttons will be toggled
     * <p>
     * If all lights are off, it will beep and congrat popping a dialog box
     *
     * @param e event that will be received
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof LightsOutButton) {
            LightsOutButton button = (LightsOutButton) e.getSource();
            num++;
            clicksLabel.setText("Number of clicks: " + num);

            toggleButtons(button);

            if (numOfOff() == 25) {
                Toolkit.getDefaultToolkit().beep(); // beep when player wins
                JOptionPane.showMessageDialog(null, "You just won the game!");
            }
        } else if (e.getSource() == restartButton) {
            num = 0;
            clicksLabel.setText("Number of clicks: " + num);
            setup();
        } else if (e.getSource() == solverButton) {
            launchSolver();
        }
    }

    public static void main(String[] args) {
        LightsOut game = new LightsOut();
        game.setVisible(true);
    }
}
