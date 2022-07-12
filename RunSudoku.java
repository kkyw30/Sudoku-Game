import javax.swing.*;
import java.awt.*;

public class RunSudoku {
    public static void main(String[] args) {
        String fileName = args[0];
        Sudoku Sudoku = new Sudoku(fileName);
    }

    public void run() {
        String fileName = "initial.txt";      // our starting file
        Sudoku board = new Sudoku(fileName);

        // top-level frame storing all game components
        final JFrame frame = new JFrame("Sudoku");
        frame.setLocation(300, 300);

        // status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // reset button 
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.clearAll(fileName));
        control_panel.add(reset);

        // solve button 
        final JButton solve = new JButton("Solve");
        solve.addActionListener(e -> board.solve(fileName));
        control_panel.add(solve);

        // put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Instructions Panel
        JOptionPane.showMessageDialog(frame, "Welcome to Sudoku, a game where you try to fill in each square on the board with a number from 1 - 9, but without \n" +
        "any duplicate elements in any row, column or 3x3 box. Click the Square you want to edit, then type in your desired number. Illegal entries will \n" 
        + "show up in red, and you can always press the solve button to get the program to solve the puzzle for you. Good luck and have fun!");
    }
}
