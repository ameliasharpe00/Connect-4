
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ConnectBoard extends JPanel {

    private ConnectModel model; // model for the game
    private JLabel status; // current status text
    private JLabel moves;
    private String player1 = JOptionPane.showInputDialog("Player 1: enter name");
    private String player2 = JOptionPane.showInputDialog("Player 2: enter name");
    // Game constants
    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 600;

    /**
     * Initializes the game board.
     */
    public ConnectBoard(JLabel statusInit) {
        // creates border
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key
        // listener.
        setFocusable(true);

        model = new ConnectModel(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game board based
         * off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                model.playTurn(p.x / 100);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
                moves.setText("Number of Moves: " + model.getNumMoves());
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        model.reset();
        status.setText(player1 + "'s Turn");
        repaint();

        moves.setText("Number of Moves: " + model.getNumMoves());

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void undoMove() {
        model.undo();
        repaint();
        if (model.getCurrentPlayer()) {
            status.setText(player1 + "'s Turn");
        } else {
            status.setText(player2 + "'s Turn");
        }
        moves.setText("Number of Moves: " + model.getNumMoves());
    }

    // it seems to be resuming the pausing
    public void pauseGame() {
        model.pause();
        repaint();
        moves.setText("Number of Moves: " + model.getNumMoves());
    }

    public void resumeGame() {
        model.resume();
        System.out.println();
        repaint();
        moves.setText("Number of Moves: " + model.getNumMoves());
    }

    public void instructions() {
        JOptionPane.showMessageDialog(new JFrame(),
                "- You win a game by connecting 4 circles" + 
            System.lineSeparator()
                        + "either horizontally, vertically or diagonally" +
            System.lineSeparator()
                        + "- The game will result in a tie if "
                        + "the board is full and there" + System.lineSeparator()
                        + "are no 4-connected circles");
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (model.getCurrentPlayer()) {
            status.setText(player1 + "'s Turn");
        } else {
            status.setText(player2 + "'s Turn");
        }

        int winner = model.checkWinner();
        if (winner == 1) {
            status.setText(player1 + " wins!!!");
        } else if (winner == 2) {
            status.setText(player2 + " wins!!!");
        } else if (winner == 3) {
            status.setText("It's a tie.");
        }
    }

    public JLabel getNumMovesLabel() {
        moves = new JLabel();
        moves.setText("Number of Moves: " + model.getNumMoves());
        return moves;
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach will not be
     * sufficient for most games, because it is not modular. All of the logic for
     * drawing the game board is in this method, and it does not take advantage of
     * helper methods. Consider breaking up your paintComponent logic into multiple
     * methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(100, 0, 100, 600); // very left
        g.drawLine(200, 0, 200, 600);
        g.drawLine(300, 0, 300, 600);
        g.drawLine(400, 0, 400, 600);
        g.drawLine(500, 0, 500, 600);
        g.drawLine(600, 0, 600, 600);
        g.drawLine(0, 100, 700, 100);
        g.drawLine(0, 200, 700, 200);
        g.drawLine(0, 300, 700, 300);
        g.drawLine(0, 400, 700, 400);
        g.drawLine(0, 500, 700, 500);

        // Draws Circles
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                int state = model.getCell(j, i);
                if (state == 1) {
                    g.setColor(Color.BLUE);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 2) {
                    g.setColor(Color.PINK);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 0) {
                    g.setColor(Color.WHITE);
                    g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                }
            }
        }

    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

}
