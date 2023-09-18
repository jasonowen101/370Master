import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckerboardGUI extends JFrame {
    private static final int BOARD_SIZE = 8;
    private CheckerSquare[][] squares;

    public CheckerboardGUI() {
        setTitle("Checkerboard");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    private void startGame() {
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
    }

    private void showHelpDialog() {
        String message = "Click the checker you want to move.\n" +
                "Click the empty space you want to move the checker to.\n" +
                "You can click the checker again to deselect it, or click another checker.";
        JOptionPane.showMessageDialog(this, message, "How to Move Checkers", JOptionPane.INFORMATION_MESSAGE);
    }


}
