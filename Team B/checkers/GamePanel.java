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
        setSize(400, 400);

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

        add(boardPanel, BorderLayout.CENTER);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        JButton clearBoardButton = new JButton("Clear Board");
        clearBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
            }
        });

        JButton helpButton = new JButton("?");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelpDialog();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);
        buttonPanel.add(clearBoardButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(turn);

        add(buttonPanel, BorderLayout.SOUTH);

//        setVisible(true);
    }


    private void startGame() {
        clearBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row < 3 && (row + col) % 2 == 1) {
                    squares[row][col].toggleChecker(CheckerSquare.TEAM1);
                } else if (row > 4 && (row + col) % 2 == 1) {
                    squares[row][col].toggleChecker(CheckerSquare.TEAM2);
                }
            }
        }
    }

    private void clearBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col].toggleChecker(null);
            }
        }
        GUI.blueTurn = true;
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
}