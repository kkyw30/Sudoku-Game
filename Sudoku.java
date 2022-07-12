import java.io.FileNotFoundException;
import java.io.FileReader; 
import java.io.IOException; 
import java.util.Iterator; 
import java.io.BufferedReader; 
import java.io.File;
import java.util.Scanner; 
import java.awt.Graphics;
import javax.swing.*;

public class Sudoku {
    private Square[][] board = new Square[9][9];     // store game contents in 2d array 
    private static int counter = 0;     // count # of legal entries in array, game over if 81
    public static final int BOARD_WIDTH = 450;     // set dimensions of the board
    public static final int BOARD_HEIGHT = 450; 

// read in a File using the Scanner method (will use this method in constructor) 
private String readFile(String fileName) {
    File file = new File(fileName);   // define new file
    StringBuilder fileContent = new StringBuilder((int) file.length());  // mutable sequence of chars, same length as the file 

    // read in the contents of the file (starting Sudoku numbers) using Scanner 
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            fileContent.append(scanner.nextLine() + System.lineSeparator());
        }
        return fileContent.toString();
    } catch (FileNotFoundException ex) {
        return " ";        // return empty String if no file is found 
    }
}

    public Sudoku(String fileName) {
    String fileContent = readFile(fileName); 
    int newLines = 0; 

    // determine whether characters in input file are valid Sudoku numbers
    for (int i = 0; i < fileContent.length(); i++) {
        int currDigit = (int) fileContent.charAt(i);       // convert ASCII character to a digit (hopefully between 1 and 9)
        if (currDigit == 10) {         // newLine character
            newLines++;
        } else if (currDigit == 32) {  // space character
            continue; 
        } else if (currDigit <= 48 || currDigit > 57) {          // can only have numbers between 1 and 9
            throw new IllegalArgumentException("invalid starting number"); 
        }
    }

    // make sure there are 9 rows total
    if (newLines != 8) {
        throw new IllegalArgumentException("input doesn't have 9 rows");
    }

    // make sure there are 81 characters in String (so we can properly fill board) 
    if (fileContent.length() - newLines != 81) {
        throw new IllegalArgumentException("input has wrong number of elements"); 
    }

    int charCount = 0; 
    for (int row = 0; row < board.length; row++) {
        for (int col = 0; col < board[row].length; col++) {
            char curr = fileContent.charAt(charCount); 
            charCount++;
            if (curr == ' ') {    // space means Square is currently empty
                board[row][col] = new Square(0, row, col);
            }
             else if ((int) curr == 10) {  // skip every 10th character
                col = col - 1; 
             } else {
                int digit = (int) curr - 48; 
                board[row][col] = new Square(digit, row, col);
                counter++;
             }
        }
    }

    // make sure all entries in input file are legal 
    while (isGameOver() == false) {
        for (int row = 0; row  < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!this.isLegalEntry(row, col)) {
                    throw new IllegalArgumentException("input has illegal elements");
                }
            }
        }
    }
}

// method for determining whether game is over
public boolean isGameOver() {
    // check if each row adds up to 45 (1-9)
    for (int row = 0; row < 9; row++) {
        int rowSum = this.rowSum(row); 
        if (rowSum != 45 && counter < 81) {
            return false; 
        }
    }

    // check if each column adds to 45
    for (int col = 0; col < 9; col++) {
        int colSum = this.colSum(col); 
        if (colSum != 45 && counter < 81) {
            return false; 
        }
    }

    return true;
}

// method for calculating sum of a row
public int rowSum(int row) {
    int sum = 0; 
    for (int col = 0; col < 9; col++) {
        sum += board[row][col].getNumber();
    }
    return sum; 
}

// method for calculating sum of a column
public int colSum(int col) {
    int sum = 0; 
    for (int row = 0; row < 9; row++) {
        sum += board[row][col].getNumber();
    }
    return sum; 
}

// method for determining whether a desired input is legal
public boolean isLegalEntry(int row, int col) {
    return isLegalRow(row) && isLegalCol(col) && isLegalBox(row, col);   // all three have to be satisfied
}

// helper method for isLegalEntry, checks if a row is legal 
private boolean isLegalRow(int row) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (i != j) {
                if (board[row][i].getNumber() == board[row][j].getNumber() && board[row][i].getNumber() != 0 && board[row][j].getNumber() != 0) {  // means there's duplicates
                    return false; 
                }
            }
        }
    }
    return true;    // valid row otherwise
}

// checks if a column is legal
private boolean isLegalCol(int col) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (i != j) {
                if (board[i][col].getNumber() == board[j][col].getNumber() && board[i][col].getNumber() != 0 && board[j][col].getNumber() != 0) {  // means there's duplicates
                    return false; 
                }
            }
        }
    }
    return true;    // valid column otherwise
}

// checks if a 3x3 box is legal
private boolean isLegalBox(int row, int col) {
    // determine starting indices for the for loop iteration
    int startRow = determineBoxRow(row); 
    int startCol = determineBoxCol(col);

    int[] boxContents = new int[9];     // store the numbers from the box
    int counter = 0;
    for (int i = startRow; i < startRow + 3; i++) {
        for (int j = startCol; j < startCol + 3; j++) {
            int curr = board[i][j].getNumber();
            boxContents[counter] = curr; 
            counter++;
        }
    }

    // use the array to determine if there are any duplicate elements
    for (int i = 0; i < boxContents.length; i++) {
        for (int j = 0; j < boxContents.length; j++) {
            if (i != j) {
                if (boxContents[i] == boxContents[j] && boxContents[i] != 0 && boxContents[j] != 0) {  // means there's duplicates
                    return false; 
                }
            }
        }
    }
    return true; 
}

// determines which row to start at (for isLegalBox) 
private int determineBoxRow(int row) {
    int startRow = 0; 
    if (row < 3) {
        startRow = 0; 
    } else if (row >=3 && row < 6) {
        startRow = 3;
    } else if (row >= 6) {
        startRow = 6; 
    }
    return startRow; 
}

// determines which col to start at (for isLegalBox)
private int determineBoxCol(int col) {
    int startCol = 0; 
    if (col < 3) {
        startCol = 0; 
    } else if (col >=3 && col < 6) {
        startCol = 3;
    } else if (col >= 6) {
        startCol = 6; 
    }
    return startCol; 
}

// update the number in a Square (based on user input)
public void updateNumber(int row, int col, int input) {
    board[row][col].setNumber(input);
    board[row][col].setHasChanged(true);
}

// method to reset the board
public void clearAll(String fileName) {
    // creates new empty board with specified fileName
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j].hasChanged()) {
                board[i][j].setNumber(0);           // back to empty Square
            } else {
                continue;    // don't do anything to original starting Squares 
            }
        }
    }
}

public Square[][] getBoard() {
    Square[][] b = this.board; 
    return b; 
}

// NOW INCLUDE THE METHODS WE NEED FOR SOLVING SUDOKU
public boolean solve(String fileName) { 
    this.clearAll(fileName);          // clear everything first so program can restart with clean slate
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col].getNumber() == 0) {
                for (int k = 1; k <= 9; k++) {
                    board[row][col].setNumber(k);            // try all possible values in that Square
                    if (isLegalEntry(row, col) && solve(fileName)) {   // recursive backtracking
                        return true; 
                    }
                    board[row][col].setNumber(0);
                }
                return false;
            }
        }
    }
    return true;
}

}
