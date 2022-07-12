import java.awt.Graphics; 
import java.awt.Color; 

public class Square {
    private int number = 0;    // 0 means square is initially empty
    private static int row; 
    private static int col;
    private boolean isValid;   // check if number in cell legal according to Sudoku rules 
    private boolean hasChanged; 

public Square(int userInput, int row, int col) {
    number = userInput; 
    this.row = row; 
    this.col = col; 
    isValid = true;    // default to true
    hasChanged = false; 
}

public void drawSquare(Graphics g) {
    int x = (int) getX(col);   // cast to int for drawing functions
    int y = (int) getY(row); 
    if (this.number == 0) {
        g.drawString(" ", x, y);
    } else {
        g.drawString("" + this.number, x, y); 
    }
}

// determine x-coordinate based on column number 
public double getX(int col) {
    double x = col / 9.0 + 1.0 / 18.0; 
    return x; 
}

// determine y-coordinate based on row number
public double getY(int row) {
    double y = 17.0 / 18.0 - row / 9.0; 
    return y;   
}

// define getter methods for each of the fields in Square class
public int getRow() {
    return row; 
}

public int getCol() {
    return col; 
}

public int getNumber() {
    return number; 
}

public boolean isValid() {
    return isValid; 
}

public boolean hasChanged() {
    return hasChanged;
}

// setter method for Square's number (useful for changing numbers)
public void setNumber(int number) {
    this.number = number; 
}

public void setHasChanged(boolean b) {
    this.hasChanged = b;
}

// changes a Square's number to red-colored if illegal play
public void illegalRed(Graphics g) {
    int x = (int) getX(col);
    int y = (int) getY(col); 
    g.setColor(Color.RED);
    g.drawString("" + this.number, x, y);
}

// clear current element inside Square
public void clear(Graphics g) {
    setNumber(0);      // this will draw empty Square according to drawSquare method
    this.drawSquare(g);
}

// highlights chosen Square (useful for knowing which Square we're currently editing)
public void highlight(Graphics g) {
    g.setColor(Color.YELLOW);
    g.fillRect(row, col, 50, 50);
}

}