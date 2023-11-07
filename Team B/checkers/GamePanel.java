package checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static CheckerSquare[][] squares;
    public static JLabel turn = new JLabel("Blue's turn");

    public static void setTurnLabelText(String txt){
        turn.setText(txt);
    }

    public static CheckerSquare[][] getSquares() {
        return squares;
    }


    public GamePanel() {
        BackgroundPanel boardPanelHolder = new BackgroundPanel(new Color(0, 0, 0, 90));
        boardPanelHolder.setPreferredSize(new Dimension(550, 550));
        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        squares = new CheckerSquare[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                CheckerSquare square = new CheckerSquare(row, col);
                squares[row][col] = square;
                boardPanel.add(square);
                square.addMouseListener(new CheckerSquareMouse(square));
            }
        }

        boardPanelHolder.add(boardPanel, BorderLayout.CENTER);
        add(boardPanelHolder, BorderLayout.CENTER);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                GUI.gameOver = false;
            }
        });

        JButton clearBoardButton = new JButton("Clear Board");
        clearBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
                GUI.gameOver = true;
            }
        });

        JButton helpButton = new JButton("?");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelpDialog();
            }
        });

        configureButton(startGameButton);
        configureButton(clearBoardButton);
        configureButton(helpButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(125, 125, 125));
        buttonPanel.add(startGameButton);
        buttonPanel.add(clearBoardButton);
        buttonPanel.add(helpButton);
        turn.setForeground(Color.WHITE);
        buttonPanel.add(turn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void configureButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Centered the button horizontally
        button.setFont(new Font("Arial", Font.BOLD, 18)); // font and size
        button.setForeground(Color.WHITE); // text color to white
        button.setBackground(Color.BLACK); //background color to black
        button.setFocusPainted(false);

    }

    private void startGame() {
        clearBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row < 3 && (row + col) % 2 == 1) {
                    squares[row][col].toggleChecker(CheckerSquare.TEAM1);
                    //squares[row][col].setIsKing(true); uncomment to test king graphics quickly
                } else if (row > 4 && (row + col) % 2 == 1) {
                    squares[row][col].toggleChecker(CheckerSquare.TEAM2);
                    //squares[row][col].setIsKing(true); uncomment to test king graphics quickly
                }
            }
        }
    }

    private void clearBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col].toggleChecker(null);
                squares[row][col].setIsKing(false);
                squares[row][col].setSelected(false);
            }
        }
        CheckerSquareMouse.clearSelection();
        GUI.blueTurn = true;
        GamePanel.setTurnLabelText("Blue's turn");
    }

    private void showHelpDialog() {
        String message = "Click the checker you want to move.\n" +
                "Click the empty space you want to move the checker to.\n" +
                "You can click the checker again to deselect it, or click another checker.";
        JOptionPane.showMessageDialog(this, message, "How to Move Checkers", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setFocusable(true);
    }

    public CheckerSquare[][] getCheckersSquare() {
        return squares;
    }
}