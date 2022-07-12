import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 
import java.awt.Graphics;
import java.awt.Color; 

@SuppressWarnings("serial")
public class SudokuBoard extends JPanel {

    private Sudoku Sudoku; // model for the game
    private JLabel status; // current status text
    String fileName = "initial.txt";

    private double currX = -1;
    private double currY = -1;

    private int row; 
    private int col; 

    private Square chosen;     // Square we want to edit
    private int digit;         // number we want to input

    // Game constants
    public static final int BOARD_WIDTH = 450;
    public static final int BOARD_HEIGHT = 450;

    /**
     * Initializes the game board.
     */
    public void GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        Sudoku = new Sudoku(fileName); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                currX = e.getX();
                currY = e.getY();

                if (Sudoku.isGameOver()) {
                    return;
                }

                // convert mouse coordinates to 2D array coordinates
                col = getCol(currX);
                row = getRow(currY);

                // record starting Square
                chosen = Sudoku.getBoard()[row][col];
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                currX = e.getX();
                currY = e.getY();
                int col = getCol(currX);
                int row = getRow(currY);

                chosen = Sudoku.getBoard()[row][col];
        }});

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                digit = (int) e.getKeyChar();
                if (digit < 1 || digit > 9) {
                    status.setText("invalid input");
                } else {
                    Sudoku.getBoard()[row][col].setNumber(digit);
                }
            }

            // not much to do for keyReleased or keyTyped
            @Override 
            public void keyReleased(KeyEvent e) {
                repaint();
            }

            @Override
            public void keyTyped(KeyEvent e) {
                repaint();

        }});

    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        Sudoku.clearAll(fileName);
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (Sudoku.isGameOver()) {
            status.setText("You solved it!");
        }
    }

    // inverse of getX in Square class (input will be mouse Xcoord)
    public int getCol(double x) {
        return (int) x / 50;
    }

    // inverse of getY in Square class
    public int getRow(double y) {
        return 7 - (int) y / 50;
    }

    /**
     * Draws the game board. Divides the board into 64 Squares, and then draws
     * the Piece on the Squares for the Squares that currently have Pieces
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the 64 Squares
        g.drawLine(0, 0, 0, 450);
        g.drawLine(50, 0, 50, 450);
        g.drawLine(100, 0, 100, 450);
        g.drawLine(150, 0, 150, 450);
        g.drawLine(200, 0, 200, 450);
        g.drawLine(250, 0, 250, 450);
        g.drawLine(300, 0, 300, 450);
        g.drawLine(350, 0, 350, 450);
        g.drawLine(400, 0, 400, 450);
        g.drawLine(450, 0, 450, 450);

        g.drawLine(0, 0, 450, 0);
        g.drawLine(0, 50, 450, 50);
        g.drawLine(0, 100, 450, 100);
        g.drawLine(0, 150, 450, 150);
        g.drawLine(0, 200, 450, 200);
        g.drawLine(0, 250, 450, 250);
        g.drawLine(0, 300, 450, 300);
        g.drawLine(0, 350, 450, 350);
        g.drawLine(0, 400, 450, 400);
        g.drawLine(0, 450, 450, 450);

        Square[][] board = Sudoku.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!Sudoku.isLegalEntry(i, j)) {
                    board[i][j].illegalRed(g);
                } else {
                    board[i][j].drawSquare(g);
                }
            }
        }
    }
}



